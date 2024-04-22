package com.timber.soft.mylivewallpaper.ui.fragment

import android.view.View
import com.timber.soft.mylivewallpaper.databinding.FragmentCollectBinding

class CollectFragment : BaseFragment() {
    private lateinit var binding: FragmentCollectBinding
    override fun getFragmentContentView(): View {
        binding = FragmentCollectBinding.inflate(layoutInflater)
        return binding.root
    }
}