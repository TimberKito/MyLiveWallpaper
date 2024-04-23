package com.timber.soft.mylivewallpaper.ui.activity

import android.graphics.drawable.Drawable
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
import com.timber.soft.mylivewallpaper.data.WallpaperData
import com.timber.soft.mylivewallpaper.databinding.ActivityDetailsBinding
import com.timber.soft.mylivewallpaper.tools.AppFinalString

class DetailActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var wallpaperData: WallpaperData
    override fun getActivityContentView(): View {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        wallpaperData = intent.getSerializableExtra(AppFinalString.KEY_EXTRA) as WallpaperData
        initButton()
        initPreImg()

    }

    private fun initPreImg() {
        Glide.with(this).load(wallpaperData.thumbnail)
            .transition(DrawableTransitionOptions.withCrossFade())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .listener(object : RequestListener<Drawable> {
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
                Log.e("onclick", "detailsCollect has been click!")
            }

            binding.detailsPlayButton -> {
                Log.e("onclick", "detailsPlayButton has been click!")
            }
        }
    }

}