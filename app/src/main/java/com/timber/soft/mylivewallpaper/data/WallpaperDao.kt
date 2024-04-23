package com.timber.soft.mylivewallpaper.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WallpaperDao {
    @Query("select * from wallpaper where isCollect = :collect ")
    suspend fun getCollectData(collect: Boolean = true): List<WallpaperData>

    @Update
    suspend fun updateData(wallpaperData: WallpaperData)

    @Insert
    suspend fun insertData(wallpaperData: WallpaperData)

    @Delete
    suspend fun deleteData(wallpaperData: WallpaperData)

}