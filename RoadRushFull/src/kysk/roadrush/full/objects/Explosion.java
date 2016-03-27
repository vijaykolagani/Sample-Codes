package kysk.roadrush.full.objects;

import kysk.roadrush.full.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Explosion {
	
	private Bitmap explosion[];
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private boolean startExplosion;
	private boolean finishExplosion;
	private int currentFrame = 0;
	private int frameCounter = 0;
	
	public Explosion(Context context) {
		finishExplosion = false;
		explosion = new Bitmap[5];
		explosion[0] = BitmapFactory.decodeResource(context.getResources(), R.drawable.comic_explosion_01);
		explosion[1] = BitmapFactory.decodeResource(context.getResources(), R.drawable.comic_explosion_02);
		explosion[2] = BitmapFactory.decodeResource(context.getResources(), R.drawable.comic_explosion_03);
		explosion[3] = BitmapFactory.decodeResource(context.getResources(), R.drawable.comic_explosion_04);
		explosion[4] = BitmapFactory.decodeResource(context.getResources(), R.drawable.comic_explosion_05);
	}
	
	public void draw(Canvas canvas) {
		if (isStartExplosion()) {
			setFinishExplosion(false);
			width = explosion[currentFrame].getWidth();
			height = explosion[currentFrame].getHeight();
			
			src = new Rect(0, 0, width, height);
			dst = new Rect(startX, startY, startX + width / 3, startY + height / 3);
			
			if (frameCounter == 3) {
				frameCounter = 0;
				currentFrame++;				
			}
			frameCounter++;
		}
		if (currentFrame == 5) {
			currentFrame = 0;
			setFinishExplosion(true);
			setStartExplosion(false);
		}
		if (!isFinishExplosion()) {
			canvas.drawBitmap(explosion[currentFrame], src, dst, null);			
		}
	}

	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public boolean isStartExplosion() {
		return startExplosion;
	}

	public void setStartExplosion(boolean startExplosion) {
		this.startExplosion = startExplosion;
	}

	public boolean isFinishExplosion() {
		return finishExplosion;
	}

	public void setFinishExplosion(boolean finishExplosion) {
		this.finishExplosion = finishExplosion;
	}

	public int getCurrentFrame() {
		return currentFrame;
	}
}
