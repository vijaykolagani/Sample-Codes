package com.appzollo.utils;

/**
 * Created by vijay on 24-12-2014.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appzollo.R;
import com.appzollo.classes.LoadProfileImageAsync;
import com.appzollo.interfaces.OnLoadCompleteListener;


public class ImageAdapter extends BaseAdapter implements OnLoadCompleteListener {
    private Context mContext;
    private String[] _imagePaths;
    private LayoutInflater inflater;

    static TextView mDotsText[];
    private int mDotsCount;
    private LinearLayout mDotsLayout;


    //array of integers for images IDs
    private Integer[] mImageIds = {
            R.drawable.ic_365logo,
            R.drawable.ic_365logo,
            R.drawable.ic_365logo

    };

    //constructor
    public ImageAdapter(Context c,String[] imagePaths) {

        mContext = c;
        this._imagePaths = imagePaths;
    }

    @Override
    public int getCount() {
        return _imagePaths.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ImageView imageView = new ImageView(mContext);
        imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        ImageView imgDisplay;
      /*  imageView.setImageResource(mImageIds[i]);
        imageView.setLayoutParams(new Gallery.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
       */
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_image, viewGroup,
                false);

        imgDisplay = (ImageView) viewLayout.findViewById(R.id.imgDisplay);

        LoadProfileImageAsync task = new LoadProfileImageAsync(imgDisplay, ImageAdapter
                .this);
        task.execute(_imagePaths[0]);
        mDotsLayout = (LinearLayout)viewLayout.findViewById(R.id.image_count);
        //here we count the number of images we have to know how many dots we need
        mDotsCount = _imagePaths.length;

        //here we create the dots
        //as you can see the dots are nothing but "."  of large size
        mDotsText = new TextView[mDotsCount];

        //here we set the dots
        for (int j = 0; j < mDotsCount; j++) {
            mDotsText[j] = new TextView(mContext);
            mDotsText[j].setText(".");
            mDotsText[j].setTextSize(45);
            mDotsText[j].setTypeface(null, Typeface.BOLD);
            mDotsText[j].setTextColor(android.graphics.Color.GRAY);
            mDotsLayout.addView(mDotsText[j]);
        }


        mDotsText[i]
                .setTextColor(Color.WHITE);
        return viewLayout;
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
