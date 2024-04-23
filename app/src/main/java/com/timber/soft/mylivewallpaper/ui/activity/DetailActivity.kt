package com.timber.soft.mylivewallpaper.ui.activity

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import com.timber.soft.mylivewallpaper.tools.AppTools.isExist
import com.timber.soft.mylivewallpaper.ui.customerView.DownLoadDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var wallpaperData: WallpaperData
    private lateinit var downDialog: DownLoadDialog
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
                    // 加载失败时的处理
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
                    // 图片加载完成时的处理
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
        binding.detailsCollect.isSelected = wallpaperData.isCollect
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.detailsBack -> {
                finish()
            }

            binding.detailsSet -> {
                Log.e("onclick", "detailsSet has been click!")
            }

            binding.detailsCollect -> {
                setCollect()
            }

            binding.detailsPlayButton -> {
                if (isExist()) {
                    playVideo()
                } else {
                    showDownloadDialog()
                }
            }
        }
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
                AppDatabase.dataBase.getWallpaperDao().deleteData(wallpaperData.apply {
                    isCollect = binding.detailsCollect.isSelected
                })
            }
        }
    }

    private fun showDownloadDialog() {
        Log.e("file_play", "file is null!")
    }

    private fun playVideo() {
        Log.e("file_play", "file is exist!")
    }

    private fun isExist(): Boolean {
        return isExist(wallpaperData.preview)
    }


}