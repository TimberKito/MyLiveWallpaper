package com.timber.soft.mylivewallpaper.data

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CollectViewModel : ViewModel() {

    private var wallpaperDataList: MutableLiveData<List<WallpaperData>> =
        MutableLiveData<List<WallpaperData>>()

    init {
        viewModelScope.launch {
            wallpaperDataList.value = AppDatabase.dataBase.getWallpaperDao().getCollectData()
        }
    }

    fun update() {
        viewModelScope.launch {
            wallpaperDataList.value = AppDatabase.dataBase.getWallpaperDao().getCollectData()
        }
    }

    fun getList() = wallpaperDataList

    override fun onCleared() {
        super.onCleared()
    }
}