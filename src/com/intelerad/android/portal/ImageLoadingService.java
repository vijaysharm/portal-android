/**
 * 
 */
package com.intelerad.android.portal;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.util.LruCache;
import android.util.Log;

import android.widget.Toast;
import com.intelerad.android.portal.models.Session;
import com.intelerad.android.portal.rpc.PortalApi;
import com.intelerad.imageviewer.gwt.model.GwtPacsImage;
import com.intelerad.imageviewer.gwt.model.Image2dRequest;

/**
 * @author Serge Perinsky
 */
public class ImageLoadingService extends Service
{

    private static final String LOG_TAG = ImageLoadingService.class.getSimpleName();

    private static final String LOAD_IMAGE_ACTION = "LoadIntelePacsImage";

    private static final String TASK_KEY = "task";

    private NotificationManager mNotificationManager = null;

    private static List<ImageLoadingTask> sImageLoadingTaskQueue = new ArrayList<ImageLoadingTask>();

    private LruCache<String, Bitmap> mMemoryCache;
    
//    private DiskLruCache mDiskCache;
//    private final Object mDiskCacheLock = new Object();
//    private boolean mDiskCacheStarting = true;
//    private static final int DISK_CACHE_SIZE = 1024 * 1024 * 10; // 10MB ///FIXME make configurable
//    private static final String DISK_CACHE_SUBDIR = "InteleConnectCache";
    
    @Override
    public void onCreate()
    {

        mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE );

        // initialize cache
        
        // Get max available VM memory, exceeding this amount will throw an OutOfMemory exception.
        // Stored in kilobytes as LruCache takes an int in its constructor.
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/4th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 4;

        Log.i(LOG_TAG, "Initializing a memory cache of " + cacheSize
                       + " KB (VM size is " + maxMemory + " KB)");
        
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap bitmap) {
                // The cache size will be measured in kilobytes rather than
                // number of items.
                
                // bitmap.getByteCount() is only present in API 12+, just use the equivalent code:
///
Log.d(LOG_TAG, "bitmap size is " + (bitmap.getRowBytes() * bitmap.getHeight() / 1024) +  " KB");
///
                return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
            }
        };
    }

    @Override
    public int onStartCommand( Intent intent, int flags, int startId )
    {
        // /FIXME (!) use notification that caching service is running (let the user cancel it at
        // will)
        Toast.makeText( this, "image loader service starting", Toast.LENGTH_SHORT ).show();

/// FIXME tmp
Log.i( LOG_TAG, "*** the service was started:" );
Log.i( LOG_TAG, "intent=" + intent );
Log.i( LOG_TAG, "intent extras=" + intent.getExtras() );
Log.i( LOG_TAG, "accession ID=" + Utils.extractAccession( intent ) );
///

        return START_REDELIVER_INTENT; ///FIXME correct?
    }

    @Override
    public void onDestroy()
    {
        // /FIXME (!) use notification that caching service is running (let the user cancel when
        // needed)
        Toast.makeText( this, "image loader service done", Toast.LENGTH_SHORT ).show();
    }

    /**
     * Class for clients to access. Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class LocalBinder extends Binder
    {
        ImageLoadingService getService()
        {
            return ImageLoadingService.this;
        }
    }

    // This is the object that receives interactions from clients.
    // See RemoteService for a more complete example.
    private final IBinder mBinder = new LocalBinder();

    @Override
    public IBinder onBind( Intent arg0 )
    {
        return mBinder;
    }

    void showNotification( int notificationId, String text )
    {

        if ( mNotificationManager != null )
        {

            // /FIXME what to do about various incompatibilities in API?

            // Notification notification = new Notification.Builder(this)
            // .setContentTitle(title)
            // .setContentText(text)
            // .setSmallIcon(R.drawable.ic_launcher)
            // .build();

            Notification notification = new Notification( R.drawable.ic_launcher, // icon
                text, System.currentTimeMillis() ); // when to show

            // The PendingIntent to launch our activity if the user selects this notification
            // /FIXME where do we jump? we need to know which activity to choose
            PendingIntent contentIntent = PendingIntent.getActivity( ImageLoadingService.this, 0,
                new Intent( this, DatasetSelectorActivity.class ), 0 );

            // Set the info for the views that show in the notification panel.
            notification.setLatestEventInfo( this, "InteleConnect", text, contentIntent );

            mNotificationManager.notify( notificationId, notification );
        }
    }

    void cancelNotification( int notificationId )
    {
        if ( mNotificationManager != null )
        {
            mNotificationManager.cancel( notificationId );
        }
    }

    public static boolean isServiceRunning( Context context )
    {
        ActivityManager manager = (ActivityManager) context
            .getSystemService( Context.ACTIVITY_SERVICE );
        for ( RunningServiceInfo service : manager.getRunningServices( Integer.MAX_VALUE ) )
        {
            if ( ImageLoadingService.class.getName().equals( service.service.getClassName() ) )
            {
                return true;
            }
        }
        return false;
    }

///FIXME stop the service when activity exits;
// on the other hand, use WakeLock if the queue is not empty

///FIXME if the activity is no longer visible/active and the queue is empty, stop the service

    private static ImageLoadingService sService = null;

    // defines callbacks for service binding, passed to bindService()
    private static ServiceConnection sConnection = new ServiceConnection()
    {

        @Override
        public void onServiceConnected( ComponentName serviceComponentName, IBinder binder )
        {
///
Log.i( LOG_TAG, "%%% SERVICE CONNECTED. Service ComponentName=" + serviceComponentName );
///
            sService = ( (LocalBinder) binder ).getService();
            sService.startDownloadQueueProcessingThread();
            sService.showNotification( 0, "Image downloading service started" );
        }

        @Override
        public void onServiceDisconnected( ComponentName serviceComponentName )
        {
///
Log.i( LOG_TAG, "%%% SERVICE DISCONNECTED. Service ComponentName=" + serviceComponentName );
///
            sService = null;
        }
    };

    public static void stopService( Context context )
    {
        if ( sConnection != null )
        {
            context.unbindService( sConnection );
            sConnection = null;
        }
    }
    
    private static void ensureServiceIsStarted( Context context )
    {
        if ( sService == null ) // not running yet?
        { 
///
Log.i( LOG_TAG, "BINDING SERVICE (making attempt)" );
///
            Intent intent = new Intent( context, ImageLoadingService.class );
            context.bindService( intent, sConnection, Context.BIND_AUTO_CREATE );
        }
    }

    // /FIXME provide a static method to unbind from the service

    protected void startDownloadQueueProcessingThread()
    {
        Runnable worker = new Runnable()
        {

            // /FIXME fix this: will consume idle cycles when the queue is empty
            @Override
            public void run()
            {
                while ( true )
                { // / FIXME should be while not cancelled and while bound! {
                    while ( !sImageLoadingTaskQueue.isEmpty() )
                    {
                        /// FIXME use synchronized access
                        ImageLoadingTask task = sImageLoadingTaskQueue.get( 0 );
                        // /
                        Log.i( LOG_TAG, "WORKER: processing task " + task );
                        // /
                        AsyncBitmapDownloadTask asyncDownloadTask = new AsyncBitmapDownloadTask();
                        asyncDownloadTask.execute( task );
                        // /
                        Log.i( LOG_TAG, "WORKER: removing task " + task + " from queue" );
                        // /
                        sImageLoadingTaskQueue.remove( 0 ); // done processing this item
                    }

                    try
                    {
                        Thread.sleep( 10 ); // 10 ms between checks
                    }
                    catch ( InterruptedException e )
                    {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } // / FIXME should be woken up only when necessary!
                }
            }
        };

        Thread workerThread = new Thread( worker );
        workerThread.start();

        // /FIXME make sure this can be cancelled as well!
    }

    public static void clearImageLoadingTaskQueue()
    {
///FIXME do something else? remove the queue processing thread here
        // sImageLoadingTaskQueue = new ArrayList<ImageLoadingTask>();
    }

    public static void scheduleImageLoadingTask( Context context, ImageLoadingTask task )
    {
        ensureServiceIsStarted( context );

///
Log.i( LOG_TAG, ">>> scheduling image loading task: " + task );
///

        synchronized ( sImageLoadingTaskQueue ) {
            sImageLoadingTaskQueue.add( task );
        }
    }

    private void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            synchronized ( mMemoryCache )
            {
                mMemoryCache.put(key, bitmap);
            }
        }
    }

    private Bitmap getBitmapFromMemoryCache(String key) {
        synchronized ( mMemoryCache )
        {
            return mMemoryCache.get(key);
        }
    }
    
    class AsyncBitmapDownloadTask extends AsyncTask<ImageLoadingTask, Void, Bitmap>
    {

        ImageLoadingTask mTask = null;

        @Override
        protected Bitmap doInBackground( ImageLoadingTask... params )
        {
            mTask = params[ 0 ];

///FIXME 
Log.i( LOG_TAG, "Starting background job to download image from PACS. Task=" + mTask );
///
            try
            {
                return getPacsImageBitmap( mTask.getPacsSession(),
                                            mTask.getPacsImage(),
                                            mTask.getRequestedWidth(),
                                            mTask.getRequestedHeight() );
            }
            catch ( Exception e )
            {
                e.printStackTrace();

                return BitmapFactory.decodeResource( getResources(), R.drawable.error_icon );
            }
        }

        @Override
        protected void onPostExecute( Bitmap bitmap )
        {
            if ( ( mTask != null ) && ( bitmap != null ) && mTask.isReceiverAlive() )
            {
                mTask.getBitmapReceiver().onBitmapDelivery( bitmap, mTask );
            }
        }
    }

    private Bitmap getPacsImageBitmap( Session pacsSession, GwtPacsImage pacsImage, int width,
                                       int height ) throws Exception
    {

        if ( width <= 0 || height <= 0 )
        {
            // /FIXME substitute original image dimensions here
            width = 16;
            height = 16;
        }

        // TODO should check and use cache here first

        Image2dRequest imageRequest = new Image2dRequest( pacsImage, width, height, true );
        String imageUrl = pacsImage.getUrlEncoder().getUrl( imageRequest );

        Log.i( LOG_TAG, "Using PACS image URL = " + imageUrl );
        
        // first, see if the bitmap is already in the memory cache (level 1)
        Bitmap bitmap = getBitmapFromMemoryCache( imageUrl ); // image URL is the cache key
        
        if ( bitmap != null )
        {
            Log.i(LOG_TAG, "************* MEMORY CACHE: bitmap found!");///FIXME
            return bitmap;
        }
        
        byte[] imageBytes = PortalApi.getImageBytes( pacsSession, imageUrl );

        // /
        if ( imageBytes != null )
        {
            Log.i( LOG_TAG, "downloaded " + imageBytes.length + " image byte(s) from PACS" );
        }
        else
        {
            Log.i( LOG_TAG, "error downloading image from PACS" );
        }
        // /

        // see Context.getExternalCacheDir() etc.
        String imageFileName = getCacheDir() + "/image-" + pacsImage.getImageNodeSopInstanceUid() +
                               ".jpg";// /FIXME naming
        try
        {
            BufferedOutputStream imageFileStream = new BufferedOutputStream( new FileOutputStream(
                imageFileName ) );
            imageFileStream.write( imageBytes );
            imageFileStream.flush();
            imageFileStream.close();

            bitmap = BitmapFactory.decodeFile( imageFileName );

            // add the freshly loaded bitmap to the cache
            addBitmapToMemoryCache( imageUrl, bitmap );
            
///FIXME tmp; retain cache files via some policy etc.
new File( imageFileName ).delete();
///^
            return bitmap;

        }
        catch ( IOException e )
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
