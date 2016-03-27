package com.savetheeggs.help;

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
import com.savetheeggs.menu.MainActivity;

public class HelpViewPager extends FragmentActivity implements OnPageChangeListener {
	
	private HelpAdapter helpAdapter;
	private ViewPager viewPager;
	
	private TextView dotOne, dotTwo, dotThree;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help_layout);
	
		dotOne = (TextView) this.findViewById(R.id.dot_one);
		dotTwo = (TextView) this.findViewById(R.id.dot_two);
		dotThree = (TextView) this.findViewById(R.id.dot_three);
		
		helpAdapter = new HelpAdapter(getSupportFragmentManager());
		
		viewPager = (ViewPager) this.findViewById(R.id.viewPager);
		viewPager.setAdapter(helpAdapter);
		viewPager.setOnPageChangeListener(this);
	}

	public static class HelpAdapter extends FragmentPagerAdapter {
		
        public HelpAdapter(FragmentManager fm) {
            super(fm);
        }
 
        @Override
        public int getCount() {
            return 3;
        }
 
        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0:
                return new HelpFragmentOne();
            case 1:
                return new HelpFragmentTwo();
            case 2:
                return new HelpFragmentThree();
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
            break;
        case 1:
            dotOne.setText(R.string.page_indicator_normal);
            dotTwo.setText(R.string.page_indicator);
            dotThree.setText(R.string.page_indicator_normal);
            break;
        case 2:
            dotOne.setText(R.string.page_indicator_normal);
            dotTwo.setText(R.string.page_indicator_normal);
            dotThree.setText(R.string.page_indicator);
            break;
        }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) {	    	
			Intent intent = new Intent(HelpViewPager.this, MainActivity.class);
			startActivity(intent);
			this.finish();
	        return true;
	    }
	    else {
	        return super.onKeyDown(keyCode, event);
	    }
	}	
}