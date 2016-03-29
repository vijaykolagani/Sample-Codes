package com.appzollo.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Prasad on 14-Jan-15.
 */
public class AddSDImgAdp extends BaseAdapter {
    int GalItemBg;
    ArrayList<Bitmap> bitmapList;
    private Context cont;

    public AddSDImgAdp(Context c, ArrayList<Bitmap> bitmapList) {
        cont = c;
        this.bitmapList = bitmapList;
    }

    public int getCount() {
        return bitmapList.size();
    }

    public Object getItem(int position) {
        return bitmapList.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imgView = new ImageView(cont);

        // imgView.setImageResource(Imgid[position]);
        imgView.setImageBitmap(bitmapList.get(position));

        imgView.setLayoutParams(new Gallery.LayoutParams(250, 250));
        imgView.setScaleType(ImageView.ScaleType.FIT_XY);
        imgView.setBackgroundResource(GalItemBg);

        return imgView;
    }
}
