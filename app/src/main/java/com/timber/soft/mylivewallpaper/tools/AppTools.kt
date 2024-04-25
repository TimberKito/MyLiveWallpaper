package com.timber.soft.mylivewallpaper.tools

import android.content.Context
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import com.timber.soft.mylivewallpaper.data.WallpaperData
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

object AppTools {

    fun View.throttleClicks(time: Long = 1000, block: (View) -> Unit) {
        this.clicks().throttleFirst(time, TimeUnit.MILLISECONDS).subscribe { block(this) }
    }


    fun onMain(operation: () -> Unit) = Handler(Looper.getMainLooper()).post(operation)
    fun parseJsonFile(jsonInputStream: InputStream): List<WallpaperData> {
        val reader = InputStreamReader(jsonInputStream)
        val jsonString = reader.readText()
        return Gson().fromJson(jsonString, Array<WallpaperData>::class.java).toMutableList()
    }

    fun glideDownload(context: Context, url: String, downloadCall: (File?) -> Unit) {
        Glide.with(context).downloadOnly().load(url).addListener(object : RequestListener<File> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<File>?,
                isFirstResource: Boolean
            ): Boolean {
                downloadCall.invoke(null)
                return false
            }

            override fun onResourceReady(
                resource: File?,
                model: Any?,
                target: Target<File>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                downloadCall.invoke(resource)
                return false
            }
        }).preload()
    }
}