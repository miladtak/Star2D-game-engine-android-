package com.star4droid.star2d.CodeEditor;

import android.content.Context;
import android.util.Log;
import com.google.gson.Gson;
import com.tyron.javacompletion.project.ModuleManager;
import com.tyron.javacompletion.project.Project;
import com.tyron.javacompletion.storage.IndexStore;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;

public class IndexUtil {
	public static ModuleManager getModule(Project project){
		try {
			Field f=project.getClass().getDeclaredField("moduleManager");
			f.setAccessible(true);
			return (ModuleManager)f.get(project);
		} catch(Exception exception){
			Log.e("getting module",Log.getStackTraceString(exception));
			return null;
		}
	}
	
	public static void loadFile(Project project,String path){
		try {
			InputStream inputStream=new FileInputStream(path);
			loadStream(project,inputStream);
		} catch(Exception exception){
			Log.e("loading file error",Log.getStackTraceString(exception));
		}
	}
	
	public static void loadStream(Project project,InputStream stream){
		InputStreamReader reader= new InputStreamReader(stream);
		getModule(project).addDependingModule(new IndexStore().readModule(reader));
	}
	
	public static void loadJdk(Project project,Context context,String... other) throws Exception { 
		IndexStore indexStore = new IndexStore();
		ModuleManager manager=getModule(project);
		if (manager == null) return;
		try {
			InputStreamReader reader = new InputStreamReader(context.getAssets().open("editor/index.json"));
			manager.addDependingModule(indexStore.readModule(reader));
		} catch(Exception ignored){}
		try {
			manager.addDependingModule(indexStore.readModule(new InputStreamReader(context.getAssets().open("editor/libgdx.json"))));
		} catch(Exception ignored){}
		try {
			manager.addDependingModule(indexStore.readModule(new InputStreamReader(context.getAssets().open("editor/visui.json"))));
		} catch(Exception ignored){}
		try {
			manager.addDependingModule(indexStore.readModule(new InputStreamReader(context.getAssets().open("editor/addition.json"))));
		} catch(Exception ignored){}
		if (other != null) {
			for(String s:other) {
				try {
					if (s != null && !s.isEmpty() && new java.io.File(s).exists()) {
						manager.addDependingModule(indexStore.readModule(getInputStream(s)));
					}
				} catch(Exception ignored){}
			}
		}
	}
	
	public static InputStreamReader getInputStream(String file) throws Exception {
		return new InputStreamReader(new FileInputStream(file));
	}
}