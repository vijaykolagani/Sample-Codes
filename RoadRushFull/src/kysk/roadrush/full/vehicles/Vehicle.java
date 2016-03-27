package kysk.roadrush.full.vehicles;

import android.graphics.Canvas;

public interface Vehicle {
	void draw(Canvas canvas);
	void onTouch(int eventX, int eventY);
	void onSwipe(int oldX, int oldY, int eventX, int eventY);
	boolean isBlocked(int multiplier, int direction);
	int getStartValue(int multiplier);
	int getStopValue(int direction);
	boolean detectCollision(int direction, int startX, int startY, int width, int height);
	void loadVehicle(Canvas canvas, int d1, int d2, int d3, int d4, int d5, int d6, int d7, int d8, int d9,
			int d10, int d11, int d12, int d13, int d14, int d15);
}
