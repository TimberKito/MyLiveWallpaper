package com.timber.soft.mylivewallpaper.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import java.io.Serializable

@Entity(tableName = AppFinalString.TABLE_NAME_WALLPAPER)
data class WallpaperData(
    val thumbnail: String,
    val preview: String,
    val title: String,

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val classId: String? = null,
    var isCollect: Boolean = false,
    var downloadUrl: String? = null
) : Serializable