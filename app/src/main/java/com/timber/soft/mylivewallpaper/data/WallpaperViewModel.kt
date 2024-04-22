package com.timber.soft.mylivewallpaper.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.timber.soft.mylivewallpaper.tools.AppTools
import com.timber.soft.mylivewallpaper.tools.AppTools.parseJsonFile
import com.timber.soft.mylivewallpaper.tools.MyApplication
import java.util.concurrent.atomic.AtomicBoolean

class WallpaperViewModel : ViewModel() {
    private var wallpaperDataListLiveData: MutableLiveData<MutableList<WallpaperData>> =
        MutableLiveData()
    private var wallpaperDataList: MutableList<WallpaperData> = mutableListOf()
    private var wallpaperPoolsList: MutableList<WallpaperData> = mutableListOf()
    var isLoading = AtomicBoolean(false)

    fun getData(): MutableLiveData<MutableList<WallpaperData>> {
        return wallpaperDataListLiveData
    }

    fun updateWallpaper() {
        if (isLoading.get()) {
            return
        }
        try {
            isLoading.set(true)
            wallpaperDataList.clear()
            wallpaperPoolsList.clear()
            wallpaperPoolsList.addAll(getDataList())
            val list = wallpaperPoolsList.subList(0, 40)
            wallpaperDataList.addAll(list)
            wallpaperPoolsList.removeAll(list)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            wallpaperDataListLiveData.postValue(wallpaperDataList)
            isLoading.set(false)
        }
    }

    fun loadWallpaper() {
        if (isLoading.get()) {
            return
        }
        try {
            isLoading.set(true)
            val list = wallpaperPoolsList.subList(0, 20)
            wallpaperDataList.addAll(list)
            wallpaperPoolsList.removeAll(list)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            wallpaperDataListLiveData.postValue(wallpaperDataList)
            isLoading.set(false)
        }
    }

    private fun getDataList(): List<WallpaperData> {
        return parseJsonFile(MyApplication.appContext.assets.open("live.json")).shuffled()
    }

    override fun onCleared() {
        super.onCleared()
    }
}