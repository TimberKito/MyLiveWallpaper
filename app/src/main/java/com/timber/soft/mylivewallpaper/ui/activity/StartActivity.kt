package com.timber.soft.mylivewallpaper.ui.activity

import android.content.Intent
import android.view.View
import com.timber.soft.mylivewallpaper.databinding.ActivityStartBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : BaseActivity() {
    private lateinit var binding: ActivityStartBinding
    private val coroutineScope = CoroutineScope(Dispatchers.Main)
    private val countTime: Long = 2000


    override fun initViews() {
        super.initViews()

        coroutineScope.launch {
            delay(countTime)
            startMainActivity()
        }

    }

    override fun getActivityContentView(): View {
        binding = ActivityStartBinding.inflate(layoutInflater)
        return binding.root
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

}