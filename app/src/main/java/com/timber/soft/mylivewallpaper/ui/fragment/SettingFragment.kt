package com.timber.soft.mylivewallpaper.ui.fragment

import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.timber.soft.mylivewallpaper.R
import com.timber.soft.mylivewallpaper.data.AppDatabase
import com.timber.soft.mylivewallpaper.databinding.FragmentSettingBinding
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SettingFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun getFragmentContentView(): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        initButton()
        initInfoCard()
    }

    private fun initInfoCard() {
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

    private fun initButton() {
        binding.setLayoutRating.setOnClickListener {
            RateFragment.newInstance(0, 0).show(childFragmentManager, "")
        }

        binding.setLayoutShare.setOnClickListener {
            val url = getString(R.string.set_shop_link) + (activity?.packageName ?: "")
            val intent = Intent(Intent.ACTION_SEND)
            intent.setType("text/plain")
            intent.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(intent)
        }

        binding.setLayoutDelete.setOnClickListener() {

            CoroutineScope(Dispatchers.IO).launch {
                AppDatabase.dataBase.getWallpaperDao().deleteAllData()
            }
            sendDatabaseUpdatedBroadcast()
            Toast.makeText(
                requireActivity(), "Cleared all collections successfully.", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun sendDatabaseUpdatedBroadcast() {
        val intent = Intent(AppFinalString.ACTION_DATABASE_UPDATED)
        LocalBroadcastManager.getInstance(requireContext()).sendBroadcast(intent)
    }
}