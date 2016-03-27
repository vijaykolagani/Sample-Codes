package com.savetheeggs.levels;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.TextView;

import com.savetheeggs.R;
import com.savetheeggs.worlds.WorldViewPager;

public class LevelViewPager extends FragmentActivity implements OnPageChangeListener {
	
	private LevelAdapter levelAdapter;
	private ViewPager viewPager;
	
	private TextView dotOne, dotTwo, dotThree, dotFour;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.level_layout_main);
	
		dotOne = (TextView) this.findViewById(R.id.dot_one);
		dotTwo = (TextView) this.findViewById(R.id.dot_two);
		dotThree = (TextView) this.findViewById(R.id.dot_three);
		dotFour = (TextView) this.findViewById(R.id.dot_four);
		
		levelAdapter = new LevelAdapter(getSupportFragmentManager());
		
		viewPager = (ViewPager) this.findViewById(R.id.viewPager);
		viewPager.setAdapter(levelAdapter);
		viewPager.setOnPageChangeListener(this);
	}

	public static class LevelAdapter extends FragmentPagerAdapter {
		
        public LevelAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public int getCount() {
            return 4;
        }
 
        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0:
                return new LevelFragmentOne();
            case 1:
                return new LevelFragmentTwo();
            case 2:
                return new LevelFragmentThree();
            case 3:
            	return new LevelFragmentFour();
            default:
                return null;
            }
        }
    }

	public void onPageScrollStateChanged(int arg0) {
		
	}

	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
	}

	public void onPageSelected(int position) {
		Log.d("OnPageSelected", "position: " + position);
        switch (position) {
        case 0:
            dotOne.setText(R.string.page_indicator);
            dotTwo.setText(R.string.page_indicator_normal);
            dotThree.setText(R.string.page_indicator_normal);
            dotFour.setText(R.string.page_indicator_normal);
            break;
        case 1:
            dotOne.setText(R.string.page_indicator_normal);
            dotTwo.setText(R.string.page_indicator);
            dotThree.setText(R.string.page_indicator_normal);
            dotFour.setText(R.string.page_indicator_normal);
            break;
        case 2:
            dotOne.setText(R.string.page_indicator_normal);
            dotTwo.setText(R.string.page_indicator_normal);
            dotThree.setText(R.string.page_indicator);
            dotFour.setText(R.string.page_indicator_normal);
            break;
        case 3:
            dotOne.setText(R.string.page_indicator_normal);
            dotTwo.setText(R.string.page_indicator_normal);
            dotThree.setText(R.string.page_indicator_normal);
            dotFour.setText(R.string.page_indicator);
            break;
        }		
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent(LevelViewPager.this, WorldViewPager.class);
			startActivity(intent);
			this.finish();
	        return true;
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }
	}	
}
