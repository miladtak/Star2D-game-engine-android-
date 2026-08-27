package com.star4droid.star2d.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import com.star4droid.star2d.evo.star2dApp;

public class EngineSettings {
	public static SharedPreferences sharedPreferences;
	
	public static void init(Context context){
		if(context != null) {
			sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
		}
	}
	
	private static void ensureInit() {
		if (sharedPreferences == null && star2dApp.getContext() != null) {
			init(star2dApp.getContext());
		}
	}
	
	public static void set(String string, String value){
		ensureInit();
		if (sharedPreferences != null) {
			sharedPreferences.edit().putString(string, value).apply();
		}
	}
	
	public static void set(String string, int value){
		ensureInit();
		if (sharedPreferences != null) {
			sharedPreferences.edit().putInt(string, value).apply();
		}
	}
	
	public static void set(String string, float value){
		ensureInit();
		if (sharedPreferences != null) {
			sharedPreferences.edit().putFloat(string, value).apply();
		}
	}
	
	public static void set(String string, boolean value){
		ensureInit();
		if (sharedPreferences != null) {
			sharedPreferences.edit().putBoolean(string, value).apply();
		}
	}
	
	public static SharedPreferences get(){
		ensureInit();
		return sharedPreferences;
	}
}