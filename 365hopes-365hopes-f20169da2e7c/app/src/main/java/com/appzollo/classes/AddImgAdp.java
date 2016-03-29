package com.appzollo.classes;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.appzollo.interfaces.OnLoadCompleteListener;

import java.util.ArrayList;

/**
 * Created by Prasad on 14-Jan-15.
 */
public class AddImgAdp extends BaseAdapter implements OnLoadCompleteListener {
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
    public AddImgAdp(Context c,String[] imagePaths) {

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
        imageView.setLayoutParams(new Gallery.LayoutParams(250, 250));
        ImageView imgDisplay;



        LoadProfileImageAsync task = new LoadProfileImageAsync(imageView, AddImgAdp
                .this);
        task.execute(_imagePaths[0]);

        return imageView;
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