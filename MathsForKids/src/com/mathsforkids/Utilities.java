package com.mathsforkids;

import android.content.Context;
import android.content.SharedPreferences;

public class Utilities {
	public static void updateTickVisibility(Context context, String level) {
		SharedPreferences prefs;
		SharedPreferences.Editor editor;
		prefs = context.getSharedPreferences(Constants.PREFS,
				Context.MODE_PRIVATE);
		editor = prefs.edit();
		editor.putBoolean(level, true);
		editor.commit();
	}

	public static void updateQuizBest(Context context, String quiz, int best) {
		SharedPreferences prefs;
		SharedPreferences.Editor editor;
		prefs = context.getSharedPreferences(Constants.PREFS,
				Context.MODE_PRIVATE);
		editor = prefs.edit();
		editor.putInt(quiz, best);
		editor.commit();
	}
}
