package com.intelerad.android.portal;

import java.lang.ref.WeakReference;

import com.intelerad.android.portal.models.Session;
import com.intelerad.imageviewer.gwt.model.GwtPacsImage;

public class ImageLoadingTask { ///FIXME remove: implements Serializable {
	
//	private static final long serialVersionUID = -2133803508119125330L;
	
	private final WeakReference<BitmapReceiver> mBitmapReceiverReference;
//	private BitmapReceiver mBitmapReceiver;
	private Session mPacsSession;
    private GwtPacsImage mPacsImage;
	private int mRequestedWidth;
	private int mRequestedHeight;
	
	public ImageLoadingTask(BitmapReceiver bitmapReceiver,
							Session pacsSession,
			 				GwtPacsImage pacsImage) {
		this(bitmapReceiver, pacsSession, pacsImage, 0, 0); // get the whole image
	}
	
	public ImageLoadingTask(BitmapReceiver bitmapReceiver,
							 Session pacsSession,
							 GwtPacsImage pacsImage,
							 int requestedWidth,
							 int requestedHeight) {
        // Use a WeakReference to ensure the ImageView can be garbage collected
        this.mBitmapReceiverReference = new WeakReference<BitmapReceiver>(bitmapReceiver);
//		this.mBitmapReceiver = bitmapReceiver;
        this.mPacsSession = pacsSession;
		this.mPacsImage = pacsImage;
		this.mRequestedWidth = requestedWidth;
		this.mRequestedHeight = requestedHeight;
	}
	
	public boolean isReceiverAlive() {
		return getBitmapReceiver() != null;
	}

	public BitmapReceiver getBitmapReceiver() {
        return mBitmapReceiverReference != null? mBitmapReceiverReference.get() : null;
//		return mBitmapReceiver;
	}
	
	public Session getPacsSession() {
		return mPacsSession;
	}
	
	public GwtPacsImage getPacsImage() {
		return mPacsImage;
	}

	public int getRequestedWidth() {
		return mRequestedWidth;
	}

	public int getRequestedHeight() {
		return mRequestedHeight;
	}
	
	public String toString()
	{
	    return "[ImageLoadingTask: SOP UID=" + mPacsImage.getImageNodeSopInstanceUid() + "]";
	}
}
