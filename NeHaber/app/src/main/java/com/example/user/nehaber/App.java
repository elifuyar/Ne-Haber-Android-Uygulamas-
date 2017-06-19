package com.example.user.nehaber;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.impl.LimitedAgeDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;

public class App extends Application {
	@Override
	public void onCreate() {
		super.onCreate();

		File cacheDir = StorageUtils.getCacheDirectory(getBaseContext());

		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.cacheOnDisc(true).bitmapConfig(Bitmap.Config.RGB_565)
				.imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				getBaseContext()).threadPoolSize(2)
				.memoryCache(new WeakMemoryCache())
				.discCache(new LimitedAgeDiscCache(cacheDir, 60 * 60 * 24))
				.discCacheFileCount(100).discCacheSize(1024 * 1024 * 50)
				.defaultDisplayImageOptions(options).build();
		ImageLoader.getInstance().init(config);
	}
}
