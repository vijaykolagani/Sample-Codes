package kysk.roadrush.full.vehicles;

import kysk.roadrush.full.Constants;
import kysk.roadrush.full.R;
import kysk.roadrush.full.sounds.SoundManager;
import kysk.roadrush.full.vehicles.direction.BettleBlueDirection;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class BettleBlue implements Vehicle {

	private Bitmap leftVehicle, rightVehicle, topVehicle, bottomVehicle, vehicle;
	private Bitmap lightLeft, lightRight, lightTop, lightBottom;
	private Rect src;
	private Rect dst;
	private int width, height;
	private int startX, startY;
	private boolean available;
	private boolean isStopped;
	private boolean isSwiped;
	private int left, top, right, bottom;
	private int speed;
	private int direction;
	private boolean isSameDirection;
	private boolean isAvailableFlag;
	private boolean trafficStopped;
	private boolean isTouchStopped;
	private int multiplier;
	private int stopValue;
	private int score = 0;
	private int swipes = 0;
	private BettleBlueDirection vehicleDirection;
	private int lightCounter = 0;
	private SoundManager soundManager;
	
	public BettleBlue(Context context, SoundManager soundManager, int track) {
		available = false;
		isAvailableFlag = false;
		isStopped = false;
		isSameDirection = false;
		direction = -1;
		multiplier = 0;
		stopValue = 0;
		this.soundManager = soundManager;
		vehicleDirection = new BettleBlueDirection(this, track);
		leftVehicle = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_blue_left);
		rightVehicle = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_blue_right);
		topVehicle = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_blue_top);
		bottomVehicle = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_blue_bottom);
		
		lightLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_lights_left);
		lightRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_lights_right);
		lightTop = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_lights_top);
		lightBottom = BitmapFactory.decodeResource(context.getResources(), R.drawable.bettle_lights_bottom);
	}

	public void draw(Canvas canvas) {
		if (!isAvailable()) {
			switch (direction) {
			case 0:
			case 1:
			case 2:
			case 3:
				vehicle = leftVehicle;
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				vehicle = rightVehicle;
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				vehicle = topVehicle;
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				vehicle = bottomVehicle;
				break;
			}
			width = vehicle.getWidth();
			height = vehicle.getHeight();
			left = 0;
			top = 0;
			right = width;
			bottom = height;
			vehicleDirection.vehiclePath(direction, width, height, canvas);
			if (isSameDirection) {
				switch (direction) {
				case 0:
				case 1:
				case 2:
				case 3:
					if (multiplier < 0) {
						startX = multiplier - width;						
					} else {
						startX -= (multiplier + width);
					}
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					if (multiplier > canvas.getWidth()) {
						startX = multiplier + width;
					} else {
						startX += (multiplier + width);						
					}
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					if (multiplier < 0) {
						startY = multiplier - height;
					} else {
						startY -= (multiplier + height);	
					}
					break;
				case 12:
				case 13:
				case 14:
				case 15:
					if (multiplier > canvas.getHeight()) {
						startY = multiplier + height;
					} else {
						startY += (multiplier + width);	
					}
					break;
				}
			}
			setSameDirection(false);
			setSwiped(false);
			setAvailable(true);
			setSpeed(Constants.NORMAL_SPEED);
		}
		
		if (!isStopped() || isTrafficStopped()) {
			switch (direction) {
			case 0:
			case 1:
			case 2:
			case 3:
				if (isTrafficStopped()) {
					if (startX <= stopValue - width / 2 - 5) {
						startX += speed;
					}
				} else {
					if (startX <= canvas.getWidth() + width / 2) {
						startX += speed;
					} else {
						score++;
						setAvailable(false);
						setAvailableFlag(false);
						setMultiplier(0);
						setDirection(-1);
						setStartX(0);
						setStartY(0);
						this.width = 0;
						this.height = 0;
					}					
				}
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				if (isTrafficStopped()) {
					if (startX >= stopValue + width / 2 + 5) {
						startX -= speed;
					}					
				} else {
					if (startX >= 0 - width / 2) {
						startX -= speed;
					} else {
						score++;
						setAvailable(false);
						setAvailableFlag(false);
						setMultiplier(0);
						setDirection(-1);
						setStartX(0);
						setStartY(0);
						this.width = 0;
						this.height = 0;
					}							
				}
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				if (isTrafficStopped()) {
					if (startY <= stopValue - height / 2 - 5) {
						startY += speed;
					}					
				} else {
					if (startY <= canvas.getHeight() + height / 2) {
						startY += speed;
					} else {
						score++;
						setAvailable(false);
						setAvailableFlag(false);
						setMultiplier(0);
						setDirection(-1);
						setStartX(0);
						setStartY(0);
						this.width = 0;
						this.height = 0;
					}								
				}
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				if (isTrafficStopped()) {
					if (startY >= stopValue + height / 2 + 5) {
						startY -= speed;
					}					
				} else {
					if (startY >= 0 - height / 2) {
						startY -= speed;
					} else {
						score++;
						setAvailable(false);
						setAvailableFlag(false);
						setMultiplier(0);
						setDirection(-1);
						setStartX(0);
						setStartY(0);
						this.width = 0;
						this.height = 0;
					}								
				}
				break;
			}
		}
		
		if (lightCounter % 60 == 0) {
			if (isTouchStopped()) {
				switch (direction) {
				case 0:
				case 1:
				case 2:
				case 3:
					src = new Rect(0, 0, lightLeft.getWidth(), lightLeft.getHeight());
					dst = new Rect(startX + width / 2 - 5, startY - 5, startX + width / 2 + lightLeft.getWidth() / 2, 
							startY + lightLeft.getHeight() / 2);
					canvas.drawBitmap(lightLeft, src, dst, null);
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					src = new Rect(0, 0, lightRight.getWidth(), lightRight.getHeight());
					dst = new Rect(startX - lightRight.getWidth() / 2, startY - 5, startX + 5, 
							startY + lightRight.getHeight() / 2);
					canvas.drawBitmap(lightRight, src, dst, null);
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					src = new Rect(0, 0, lightTop.getWidth(), lightTop.getHeight());
					dst = new Rect(startX - 5, startY + height / 2 - 5, startX + lightTop.getWidth() / 2, 
							startY + height / 2 + lightTop.getHeight() / 2);
					canvas.drawBitmap(lightTop, src, dst, null);
					break;
				case 12:
				case 13:
				case 14:
				case 15:
					src = new Rect(0, 0, lightBottom.getWidth(), lightBottom.getHeight());
					dst = new Rect(startX - 5, startY -  lightTop.getHeight() / 2, startX + lightBottom.getWidth() / 2, 
							startY + 5);
					canvas.drawBitmap(lightBottom, src, dst, null);
					break;
				}
				soundManager.playBettleHorn();
			}
			if (lightCounter == 180) {
				lightCounter = 0;
				setStopped(false);
				setTouchStopped(false);
			}
		}
		
		lightCounter++;
		
		src = new Rect(left, top, right, bottom);
		dst = new Rect(startX, startY, startX + width / 2, startY + height / 2);
		canvas.drawBitmap(vehicle, src, dst, null);
	}

	public void onTouch(int eventX, int eventY) {
		if (eventX >= startX && eventX <= startX + width / 2
				&& eventY >= startY && eventY <= startY + height / 2) {
			if (!isStopped()) {
				setSpeed(Constants.NORMAL_SPEED);
				setStopped(true);
				setSwiped(false);
			} else {
				setStopped(false);
			}
		}
	}	
	
	public void onSwipe(int oldX, int oldY, int eventX, int eventY) {
		switch (direction) {
		case 0:
		case 1:
		case 2:
		case 3:
			if (eventX >= startX - 10 && eventX <= startX + width / 2 + 10 
					&& eventY >= startY - 5 && eventY <= startY + height / 2 + 5) {
				if (!isStopped()) {
					setSpeed(Constants.NORMAL_SPEED);
					setStopped(true);
					setSwiped(false);
					setTouchStopped(true);
					soundManager.playBrakeSound();
					lightCounter = 1;
				} else {
					setStopped(false);
					setTouchStopped(false);
				}				
			} else if (eventX > oldX + 25 && oldX >= startX - 25 && oldX <= startX + width / 2 + 25
					&& oldY >= startY && oldY <= startY + height / 2) {
				if (!isSwiped()) {
					setSpeed(Constants.SWIPE_SPEED);
					setSwiped(true);
					setStopped(false);
					setTouchStopped(false);
					soundManager.playSwipeSound();
					swipes++;
				} else {
					setSwiped(false);
				}
			}
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			if (eventX >= startX - 10 && eventX <= startX + width / 2 + 10 
					&& eventY >= startY - 5 && eventY <= startY + height / 2 + 5) {
				if (!isStopped()) {
					setSpeed(Constants.NORMAL_SPEED);
					setStopped(true);
					setSwiped(false);
					setTouchStopped(true);
					lightCounter = 1;
					soundManager.playBrakeSound();
				} else {
					setStopped(false);
					setTouchStopped(false);
				}				
			} else if (eventX < oldX - 25 && oldX >= startX - 25 && oldX <= startX + width / 2 + 25
					&& oldY >= startY && oldY <= startY + height / 2) {
				if (!isSwiped()) {
					setSpeed(Constants.SWIPE_SPEED);
					setSwiped(true);
					setStopped(false);
					setTouchStopped(false);
					soundManager.playSwipeSound();
					swipes++;
				} else {
					setSwiped(false);
				}
			}
			break;
		case 8:
		case 9:
		case 10:
		case 11:
			if (eventX >= startX - 5 && eventX <= startX + width / 2 + 5 
					&& eventY >= startY - 10 && eventY <= startY + height / 2 + 10) {
				if (!isStopped()) {
					setSpeed(Constants.NORMAL_SPEED);
					setStopped(true);
					setSwiped(false);
					setTouchStopped(true);
					lightCounter = 1;
					soundManager.playBrakeSound();
				} else {
					setStopped(false);
					setTouchStopped(false);
				}				
			} else if (eventY > oldY + 25 && oldX >= startX && oldX <= startX + width / 2
					&& oldY >= startY - 25 && oldY <= startY + height / 2 + 25) {
				if (!isSwiped()) {
					setSpeed(Constants.SWIPE_SPEED);
					setSwiped(true);
					setStopped(false);
					setTouchStopped(false);
					soundManager.playSwipeSound();
					swipes++;
				} else {
					setSwiped(false);
				}
			}
			break;
		case 12:
		case 13:
		case 14:
		case 15:
			if (eventX >= startX - 5 && eventX <= startX + width / 2 + 5 
					&& eventY >= startY - 10 && eventY <= startY + height / 2 + 10) {
				if (!isStopped()) {
					setSpeed(Constants.NORMAL_SPEED);
					setStopped(true);
					setSwiped(false);
					setTouchStopped(true);
					lightCounter = 1;
					soundManager.playBrakeSound();
				} else {
					setStopped(false);
					setTouchStopped(false);
				}				
			} else if (eventY < oldY - 25 && oldX >= startX && oldX <= startX + width / 2
					&& oldY >= startY - 25 && oldY <= startY + height / 2 + 25) {
				if (!isSwiped()) {
					setSpeed(Constants.SWIPE_SPEED);
					setSwiped(true);
					setStopped(false);
					setTouchStopped(false);
					soundManager.playSwipeSound();
					swipes++;
				} else {
					setSwiped(false);
				}
			}
			break;
		}
	}
	
	public int getSwipes() {
		return swipes;
	}

	public int getScore() {
		return score;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public int getStartX() {
		return startX;
	}

	public int getStartY() {
		return startY;
	}
	
	public void setStartX(int startX) {
		this.startX = startX;
	}

	public void setStartY(int startY) {
		this.startY = startY;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isStopped() {
		return isStopped;
	}

	public void setStopped(boolean isStopped) {
		this.isStopped = isStopped;
	}

	public boolean isSwiped() {
		return isSwiped;
	}

	public void setSwiped(boolean isSwiped) {
		this.isSwiped = isSwiped;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public void setDirection(int direction) {
		this.direction = direction;
	}

	public int getDirection() {
		return direction;
	}

	public boolean isSameDirection() {
		return isSameDirection;
	}

	public void setSameDirection(boolean isSameDirection) {
		this.isSameDirection = isSameDirection;
	}

	public boolean isAvailableFlag() {
		return isAvailableFlag;
	}

	public void setAvailableFlag(boolean isAvailableFlag) {
		this.isAvailableFlag = isAvailableFlag;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(int multiplier) {
		this.multiplier = multiplier;
	}

	public boolean isTrafficStopped() {
		return trafficStopped;
	}

	public void setTrafficStopped(boolean trafficStopped) {
		this.trafficStopped = trafficStopped;
	}

	public void setStopValue(int stopValue) {
		this.stopValue = stopValue;
	}

	public int getStopValue() {
		return stopValue;
	}

	public boolean isTouchStopped() {
		return isTouchStopped;
	}

	public void setTouchStopped(boolean isTouchStopped) {
		this.isTouchStopped = isTouchStopped;
	}

	public void loadVehicle(Canvas canvas, int d1, int d2, int d3, int d4, int d5, int d6,
			int d7, int d8, int d9, int d10, int d11, int d12, int d13,
			int d14, int d15) {
		if (!isAvailable()) {
			switch (direction) { 
			case 0:
				if (d1 != 4
						&& d2 != 4
						&& d3 != 4
						&& d4 != 4
						&& d5 != 4
						&& d6 != 4
						&& d7 != 4
						&& d8 != 4
						&& d9 != 4 
						&& d10 != 4
						&& d11 != 4
						&& d12 != 4
						&& d13 != 4
						&& d14 != 4
						&& d15 != 4) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 1:
				if (d1 != 5
						&& d2 != 5
						&& d3 != 5
						&& d4 != 5
						&& d5 != 5
						&& d6 != 5
						&& d7 != 5
						&& d8 != 5
						&& d9 != 5 
						&& d10 != 5
						&& d11 != 5
						&& d12 != 5
						&& d13 != 5
						&& d14 != 5
						&& d15 != 5) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 2:
				if (d1 != 6
						&& d2 != 6
						&& d3 != 6
						&& d4 != 6
						&& d5 != 6
						&& d6 != 6
						&& d7 != 6
						&& d8 != 6
						&& d9 != 6 
						&& d10 != 6
						&& d11 != 6
						&& d12 != 6
						&& d13 != 6
						&& d14 != 6
						&& d15 != 6) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 3:
				if (d1 != 7
						&& d2 != 7
						&& d3 != 7
						&& d4 != 7
						&& d5 != 7
						&& d6 != 7
						&& d7 != 7
						&& d8 != 7
						&& d9 != 7 
						&& d10 != 7
						&& d11 != 7
						&& d12 != 7
						&& d13 != 7
						&& d14 != 7
						&& d15 != 7) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 4:
				if (d1 != 0
						&& d2 != 0
						&& d3 != 0
						&& d4 != 0
						&& d5 != 0
						&& d6 != 0
						&& d7 != 0
						&& d8 != 0
						&& d9 != 0 
						&& d10 != 0
						&& d11 != 0
						&& d12 != 0
						&& d13 != 0
						&& d14 != 0
						&& d15 != 0) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 5:
				if (d1 != 1
						&& d2 != 1
						&& d3 != 1
						&& d4 != 1
						&& d5 != 1
						&& d6 != 1
						&& d7 != 1
						&& d8 != 1
						&& d9 != 1 
						&& d10 != 1
						&& d11 != 1
						&& d12 != 1
						&& d13 != 1
						&& d14 != 1
						&& d15 != 1) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 6:
				if (d1 != 2
						&& d2 != 2
						&& d3 != 2
						&& d4 != 2
						&& d5 != 2
						&& d6 != 2
						&& d7 != 2
						&& d8 != 2
						&& d9 != 2 
						&& d10 != 2
						&& d11 != 2
						&& d12 != 2
						&& d13 != 2
						&& d14 != 2
						&& d15 != 2) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 7:
				if (d1 != 3
						&& d2 != 3
						&& d3 != 3
						&& d4 != 3
						&& d5 != 3
						&& d6 != 3
						&& d7 != 3
						&& d8 != 3
						&& d9 != 3 
						&& d10 != 3
						&& d11 != 3
						&& d12 != 3
						&& d13 != 3
						&& d14 != 3
						&& d15 != 3) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 8:
				if (d1 != 12
						&& d2 != 12
						&& d3 != 12
						&& d4 != 12
						&& d5 != 12
						&& d6 != 12
						&& d7 != 12
						&& d8 != 12
						&& d9 != 12 
						&& d10 != 12
						&& d11 != 12
						&& d12 != 12
						&& d13 != 12
						&& d14 != 12
						&& d15 != 12) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 9:
				if (d1 != 13
						&& d2 != 13
						&& d3 != 13
						&& d4 != 13
						&& d5 != 13
						&& d6 != 13
						&& d7 != 13
						&& d8 != 13
						&& d9 != 13 
						&& d10 != 13
						&& d11 != 13
						&& d12 != 13
						&& d13 != 13
						&& d14 != 13
						&& d15 != 13) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 10:
				if (d1 != 14
						&& d2 != 14
						&& d3 != 14
						&& d4 != 14
						&& d5 != 14
						&& d6 != 14
						&& d7 != 14
						&& d8 != 14
						&& d9 != 14 
						&& d10 != 14
						&& d11 != 14
						&& d12 != 14
						&& d13 != 14
						&& d14 != 14
						&& d15 != 14) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 11:
				if (d1 != 15
						&& d2 != 15
						&& d3 != 15
						&& d4 != 15
						&& d5 != 15
						&& d6 != 15
						&& d7 != 15
						&& d8 != 15
						&& d9 != 15 
						&& d10 != 15
						&& d11 != 15
						&& d12 != 15
						&& d13 != 15
						&& d14 != 15
						&& d15 != 15) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 12:
				if (d1 != 8
						&& d2 != 8
						&& d3 != 8
						&& d4 != 8
						&& d5 != 8
						&& d6 != 8
						&& d7 != 8
						&& d8 != 8
						&& d9 != 8 
						&& d10 != 8
						&& d11 != 8
						&& d12 != 8
						&& d13 != 8
						&& d14 != 8
						&& d15 != 8) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 13:
				if (d1 != 9
						&& d2 != 9
						&& d3 != 9
						&& d4 != 9
						&& d5 != 9
						&& d6 != 9
						&& d7 != 9
						&& d8 != 9
						&& d9 != 9 
						&& d10 != 9
						&& d11 != 9
						&& d12 != 9
						&& d13 != 9
						&& d14 != 9
						&& d15 != 9) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 14:
				if (d1 != 10
						&& d2 != 10
						&& d3 != 10
						&& d4 != 10
						&& d5 != 10
						&& d6 != 10
						&& d7 != 10
						&& d8 != 10
						&& d9 != 10 
						&& d10 != 10
						&& d11 != 10
						&& d12 != 10
						&& d13 != 10
						&& d14 != 10
						&& d15 != 10) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			case 15:
				if (d1 != 11
						&& d2 != 11
						&& d3 != 11
						&& d4 != 11
						&& d5 != 11
						&& d6 != 11
						&& d7 != 11
						&& d8 != 11
						&& d9 != 11 
						&& d10 != 11
						&& d11 != 11
						&& d12 != 11
						&& d13 != 11
						&& d14 != 11
						&& d15 != 11) {
					draw(canvas);
				} else {
					setAvailableFlag(false);
				}
				break;
			}			
		} else {
			draw(canvas);
		}
	}
	
	public int getStartValue(int multiplier) {
		switch (direction) {
		case 0:
		case 1:
		case 2:
		case 3:
			if (multiplier == 0) {
				multiplier = startX;
			} else if (startX < multiplier) {
				multiplier = startX;
			}
			break;
		case 4:
		case 5:
		case 6:
		case 7:
			if (multiplier == 0) {
				multiplier = startX;
			} else if (startX > multiplier) {
				multiplier = startX;
			}
			break;
		case 8:
		case 9:
		case 10:
		case 11:
			if (multiplier == 0) {
				multiplier = startY;
			} else if (startY < multiplier) {
				multiplier = startY;
			}
			break;
		case 12:
		case 13:
		case 14:
		case 15:
			if (multiplier == 0) {
				multiplier = startY;
			} else if (startY > multiplier) {
				multiplier = startY;
			}
			break;
		}
		return multiplier;
	}

	public boolean isBlocked(int multiplier, int direction) {
		boolean blocked = false;
		switch (direction) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			if (startX == multiplier
					&& direction == this.direction) {
					blocked = true;					
			}
			break;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			if (startY == multiplier
					&& direction == this.direction) {
				blocked = true;					
			}
			break;
		}
		return blocked;
	}

	public int getStopValue(int direction) {
		int stopValue = 0;
		switch (direction) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
			if (direction == this.direction) {
				stopValue = startX;				
			}
			break;
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
			if (direction == this.direction) {
				stopValue = startY;
			}
			break;
		}
		return stopValue;
	}
	
	public boolean detectCollision(int direction, int startX, int startY,
			int width, int height) {
		
		boolean collided = false;

		if (width != 0 && height != 0
				&& this.width != 0 && this.height != 0) {
			switch (this.direction) {
			case 0:
			case 1:
			case 2:
			case 3:
				switch (direction) {
				case 0:
				case 1:
				case 2:
				case 3:
					if (this.direction == direction) {
						if (startX < this.startX) {
							if (startX + width == this.startX) {
								collided = true;
							}
						} else {
							if (startX == this.startX + this.width / 2) {
								collided = true;
							}
						}					
					}
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					if (this.direction == direction + 4) {
						if (startX <= this.startX + this.width / 2) {
							collided = true;
						}					
					}
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					if (startX <= this.startX + this.width / 2
							&& startX + width >= this.startX
							&& startY + height >= this.startY
							&& startY <= this.startY + this.height / 2) {
						collided = true;
					}
					break;
				case 12:
				case 13:
				case 14:
				case 15:
					if (startX <= this.startX + this.width / 2
							&& startX + width >= this.startX
							&& startY <= this.startY + this.height / 2
							&& startY + height >= this.startY) {
						collided = true;
					}
					break;
				}
				break;
			case 4:
			case 5:
			case 6:
			case 7:
				switch (direction) {
				case 0:
				case 1:
				case 2:
				case 3:
					if (this.direction == direction - 4) {
						if (startX + width >= this.startX) {
							collided = true;
						}					
					}
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					if (this.direction == direction) {
						if (startX < this.startX) {
							if (startX + width == this.startX) {
								collided = true;
							}
						} else {
							if (startX == this.startX + this.width / 2) {
								collided = true;
							}
						}					
					}
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					if (startX + width >= this.startX
							&& startX <= this.startX + this.width / 2
							&& startY + height >= this.startY
							&& startY <= this.startY + this.height / 2) {
						collided = true;
					}
					break;
				case 12:
				case 13:
				case 14:
				case 15:
					if (startX + width >= this.startX
							&& startX <= this.startX + this.width / 2
							&& startY <= this.startY + this.height / 2
							&& startY + height >= this.startY) {
						collided = true;
					}
					break;
				}
				break;
			case 8:
			case 9:
			case 10:
			case 11:
				switch (direction) {
				case 0:
				case 1:
				case 2:
				case 3:
					if (startX + width >= this.startX
							&& startX <= this.startX + this.width / 2
							&& startY <= this.startY + this.height / 2
							&& startY + height >= this.startY) {
						collided = true;
					}
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					if (startX <= this.startX + this.width / 2
							&& startX + width >= this.startX
							&& startY <= this.startY + this.height / 2
							&& startY + height >= this.startY) {
						collided = true;
					}
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					if (this.direction == direction) {
						if (startY < this.startY) {
							if (startY + height == this.startY) {
								collided = true;
							}
						} else {
							if (startY == this.startY + this.height / 2) {
								collided = true;
							}
						}					
					}
					break;
				case 12:
				case 13:
				case 14:
				case 15:
					if (this.direction == direction + 4) {
						if (startY <= this.startY + this.height / 2) {
							collided = true;
						}					
					}
					break;
				}
				break;
			case 12:
			case 13:
			case 14:
			case 15:
				switch (direction) {
				case 0:
				case 1:
				case 2:
				case 3:
					if (startX + width >= this.startX
							&& startX <= this.startX + this.width / 2
							&& startY + height >= this.startY
							&& startY <= this.startY + this.height / 2) {
						collided = true;
					}
					break;
				case 4:
				case 5:
				case 6:
				case 7:
					if (startX <= this.startX + this.width / 2
							&& startX + width >= this.startX
							&& startY + height >= this.startY
							&& startY <= this.startY + this.height / 2) {
						collided = true;
					}
					break;
				case 8:
				case 9:
				case 10:
				case 11:
					if (this.direction == direction - 4) {
						if (startY + height >= this.startY) {
							collided = true;
						}					
					}
					break;
				case 12:
				case 13:
				case 14:
				case 15:
					if (this.direction == direction) {
						if (startY == this.startY) {
							if (startY + height == this.startY) {
								collided = true;
							}
						} else {
							if (startY == this.startY + this.height / 2) {
								collided = true;
							}
						}					
					}
					break;
				}
				break;
			}			
		}
		return collided;
	}

}