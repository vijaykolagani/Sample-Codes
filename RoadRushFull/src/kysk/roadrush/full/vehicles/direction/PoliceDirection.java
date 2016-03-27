package kysk.roadrush.full.vehicles.direction;

import kysk.roadrush.full.vehicles.Police;
import android.graphics.Canvas;

public class PoliceDirection implements VehicleDirection {
	
	private Police police;
	private int track;
	
	public PoliceDirection(Police police, int track) {
		this.police = police;
		this.track = track;
	}
	
	public void vehiclePath(int direction, int width, int height, Canvas canvas) {
		switch (direction) {
		case 0:
			leftPath(0, width, height, canvas);
			break;
		case 1:
			leftPath(1, width, height, canvas);
			break;
		case 2:
			leftPath(2, width, height, canvas);
			break;
		case 3:
			leftPath(3, width, height, canvas);
			break;
		case 4:
			rightPath(0, width, height, canvas);
			break;
		case 5:
			rightPath(1, width, height, canvas);
			break;
		case 6:
			rightPath(2, width, height, canvas);
			break;
		case 7:
			rightPath(3, width, height, canvas);
			break;
		case 8:
			topPath(0, width, height, canvas);
			break;
		case 9:
			topPath(1, width, height, canvas);
			break;
		case 10:
			topPath(2, width, height, canvas);
			break;
		case 11:
			topPath(3, width, height, canvas);
			break;
		case 12:
			bottomPath(0, width, height, canvas);
			break;
		case 13:
			bottomPath(1, width, height, canvas);
			break;
		case 14:
			bottomPath(2, width, height, canvas);
			break;
		case 15:
			bottomPath(3, width, height, canvas);
			break;
		}
	}
	
	public void rightPath(int path, int width, int height, Canvas canvas) {
		police.setStartX(canvas.getWidth() + width);
		switch (track) {
		case 1:
		case 2:
		case 3:
			switch (path) {
			case 0:
				police.setStartY(canvas.getHeight() / 2 + height / 2 + 10);
				break;
			case 1:
				police.setStartY(canvas.getHeight() / 2 + 5);
				break;
			case 2:
				police.setStartY(canvas.getHeight() / 2 - height / 2 - 2);
				break;
			case 3:
				police.setStartY(canvas.getHeight() / 2 - height - 10);
				break;
			}
			break;
		case 4:
			switch (path) {
			case 0:
				police.setStartY(canvas.getHeight() / 2 + 2 * height - 47);
				break;
			case 1:
				police.setStartY(canvas.getHeight() / 2 + height / 2 + 5);
				break;
			case 2:
				police.setStartY(canvas.getHeight() / 2 - height - 2);
				break;
			case 3:
				police.setStartY(canvas.getHeight() / 2 - 2 * height + 25);
				break;
			}
			break;
		}
	}
	
	public void leftPath(int path, int width, int height, Canvas canvas) {
		police.setStartX(0 - width);
		switch (track) {
		case 1:
		case 2:
		case 3:
			switch (path) {
			case 0:
				police.setStartY(canvas.getHeight() / 2 + height / 2 + 10);
				break;
			case 1:
				police.setStartY(canvas.getHeight() / 2 + 5);
				break;
			case 2:
				police.setStartY(canvas.getHeight() / 2 - height / 2 - 2);
				break;
			case 3:
				police.setStartY(canvas.getHeight() / 2 - height - 10);
				break;
			}
			break;
		case 4:
			switch (path) {
			case 0:
				police.setStartY(canvas.getHeight() / 2 + 2 * height - 47);
				break;
			case 1:
				police.setStartY(canvas.getHeight() / 2 + height / 2 + 5);
				break;
			case 2:
				police.setStartY(canvas.getHeight() / 2 - height - 2);
				break;
			case 3:
				police.setStartY(canvas.getHeight() / 2 - 2 * height + 25);
				break;
			}
			break;
		}
	}
	
	public void topPath(int path, int width, int height, Canvas canvas) {
		police.setStartY(0 - height);
		switch (track) {
		case 1:
		case 2:
			switch (path) {
			case 0:
				police.setStartX(canvas.getWidth() / 2 - width - 20);
				break;
			case 1:
				police.setStartX(canvas.getWidth() / 2 - width / 2 - 10);
				break;
			case 2:
				police.setStartX(canvas.getWidth() / 2 + 10);
				break;
			case 3:
				police.setStartX(canvas.getWidth() / 2 + width);
				break;
			}
			break;
		case 3:
		case 4:
			switch (path) {
			case 0:
				police.setStartX(canvas.getWidth() / 2 - 2 * width - 15);
				break;
			case 1:
				police.setStartX(canvas.getWidth() / 2 - width - 25);
				break;
			case 2:
				police.setStartX(canvas.getWidth() / 2 + width);
				break;
			case 3:
				police.setStartX(canvas.getWidth() / 2 + 2 * width - 10);
				break;
			}
			break;
		}
	}
	
	public void bottomPath(int path, int width, int height, Canvas canvas) {
		police.setStartY(canvas.getHeight() + height);
		switch (track) {
		case 1:
		case 2:
			switch (path) {
			case 0:
				police.setStartX(canvas.getWidth() / 2 - width - 20);
				break;
			case 1:
				police.setStartX(canvas.getWidth() / 2 - width / 2 - 10);
				break;
			case 2:
				police.setStartX(canvas.getWidth() / 2 + 10);
				break;
			case 3:
				police.setStartX(canvas.getWidth() / 2 + width);
				break;
			}
			break;
		case 3:
		case 4:
			switch (path) {
			case 0:
				police.setStartX(canvas.getWidth() / 2 - 2 * width - 15);
				break;
			case 1:
				police.setStartX(canvas.getWidth() / 2 - width - 25);
				break;
			case 2:
				police.setStartX(canvas.getWidth() / 2 + width);
				break;
			case 3:
				police.setStartX(canvas.getWidth() / 2 + 2 * width - 10);
				break;
			}
			break;
		}
	}
}
