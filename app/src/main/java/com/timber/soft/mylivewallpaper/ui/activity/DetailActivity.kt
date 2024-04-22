package com.timber.soft.mylivewallpaper.ui.activity

import android.view.View
import com.timber.soft.mylivewallpaper.databinding.ActivityDetailsBinding

class DetailActivity : BaseActivity() {

    private lateinit var binding: ActivityDetailsBinding
    override fun getActivityContentView(): View {
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        super.initViews()

    }
}