package com.timber.soft.mylivewallpaper.ui.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.timber.soft.mylivewallpaper.data.CollectViewModel
import com.timber.soft.mylivewallpaper.databinding.FragmentCollectBinding
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import com.timber.soft.mylivewallpaper.ui.adapter.CollectAdapter

class CollectFragment : BaseFragment() {
    private lateinit var binding: FragmentCollectBinding
    private lateinit var collectAdapter: CollectAdapter
    private lateinit var collectViewModel: CollectViewModel
    override fun getFragmentContentView(): View {
        binding = FragmentCollectBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val filter = IntentFilter(AppFinalString.ACTION_DATABASE_UPDATED)
        LocalBroadcastManager.getInstance(requireContext())
            .registerReceiver(mDatabaseUpdatedReceiver, filter)
    }

    override fun initViews() {
        super.initViews()
        collectAdapter = CollectAdapter(requireContext())
        binding.recyclerCollect.run {
            adapter = collectAdapter
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        }
    }

    override fun onResume() {
        super.onResume()
        collectViewModel.update()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        collectViewModel = ViewModelProvider(this)[CollectViewModel::class.java]
        collectViewModel.getList().observe(viewLifecycleOwner, Observer {
            collectAdapter.updateData(it)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LocalBroadcastManager.getInstance(requireContext())
            .unregisterReceiver(mDatabaseUpdatedReceiver)
    }

    private val mDatabaseUpdatedReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                if (intent.action.equals(AppFinalString.ACTION_DATABASE_UPDATED)) {
                    collectViewModel.update()
                }
            }
        }
    }
}