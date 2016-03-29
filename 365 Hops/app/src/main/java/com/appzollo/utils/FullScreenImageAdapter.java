package com.appzollo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.appzollo.R;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.interfaces.OnLoadCompleteListener;

import java.util.ArrayList;

public class FullScreenImageAdapter extends PagerAdapter implements OnLoadCompleteListener {

	private Activity _activity;
	private String[] _imagePaths;
	private LayoutInflater inflater;

	public FullScreenImageAdapter(Activity activity,
			String[] imagePaths) {
		this._activity = activity;
		this._imagePaths = imagePaths;
	}

	@Override
	public int getCount() {
		return this._imagePaths.length;
	}

	@Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }
	
	@Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imgDisplay;
 
        inflater = (LayoutInflater) _activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, container,
                false);
 
        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        LoadProfileImageAsync task = new LoadProfileImageAsync(imgDisplay, FullScreenImageAdapter.this);
        task.execute(_imagePaths[position]);

        ((ViewPager) container).addView(viewLayout);
 
        return viewLayout;
	}
	
	@Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);
 
    }

    @Override
    public void onLoadComplete(View view, Bitmap bitmap) {
        if (bitmap != null && view != null) {
            if (view instanceof ImageView) {
                ((ImageView) view).setImageBitmap(bitmap);
            } else if (view instanceof FrameLayout) {
                Drawable d = new BitmapDrawable(bitmap);
                if (d != null) {
                    if (android.os.Build.VERSION.SDK_INT >= 16) {
                        view.setBackground(d);
                    } else {
                        view.setBackgroundDrawable(d);
                    }
                }

            }
        }
    }
}
