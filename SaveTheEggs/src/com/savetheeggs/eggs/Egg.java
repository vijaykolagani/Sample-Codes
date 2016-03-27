package com.savetheeggs.eggs;

import android.graphics.Canvas;

abstract public class Egg {
	abstract public void draw(Canvas canvas);
	abstract public boolean detectCollision(float x, float y, int width);
}
