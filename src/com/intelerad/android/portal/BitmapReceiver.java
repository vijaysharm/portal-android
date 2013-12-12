package com.intelerad.android.portal;

import android.graphics.Bitmap;

public interface BitmapReceiver {

	public void onBitmapDelivery(Bitmap bitmap, Object sourceObject);
}
