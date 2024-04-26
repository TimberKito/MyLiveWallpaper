package com.timber.soft.mylivewallpaper.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.service.wallpaper.WallpaperService
import android.util.Log
import android.view.SurfaceHolder
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import com.timber.soft.mylivewallpaper.tools.MyApplication

class VideoWallpaperService : WallpaperService() {

//    companion object {
//        const val ACTION_SET_WALLPAPER = "VideoWallpaperService"
//    }

    //    private lateinit var mBroadcastManager: LocalBroadcastManager
    override fun onCreate() {
        super.onCreate()
//        mBroadcastManager = LocalBroadcastManager.getInstance(this)
    }

    override fun onCreateEngine(): Engine {
        return VideoEngine()
    }

    inner class VideoEngine : Engine() {
        private var mediaPlayer: MediaPlayer? = null


//        private var mReceiver = object : BroadcastReceiver() {
//            override fun onReceive(context: Context?, intent: Intent?) {
//                updateVideo()
//            }
//        }

        override fun onCreate(surfaceHolder: SurfaceHolder?) {
            super.onCreate(surfaceHolder)
//            val filter = IntentFilter(ACTION_SET_WALLPAPER)
//            mBroadcastManager.registerReceiver(mReceiver, filter)
        }


        override fun onSurfaceCreated(holder: SurfaceHolder?) {
            super.onSurfaceCreated(holder)
            val uri = getVideoUri()
            mediaPlayer = MediaPlayer.create(MyApplication.appContext, uri)
            mediaPlayer?.setSurface(holder!!.surface)
            mediaPlayer?.isLooping = true
            mediaPlayer?.setVolume(0f, 0f)
            mediaPlayer?.start()

        }

        override fun onVisibilityChanged(visible: Boolean) {
            super.onVisibilityChanged(visible)
            if (visible) {
                mediaPlayer?.start()
            } else {
                mediaPlayer?.pause()
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.stop()
                }
            }
            mediaPlayer?.release()
            mediaPlayer = null
//            mBroadcastManager.unregisterReceiver(mReceiver)
        }

//        private fun updateVideo() {
//            val uri = getVideoUri()
//            mediaPlayer?.reset()
//            mediaPlayer?.setDataSource(this@VideoWallpaperService, uri)
//            mediaPlayer?.isLooping = true
//            mediaPlayer?.setVolume(0f, 0f)
//            mediaPlayer?.prepare()
//        }
    }

    private fun getVideoUri(): Uri {
        val prefs = MyApplication.appContext.getSharedPreferences(
            AppFinalString.PREFS_NAME, Context.MODE_PRIVATE
        )
        val string = prefs.getString(AppFinalString.KEY_SP, null)
        return Uri.parse(string)
    }

}