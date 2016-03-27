package com.savetheeggs.menu;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MenuLoop extends Thread {

	static final long FPS = 30;
	private MenuView view;
	private boolean running = false;

	public MenuLoop(SurfaceHolder surfaceHolder, MenuView view) {
		this.view = view;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public boolean isRunning() {
		return running;
	}

	@Override
	public void run() {
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		while (running) {
			Canvas canvas = null;
			startTime = System.currentTimeMillis();
			try {
				canvas = view.getHolder().lockCanvas();
				synchronized (view.getHolder()) {
					view.draw(canvas);																				
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					view.getHolder().unlockCanvasAndPost(canvas);
				}
			}
			sleepTime = ticksPS
					- (System.currentTimeMillis() - startTime);
			try {
				if (sleepTime > 0) {
					sleep(sleepTime);
				} else {
					sleep(10);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
		
}
