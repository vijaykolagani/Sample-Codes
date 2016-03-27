package kysk.roadrush.full.sounds;

import kysk.roadrush.full.R;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class SoundManager {
	private SoundPool soundPool;
	
	private int music;
	
	private int ambulanceSiren;
	private int policeSiren;
	private int brakeSound;
	private int crashSound;
	private int swipeSound;
	
	private int bettleHorn;
	private int buggyHorn;
	private int jeepHorn;
	private int carHorn;
	private int horn;
	
	private boolean ambulanceSirenPaused;
	private boolean policeSirenPaused;
	
	private boolean isSoundOn;
	private boolean isMusicOn;
	
	public SoundManager(Context context) {
		soundPool = new SoundPool(15, AudioManager.STREAM_MUSIC, 1);
				
		ambulanceSiren = soundPool.load(context, R.raw.ambulance_siren, 1);
		policeSiren = soundPool.load(context, R.raw.police_siren, 1);
		brakeSound = soundPool.load(context, R.raw.brake, 1);
		crashSound = soundPool.load(context, R.raw.car_crash, 1);
		swipeSound = soundPool.load(context, R.raw.race, 1);
		
		bettleHorn = soundPool.load(context, R.raw.bettle, 1);
		buggyHorn = soundPool.load(context, R.raw.buggy, 1);
		carHorn = buggyHorn;
		jeepHorn = bettleHorn;
		horn = soundPool.load(context, R.raw.horn, 1);
		
		music = soundPool.load(context, R.raw.drums, 1);
	}
	
	public void setSoundOn(boolean isSoundOn) {
		this.isSoundOn = isSoundOn;
	}

	public void setMusicOn(boolean isMusicOn) {
		this.isMusicOn = isMusicOn;
	}

	public boolean isAmbulanceSirenPaused() {
		return ambulanceSirenPaused;
	}

	public void setAmbulanceSirenPaused(boolean ambulanceSirenPaused) {
		this.ambulanceSirenPaused = ambulanceSirenPaused;
	}

	public boolean isPoliceSirenPaused() {
		return policeSirenPaused;
	}

	public void setPoliceSirenPaused(boolean policeSirenPaused) {
		this.policeSirenPaused = policeSirenPaused;
	}

	public int playMusic() {
		if (isMusicOn) {
			return soundPool.play(music, 1.0f, 1.0f, 0, -1, 1.0f);			
		}
		return 0;
	}
	
	public void pauseMusic(int streamId) {
		if (isMusicOn) {
			soundPool.pause(streamId);
		}
	}
	
	public void resumeMusic(int streamId) {
		if (isMusicOn) {
			soundPool.resume(streamId);
		}
	}
	
	public void stopMusic(int streamId) {
		if (isMusicOn) {
			soundPool.stop(streamId);			
		}
	}
	
	public int playAmbulanceSiren() {
		if (isSoundOn) {
			return soundPool.play(ambulanceSiren, 1.0f, 1.0f, 0, -1, 1.0f);			
		}
		return 0;
	}
	
	public void stopAmbulanceSiren(int streamId) {
		if (isSoundOn) {
			soundPool.stop(streamId);			
		}
	}
	
	public void pauseAmbulanceSiren(int streamId) {
		if (isSoundOn) {
			setAmbulanceSirenPaused(true);
			soundPool.pause(streamId);			
		}
	}
	
	public void resumeAmbulanceSiren(int streamId) {
		if (isSoundOn) {
			setAmbulanceSirenPaused(false);
			soundPool.resume(streamId);			
		}
	}
	
	public int playPoliceSiren() {
		if (isSoundOn) {
			return soundPool.play(policeSiren, 1.0f, 1.0f, 0, -1, 1.0f);			
		}
		return 0;
	}
	
	public void stopPoliceSiren(int streamId) {
		if (isSoundOn) {
			soundPool.stop(streamId);			
		}
	}
	
	public void pausePoliceSiren(int streamId) {
		if (isSoundOn) {
			setPoliceSirenPaused(true);
			soundPool.pause(streamId);			
		}
	}
	
	public void resumePoliceSiren(int streamId) {
		if (isSoundOn) {
			setPoliceSirenPaused(false);
			soundPool.resume(streamId);			
		}
	}
	
	public void playBrakeSound() {
		if (isSoundOn) {
			soundPool.play(brakeSound, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playCrashSound() {
		if (isSoundOn) {
			soundPool.play(crashSound, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playSwipeSound() {
		if (isSoundOn) {
			soundPool.play(swipeSound, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playBettleHorn() {
		if (isSoundOn) {
			soundPool.play(bettleHorn, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playBuggyHorn() {
		if (isSoundOn) {
			soundPool.play(buggyHorn, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playJeepHorn() {
		if (isSoundOn) {
			soundPool.play(jeepHorn, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playCarHorn() {
		if (isSoundOn) {
			soundPool.play(carHorn, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
	
	public void playHorn() {
		if (isSoundOn) {
			soundPool.play(horn, 1.0f, 1.0f, 0, 0, 1.0f);			
		}
	}
}
