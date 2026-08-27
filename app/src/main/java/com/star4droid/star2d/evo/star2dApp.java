package com.star4droid.star2d.evo;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.app.Application;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Process;
import android.util.Log;
import com.star4droid.star2d.DebugActivity;
import com.star4droid.star2d.Helpers.EngineSettings;
import com.star4droid.star2d.Helpers.FileUtil;
import com.star4droid.star2d.Utils;

public class star2dApp extends Application {
	private static Thread.UncaughtExceptionHandler uncaughtExceptionHandler;
	private static Context mApplicationContext;
	
	public static Context getContext() {
		return mApplicationContext;
	}
	
	public static Thread.UncaughtExceptionHandler getUncaughtExceptionHandler(){
		return uncaughtExceptionHandler;
	}
	
	@Override
	public void onCreate() {
		mApplicationContext = this;
		EngineSettings.init(this);
		Utils.setLanguage(this);
		
		if(uncaughtExceptionHandler == null) {
			uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
		}
		
		Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler(){
			@Override
			public void uncaughtException(Thread thread, Throwable throwable) {
				try {
					String log = Log.getStackTraceString(throwable);
					Log.e("star2dApp", "Uncaught exception: " + log);
					
					try {
						String dir = FileUtil.getPackageDataDir(star2dApp.this) + "/logs";
						FileUtil.makeDir(dir);
						FileUtil.writeFile(dir + "/last_crash.txt", log);
					} catch(Exception ignored){}
					
					final Intent intent = new Intent(mApplicationContext, DebugActivity.class);
					intent.putExtra("error", log);
					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
					mApplicationContext.startActivity(intent);
				} catch(Exception ex) {
					Log.e("star2dApp", "Error in crash handler: " + ex.getMessage());
				}
			}
		});
        
		super.onCreate();
	}
}