package com.timber.soft.mylivewallpaper.tools

import android.os.Handler
import android.os.Looper
import com.google.gson.Gson
import com.timber.soft.mylivewallpaper.data.WallpaperData
import java.io.InputStream
import java.io.InputStreamReader

object AppTools {
    fun onMain(operation: () -> Unit) = Handler(Looper.getMainLooper()).post(operation)
    fun parseJsonFile(jsonInputStream: InputStream): List<WallpaperData> {
        val reader = InputStreamReader(jsonInputStream)
        val jsonString = reader.readText()
        return Gson().fromJson(jsonString, Array<WallpaperData>::class.java).toMutableList()
    }
}