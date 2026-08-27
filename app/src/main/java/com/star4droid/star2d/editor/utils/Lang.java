package com.star4droid.star2d.editor.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;
import com.star4droid.star2d.editor.TestApp;
import java.util.HashMap;
import java.util.Locale;

public class Lang {
	/*
		Language Manager Class...
	*/
	private static I18NBundle currentLang = null;
	private static HashMap<String,I18NBundle> map = new HashMap<>();
	public static void loadTrans(String lang){
		String key = "en", code = "GB";
		if (lang != null) {
			if (lang.equals("fa")) {
				key = "fa";
				code = "IR";
			} else if (lang.equals("ar")) {
				key = "ar";
				code = "SD";
			} else if (lang.equals("fr")) {
				key = "fr";
				code = "US";
			} else if (lang.equals("br")) {
				key = "br";
				code = "US";
			} else if (lang.equals("ru")) {
				key = "ru";
				code = "US";
			} else if (lang.equals("es")) {
				key = "es";
				code = "US";
			}
		}
		if(map.containsKey(key)){
			currentLang = map.get(key);
			return;
		}
		try {
			currentLang = I18NBundle.createBundle(Gdx.files.internal("i18n/strings_"+key+"_"+code), key.equals("en") ? Locale.UK : new Locale(key, code));
		} catch(Exception e) {
			try {
				currentLang = I18NBundle.createBundle(Gdx.files.internal("i18n/strings"), Locale.ROOT);
			} catch(Exception ignored){}
		}
		if (currentLang != null) {
			map.put(key, currentLang);
		}
	}
	
	public static String getTrans(String name){
		if (name == null) return "";
		try {
			if (currentLang == null) return name;
			String firstChar = String.valueOf(name.charAt(0));
    	    boolean needModify = (firstChar.equals(firstChar.toUpperCase()) && !firstChar.equals("")) || name.contains(" ");
    	    if(!needModify)
    	        return currentLang.get(name);
    		String newName = name.replace(" ","");
    		newName = String.valueOf(newName.charAt(0)).toLowerCase() + newName.substring(1,newName.length());
			return currentLang.get(newName);
		} catch(Exception | Error e){
			return name;
		}
	}
	
	public static boolean isRTL(){
		try {
			if (TestApp.getCurrentApp() == null || TestApp.getCurrentApp().preferences == null) return false;
			String current = TestApp.getCurrentApp().preferences.getString("lang","en");
			return current.equals("fa") || current.equals("ar");
		} catch(Exception e) {
			return false;
		}
	}
}