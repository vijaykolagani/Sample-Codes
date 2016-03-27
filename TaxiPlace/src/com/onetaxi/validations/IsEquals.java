package com.onetaxi.validations;

import com.onetaxi.taxiplace.R;

import android.content.Context;
import android.util.Log;

public class IsEquals extends BaseValidation {

	private String mPswd;
	private String mRepswd;

	private IsEquals(Context context, String Pswd, String Repswd) {
		super(context);
		mPswd = Pswd;
		mRepswd = Repswd;
	}

	public static Validation build(Context context, String min, String max) {
		return new IsEquals(context, min, max);
	}

	@Override
	public String getErrorMessage() {
		return mContext.getString(R.string.zvalidations_not_in_range);
	}

	@Override
	public boolean isValid(String text) {
		if(text.equals(mPswd)){
			Log.d("testt",text+","+mPswd);
			return true;
		}
		else 
			return false;
				
	}
}