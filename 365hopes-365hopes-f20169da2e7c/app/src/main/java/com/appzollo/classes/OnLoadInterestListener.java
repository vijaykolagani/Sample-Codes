package com.appzollo.classes;


import android.graphics.Bitmap;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.BitSet;

/**
 * Created by prasad on 12/6/2014.
 */
public interface OnLoadInterestListener {
    public void onInterestLoadComplete(LinearLayout view, ArrayList<Bitmap> list);
}
