package com.example.carrental;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class UserDataPreference {
	public final static String classTag = "USER_INFO";
	private Context mContext;
	private SharedPreferences prefs;
	private Editor mEditor;

	public UserDataPreference(Context context) {
		this.prefs = context.getSharedPreferences(classTag, Context.MODE_PRIVATE);
		mEditor = prefs.edit();
	}

	public boolean rememeber(boolean state) {
		if (state) {
			mEditor.putBoolean("remember", true);
			return mEditor.commit();
		} else {
			mEditor.putBoolean("remember", false);
			return mEditor.commit();
		}
	}

	public boolean isLogged() {
		return prefs.getBoolean("remember", false);
	}
	
	public void forget(){
		mEditor.clear();
		mEditor.commit();
	}
}
