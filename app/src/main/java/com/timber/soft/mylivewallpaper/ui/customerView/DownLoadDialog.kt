package com.timber.soft.mylivewallpaper.ui.customerView

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import com.timber.soft.mylivewallpaper.R
import com.timber.soft.mylivewallpaper.databinding.ViewDownloaddialogBinding
import com.timber.soft.mylivewallpaper.tools.AppTools.throttleClicks
import java.util.concurrent.atomic.AtomicInteger

class DownLoadDialog(
    private val context: Context,
    private val url: String,
) : Dialog(context, R.style.DownLoadDialog) {
    private lateinit var binding: ViewDownloaddialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ViewDownloaddialogBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        initViews()
    }

    private fun initViews() {
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        initView()
        startDownload()
    }

    private fun startDownload() {

    }

    private fun initView() {
        binding.dialogIvClose.throttleClicks {
            dismiss()
        }
    }

}