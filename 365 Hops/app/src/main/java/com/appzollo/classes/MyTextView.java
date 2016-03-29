package com.appzollo.classes;

/**
 * Created by prasad on 12/27/2014.
 */
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.LruCache;
import android.widget.TextView;

import com.appzollo.R;

public class MyTextView extends TextView {

    private static LruCache<String, Typeface> sTypefaceCache =
            new LruCache<String, Typeface>(12);
    Typeface tf;

    public MyTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);

    }

    public MyTextView(Context context) {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs) {
        if (attrs!=null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MyTextView);
            String fontName = a.getString(R.styleable.MyTextView_fontName);


            if (fontName!=null  ) {
                tf = sTypefaceCache.get(fontName);
                if(tf==null){
                   tf = Typeface.createFromAsset(getContext().getAssets(), fontName);
                    sTypefaceCache.put(fontName, tf);
                }
                    setTypeface(tf);


            }
            a.recycle();
        }
    }

}