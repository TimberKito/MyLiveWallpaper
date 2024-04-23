package com.timber.soft.mylivewallpaper.tools

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import com.google.gson.Gson
import com.jakewharton.rxbinding4.view.clicks
import com.timber.soft.mylivewallpaper.data.WallpaperData
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.util.concurrent.TimeUnit

object AppTools {

    private const val DIR_FILE_NAME = "tep"
    private const val DIR_DOWNLOAD = "download"

    fun View.throttleClicks(time: Long = 1000, block: (View) -> Unit) {
        this.clicks().throttleFirst(time, TimeUnit.MILLISECONDS).subscribe { block(this) }
    }

    fun onMain(operation: () -> Unit) = Handler(Looper.getMainLooper()).post(operation)
    fun parseJsonFile(jsonInputStream: InputStream): List<WallpaperData> {
        val reader = InputStreamReader(jsonInputStream)
        val jsonString = reader.readText()
        return Gson().fromJson(jsonString, Array<WallpaperData>::class.java).toMutableList()
    }

    private fun getDownloadDirectory(): String {
        return getDefaultDirectory() + File.separator + DIR_DOWNLOAD
    }

    private fun getDefaultDirectory(): String {
        var dirName = ""
        if (MyApplication.appContext.getExternalFilesDir(DIR_FILE_NAME) != null) {//外部存储可用
            if (Build.VERSION.SDK_INT >= 29) {
                dirName = MyApplication.appContext.getExternalFilesDir(DIR_FILE_NAME)!!.path
            } else if (Build.VERSION.SDK_INT < 29) {
                dirName = MyApplication.appContext.getExternalFilesDir(DIR_FILE_NAME)!!.absolutePath
            }
        } else {//外部存储不可用
            dirName = MyApplication.appContext.filesDir.absolutePath
        }
        return dirName
    }

    fun getFileName(url: String): String {
        return url.substring(url.lastIndexOf("/") + 1)
    }

    fun getFilePath(url: String): String {
        return getDownloadDirectory() + File.separator + getFileName(url)
    }

    fun getFile(url: String): File {
        return File(getFilePath(url))
    }

    fun isExist(url: String): Boolean {
        return File(getFilePath(url)).exists()
    }


}