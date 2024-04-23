package com.timber.soft.mylivewallpaper.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import com.timber.soft.mylivewallpaper.tools.MyApplication

@Database(
    entities = [WallpaperData::class], version = AppFinalString.DB_VERSION, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        val dataBase: AppDatabase by lazy {
            getInstance()
        }

        private fun getInstance(): AppDatabase {
            return Room.databaseBuilder(
                MyApplication.appContext, AppDatabase::class.java, AppFinalString.DB_NAME
            ).build()
        }
    }
    abstract fun getWallpaperDao(): WallpaperDao
}