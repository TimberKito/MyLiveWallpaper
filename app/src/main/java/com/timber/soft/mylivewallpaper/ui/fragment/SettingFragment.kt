package com.timber.soft.mylivewallpaper.ui.fragment

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import com.timber.soft.mylivewallpaper.R
import com.timber.soft.mylivewallpaper.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun getFragmentContentView(): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        binding.setLayoutRating.setOnClickListener {
            //TODO:no implement
        }
        binding.setLayoutShare.setOnClickListener {
            val url = getString(R.string.set_shop_link) + (activity?.packageName ?: "")
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(intent)
        }

        val pInfo: PackageInfo
        try {
            pInfo = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                requireActivity().packageManager.getPackageInfo(
                    requireActivity().packageName, PackageManager.PackageInfoFlags.of(0)
                )
            } else {
                requireActivity().packageManager.getPackageInfo(requireActivity().packageName, 0)
            }
            binding.setAppVersion.text = "Version: " + pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Log.e("Activity is null", e.toString())
            binding.setAppVersion.text = "Version: " + ""
        }
    }
}