package com.timber.soft.mylivewallpaper.ui.activity

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Build
import android.view.SurfaceHolder
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.timber.soft.mylivewallpaper.data.AppDatabase
import com.timber.soft.mylivewallpaper.data.WallpaperData
import com.timber.soft.mylivewallpaper.databinding.ActivityDetailsBinding
import com.timber.soft.mylivewallpaper.service.VideoWallpaperService
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import com.timber.soft.mylivewallpaper.tools.AppTools.glideDownload
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var wallpaperData: WallpaperData
    private var mediaPlayer: MediaPlayer? = null
    private lateinit var videoUrl: String
    private var isDownload = false
    override fun getActivityContentView(): View {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.decorView.systemUiVisibility =
                (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
            window.statusBarColor = Color.TRANSPARENT
        }

        wallpaperData = intent.getSerializableExtra(AppFinalString.KEY_EXTRA) as WallpaperData

        initButton()
        initPreImg()

    }

    private fun initPreImg() {
        Glide.with(this).load(wallpaperData.thumbnail)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL).listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.detailsProgressbar.visibility = View.INVISIBLE
                    binding.detailsLoadingErr.visibility = View.VISIBLE
                    Toast.makeText(
                        applicationContext, "Check network connection!", Toast.LENGTH_SHORT
                    ).show()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean {
                    binding.detailsProgressbar.visibility = View.INVISIBLE
                    binding.detailsPlayButton.visibility = View.VISIBLE
                    return false
                }
            }).into(binding.detailsImage)
    }

    private fun initButton() {
        binding.detailsBack.setOnClickListener(this)
        binding.detailsCollect.setOnClickListener(this)
        binding.detailsSet.setOnClickListener(this)
        binding.detailsPlayButton.setOnClickListener(this)
        binding.detailsVideoBack.setOnClickListener(this)
        binding.detailsCollect.isSelected = wallpaperData.isCollect
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.detailsBack -> {
                finish()
            }

            binding.detailsSet -> {
                if (isDownload) {
                    binding.detailsPlayButton.isVisible = false
                    setWallpaper(videoUrl)
                } else {
                    binding.detailsPlayButton.isVisible = false
                    binding.detailsProgressbar.isVisible = true
                    downloadPaper()
                }
            }

            binding.detailsCollect -> {
                setCollect()
            }

            binding.detailsPlayButton -> {
                if (isDownload) {
                    binding.detailsPlayButton.isVisible = false
                    playVideo()
                } else {
                    binding.detailsPlayButton.isVisible = false
                    binding.detailsProgressbar.isVisible = true
                    showDownloadDialog()
                }
            }

            binding.detailsVideoBack -> {
                stopVideo()
            }
        }
    }

    private fun downloadPaper() {
        wallpaperData.preview.let {
            glideDownload(this, it) { file ->
                if (file == null) {
                    isDownload = false
                    Toast.makeText(
                        this@DetailActivity, "Sorry, the download failed.", Toast.LENGTH_SHORT
                    ).show()
                    binding.detailsProgressbar.isVisible = false
                } else {
                    binding.detailsProgressbar.isVisible = false
                    file.absolutePath.let { path ->
                        videoUrl = path
                        isDownload = true
                        CoroutineScope(Dispatchers.IO).launch {
                            AppDatabase.dataBase.getWallpaperDao().insertData(wallpaperData.apply {
                                downloadUrl = path
                            })
                        }
                        setWallpaper(videoUrl)
                    }
                }
            }
        }
    }

    private fun setWallpaper(videoUrl: String) {
//        binding.detailsCoverView.isVisible = true


        val instance = WallpaperManager.getInstance(this)
        try {
            instance.clear()
        }catch (e:Exception){

        }
        val prefs =
            applicationContext.getSharedPreferences(AppFinalString.PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(AppFinalString.KEY_SP, videoUrl)
        editor.apply()

        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(
            WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(this, VideoWallpaperService::class.java)
        )
        startActivity(intent)
    }

    private fun stopVideo() {
        try {
            mediaPlayer?.stop()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        binding.detailsFlPlay.isVisible = false
        binding.detailsPlayButton.isVisible = true
    }

    private fun setCollect() {
        if (!binding.detailsCollect.isSelected) {
            binding.detailsCollect.isSelected = !binding.detailsCollect.isSelected
            Toast.makeText(
                this@DetailActivity, "You have collected this sound.", Toast.LENGTH_SHORT
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.dataBase.getWallpaperDao().insertData(wallpaperData.apply {
                    isCollect = binding.detailsCollect.isSelected
                })
            }

        } else {
            binding.detailsCollect.isSelected = !binding.detailsCollect.isSelected
            Toast.makeText(
                this@DetailActivity, "You have unfavorite this sound.", Toast.LENGTH_SHORT
            ).show()
            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.dataBase.getWallpaperDao().updateData(wallpaperData.apply {
                    isCollect = binding.detailsCollect.isSelected
                })
            }
        }
    }

    private fun showDownloadDialog() {
        wallpaperData.preview.let {
            glideDownload(this, it) { file ->
                if (file == null) {
                    isDownload = false
                    Toast.makeText(
                        this@DetailActivity, "Sorry, the download failed.", Toast.LENGTH_SHORT
                    ).show()
                    binding.detailsProgressbar.isVisible = false
                    binding.detailsPlayButton.isVisible = true
                } else {
                    binding.detailsProgressbar.isVisible = false
                    file.absolutePath.let { path ->
                        videoUrl = path
                        isDownload = true
                        CoroutineScope(Dispatchers.IO).launch {
                            AppDatabase.dataBase.getWallpaperDao().insertData(wallpaperData.apply {
                                downloadUrl = path
                            })
                        }
                        playVideo()
                    }
                }
            }
        }
    }

    private fun playVideo() {
        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer()
                binding.detailsSurfaceVideo.holder.addCallback(object : SurfaceHolder.Callback {
                    override fun surfaceCreated(holder: SurfaceHolder) {
                        try {
                            mediaPlayer?.reset()
                            mediaPlayer?.setDataSource(videoUrl)
                            mediaPlayer?.setDisplay(holder)
                            mediaPlayer?.isLooping = true
                            mediaPlayer?.setVolume(0f, 0f)
                            mediaPlayer?.setOnPreparedListener {
                                try {
                                    it.start()
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                }
                            }
                            mediaPlayer?.prepareAsync()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }

                    override fun surfaceChanged(
                        holder: SurfaceHolder, format: Int, width: Int, height: Int
                    ) {
                    }

                    override fun surfaceDestroyed(holder: SurfaceHolder) {
                    }

                })
            } else {
                mediaPlayer?.reset()
                mediaPlayer?.setDataSource(videoUrl)
                mediaPlayer?.isLooping = true
                mediaPlayer?.setVolume(0f, 0f)
                mediaPlayer?.prepare()
            }
            binding.detailsFlPlay.isVisible = true
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.detailsFlPlay.isVisible) {
            stopVideo()
            return
        }
        finish()
    }

    override fun onPause() {
        super.onPause()
        if (binding.detailsFlPlay.isVisible) {
            try {
                stopVideo()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (binding.detailsFlPlay.isVisible) {
            try {
                mediaPlayer?.start()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}