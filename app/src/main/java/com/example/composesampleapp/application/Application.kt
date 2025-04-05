package com.example.composesampleapp.application

import android.app.Application
import android.util.Log
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.decode.SvgDecoder
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient

@HiltAndroidApp
class MyApplication : Application(), ImageLoaderFactory {
	
	override fun onCreate() {
		super.onCreate()
		Log.d(TAG, "onCreate")
	}
	
	companion object {
		private val TAG = MyApplication::class.java.simpleName
	}

	override fun newImageLoader(): ImageLoader {
		val imageLoader = ImageLoader.Builder(applicationContext)
			.components {
				add(SvgDecoder.Factory())
			}
			.crossfade(true)
			.build()
		return imageLoader
	}
}