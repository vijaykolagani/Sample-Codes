package com.mit.mit_flashlight;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.widget.ToggleButton;

public class FlashLight extends Activity {
  private SurfaceView preview=null;
  private SurfaceHolder previewHolder=null;
  private Camera camera=null;
  private boolean inPreview=false;
  private boolean cameraConfigured=false;
  Button flash,capture;
  Parameters params;
  ToggleButton tg;
  
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    setContentView(R.layout.activity_flash_light);
    
    preview=(SurfaceView)findViewById(R.id.preview);
    capture = (Button) findViewById(R.id.capture);
    tg = (ToggleButton)findViewById(R.id.toggleButton1);
    final boolean hasFlash = getApplicationContext().getPackageManager()
            .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
     
    tg.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(!tg.isChecked()){
				 params = camera.getParameters();
			        params.setFlashMode(Parameters.FLASH_MODE_OFF);
			        camera.setParameters(params);
			        
			}
			else{
				if(!hasFlash){
					Toast.makeText(getApplicationContext(), "No Flash!!!!", 1).show();
				}
				else{
					params = camera.getParameters();
			        params.setFlashMode(Parameters.FLASH_MODE_TORCH);
			        camera.setParameters(params);
			        camera.startPreview();
				}
			}
		}
	});
    capture.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
	});
    
 
    previewHolder=preview.getHolder();
    previewHolder.addCallback(surfaceCallback);
    previewHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
  }
  public Bitmap toGrayscale(Bitmap bmpOriginal)
  {        
      int width, height;
      height = bmpOriginal.getHeight();
      width = bmpOriginal.getWidth();    

      Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
      Canvas c = new Canvas(bmpGrayscale);
      Paint paint = new Paint();
      ColorMatrix cm = new ColorMatrix();
      cm.setSaturation(0);
      ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
      paint.setColorFilter(f);
      c.drawBitmap(bmpOriginal, 0, 0, paint);
      return bmpGrayscale;
  }
  
  @Override
  public void onResume() {
    super.onResume();
    
    camera=Camera.open();
    startPreview();
  }
    
  @Override
  public void onPause() {
    if (inPreview) {
      camera.stopPreview();
    }
    
    camera.release();
    camera=null;
    inPreview=false;
          
    super.onPause();
  }
  
  private Camera.Size getBestPreviewSize(int width, int height,
                                         Camera.Parameters parameters) {
    Camera.Size result=null;
    
    for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
      if (size.width<=width && size.height<=height) {
        if (result==null) {
          result=size;
        }
        else {
          int resultArea=result.width*result.height;
          int newArea=size.width*size.height;
          
          if (newArea>resultArea) {
            result=size;
          }
        }
      }
    }
    
    return(result);
  }
  
  private void initPreview(int width, int height) {
    if (camera!=null && previewHolder.getSurface()!=null) {
      try {
        camera.setPreviewDisplay(previewHolder);
      }
      catch (Throwable t) {
        Log.e("PreviewDemo-surfaceCallback",
              "Exception in setPreviewDisplay()", t);
        Toast
          .makeText(this, t.getMessage(), Toast.LENGTH_LONG)
          .show();
      }

      if (!cameraConfigured) {
        Camera.Parameters parameters=camera.getParameters();
        Camera.Size size=getBestPreviewSize(width, height,
                                            parameters);
        
        if (size!=null) {
          parameters.setPreviewSize(size.width, size.height);
          camera.setParameters(parameters);
          cameraConfigured=true;
        }
      }
    }
  }
  
  private void startPreview() {
    if (cameraConfigured && camera!=null) {
      camera.startPreview();
      inPreview=true;
    }
  }
  
  SurfaceHolder.Callback surfaceCallback=new SurfaceHolder.Callback() {
    public void surfaceCreated(SurfaceHolder holder) {
      // no-op -- wait until surfaceChanged()
    }
    
    public void surfaceChanged(SurfaceHolder holder,
                               int format, int width,
                               int height) {
      initPreview(width, height);
      startPreview();
    }
    
    public void surfaceDestroyed(SurfaceHolder holder) {
      // no-op
    }
  };
}