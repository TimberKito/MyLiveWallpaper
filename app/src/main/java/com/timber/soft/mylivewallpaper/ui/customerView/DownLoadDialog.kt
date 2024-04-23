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

/**
 * TODO : no implement
 */
class DownLoadDialog(
    private val context: Context,
    private val url: String,
    private val title: String = "",
    init: DownLoadDialog.() -> Unit
) : Dialog(context, R.style.DownLoadDialog) {
    private lateinit var binding: ViewDownloaddialogBinding
    var onDownloadSuccess: (() -> Unit)? = null
    var onDownloadFailed: (() -> Unit)? = null
    private var mCount: AtomicInteger = AtomicInteger(0)

    init {
        init()
    }

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
        binding.dialogProgressbar.progress = 0f
    }

    private fun initView() {
        if (!TextUtils.isEmpty(title)) {
            binding.dialogText.text = title
        }
        binding.dialogIvClose.throttleClicks {
            dismiss()
        }
    }

}