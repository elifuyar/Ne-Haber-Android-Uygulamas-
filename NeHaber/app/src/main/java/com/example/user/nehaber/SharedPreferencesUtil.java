package com.example.user.nehaber;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.user.nehaber.models.Categories;
import com.example.user.nehaber.models.News;
import com.example.user.nehaber.models.Newses;
import com.example.user.nehaber.models.NewsesArrayList;
import com.google.gson.Gson;


public class SharedPreferencesUtil {
	private static final String STORE_NAME = "NEWS_APP_SHARED_PREFERENCES";
	public static final String KEY_FIRST_RUN = "KEY_FIRST_RUN";
	public static final String KEY_NEWS = "KEY_NEWS";
	public static final String KEY_CATEGORIES = "KEY_CATEGORIES";
	

	public static String getString(Context context, String key) { // String veriyi ?eker
		SharedPreferences settings = context.getSharedPreferences(STORE_NAME,
				Context.MODE_PRIVATE);
		return settings.getString(key, "");
	}

	public static void setString(Context context, String key, String value) { // String veriyi kaydeder
		SharedPreferences settings = context.getSharedPreferences(STORE_NAME,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(key, value);
		editor.commit();
	}

	public static boolean isFirstRun(Context context) { // ilk �ag�r�ld�g�nda true sonra false d�ner
		String key = getString(context, KEY_FIRST_RUN);
		boolean result = Validations.isEmpty(key);
		setString(context, KEY_FIRST_RUN, "false");
		return result;
	}

	

	public static void setNewses(Context context, Newses n, String categoryId) {
		NewsesArrayList nal= new NewsesArrayList();
		for (News news : n) {
			nal.add(news);
		}
		String strObj = new Gson().toJson(nal);
		setString(context, KEY_NEWS+categoryId, strObj);
	}

	public static Newses getNewses(Context context, String categoryId) {
		String strNewses = getString(context, KEY_NEWS+categoryId);
		NewsesArrayList nal= new NewsesArrayList();
		if (Validations.isEmpty(strNewses)) {
			return new Newses(100);
		} else {
			Gson gson = new Gson();
			nal = gson.fromJson(strNewses, NewsesArrayList.class);
			Newses newses = new Newses(100);
			for (News news : nal) {
				newses.add(news);
			}
			return newses;
		}
	}
	
	public static void setCategories(Context context, Categories c) {
		String strObj = new Gson().toJson(c);
		setString(context, KEY_CATEGORIES, strObj);
	}

	public static Categories getCategories(Context context) {
		String strCategories = getString(context, KEY_CATEGORIES);
		if (Validations.isEmpty(strCategories)) {
			return new Categories();
		} else {
			Gson gson = new Gson();
			return gson.fromJson(strCategories, Categories.class);
		}
	}

	public static int getInt(Context context, String key) {
		SharedPreferences settings = context.getSharedPreferences(STORE_NAME,
				Context.MODE_PRIVATE);
		return settings.getInt(key, 0);
	}

	public static Boolean setInt(Context context, String key, int value) {
		try {
			SharedPreferences settings = context.getSharedPreferences(
					STORE_NAME, Context.MODE_PRIVATE);
			SharedPreferences.Editor editor = settings.edit();
			editor.putInt(key, value);
			editor.commit();
			return true;
		} catch (Exception ex) {
			Log.e("SharedPref_Set", ex.toString());
			return false;
		}
	}

}
