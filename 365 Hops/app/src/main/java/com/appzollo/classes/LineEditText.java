package com.appzollo.classes;

/**
 * Created by prasad on 1/6/2015.
 */
import android.content.Context;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.EditText;

public class LineEditText extends EditText {
    private Rect mRect;
    private Paint mPaint;
    // we need this constructor for LayoutInflater
    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        mRect = new Rect();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLACK); //SET YOUR OWN COLOR HERE
        setMinLines(3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int height = getHeight();
        int line_height = getLineHeight();

        int count = height / line_height;
        if(getLineCount() > count){
            count = getLineCount();
        }
        Rect r = mRect;
        r.offset(0,5);
        Paint paint = mPaint;
        int baseline = getLineBounds(0, r);

        for (int i = 0; i < count; i++) {

            canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            baseline += getLineHeight();//next line
        }

        // Finishes up by calling the parent method
        super.onDraw(canvas);
    }
}