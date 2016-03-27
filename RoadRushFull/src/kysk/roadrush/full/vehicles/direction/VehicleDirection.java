package kysk.roadrush.full.vehicles.direction;

import android.graphics.Canvas;

public interface VehicleDirection {
	void vehiclePath(int direction, int width, int height, Canvas canvas);
	void rightPath(int path, int width, int height, Canvas canvas);
	void leftPath(int path, int width, int height, Canvas canvas);
	void topPath(int path, int width, int height, Canvas canvas);
	void bottomPath(int path, int width, int height, Canvas canvas);
}
