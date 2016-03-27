package kysk.roadrush.full;

import android.graphics.Canvas;

public class GameLoop extends Thread {
	
	static final long FPS = 30;
	private GameView view;
	private boolean running = false;
	private boolean isPaused = false;
	
	public GameLoop(GameView view) {
		this.view = view;
	}
	
	public void setRunning(boolean running) {
		this.running = running;
	}
		
	public boolean isPaused() {
		return isPaused;
	}

	public void setPaused(boolean isPaused) {
		this.isPaused = isPaused;
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
					if (!isPaused) {
						view.draw(canvas);						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					view.getHolder().unlockCanvasAndPost(canvas);
				}
			}
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
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