package com.mit.mit_flashlight;


import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.PreviewCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class Preview extends SurfaceView implements SurfaceHolder.Callback {
	
	

    SurfaceHolder mydHolder;
    public Camera Mydcamera;
    
    Preview(Context context) {
        super(context);
       
        mydHolder = getHolder();
        mydHolder.addCallback(this);
        mydHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void surfaceCreated(SurfaceHolder holder) {
       
    	Mydcamera = Camera.open();
        try {
        	Mydcamera.setPreviewDisplay(holder);
			
			
        	Mydcamera.setPreviewCallback(new PreviewCallback() {

				public void onPreviewFrame(byte[] data, Camera arg1) {
					FileOutputStream outStream = null;
					try {
						outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));	
						outStream.write(data);
						outStream.close();
						
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
					}
						Preview.this.invalidate();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
      
    	Mydcamera.stopPreview();
    	Mydcamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
       
        Camera.Parameters parameters = Mydcamera.getParameters();
        parameters.setPreviewSize(w, h);
        Mydcamera.setParameters(parameters);
        Mydcamera.startPreview();
    }

    @Override
    public void draw(Canvas canvas) {
    		super.draw(canvas);
    		Paint p= new Paint(Color.RED);
    	
    		canvas.drawText("PREVIEW", canvas.getWidth()/2, canvas.getHeight()/2, p );
    }
}