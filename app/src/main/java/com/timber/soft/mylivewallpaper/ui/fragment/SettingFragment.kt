package com.timber.soft.mylivewallpaper.ui.fragment

import android.view.View
import com.timber.soft.mylivewallpaper.databinding.FragmentSettingBinding

class SettingFragment : BaseFragment() {
    private lateinit var binding: FragmentSettingBinding
    override fun getFragmentContentView(): View {
        binding = FragmentSettingBinding.inflate(layoutInflater)
        return binding.root
    }
}