package com.timber.soft.mylivewallpaper.ui.listener

import com.timber.soft.mylivewallpaper.data.WallpaperData

interface OnHomeItemClickListener {
    fun onItemClick(position: Int, wallpaperData: WallpaperData)
}