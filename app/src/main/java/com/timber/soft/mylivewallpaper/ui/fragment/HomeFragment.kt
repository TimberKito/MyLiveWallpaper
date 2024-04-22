package com.timber.soft.mylivewallpaper.ui.fragment

import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.timber.soft.mylivewallpaper.data.WallpaperData
import com.timber.soft.mylivewallpaper.data.WallpaperViewModel
import com.timber.soft.mylivewallpaper.databinding.FragmentHomeBinding
import com.timber.soft.mylivewallpaper.ui.adapter.HomeItemAdapter
import com.timber.soft.mylivewallpaper.ui.listener.OnHomeItemClickListener
import kotlinx.coroutines.Dispatchers
import androidx.fragment.app.viewModels
import com.timber.soft.mylivewallpaper.tools.AppTools.onMain
import kotlinx.coroutines.launch

class HomeFragment : BaseFragment() {

    private lateinit var binding: FragmentHomeBinding
    private val wallpaperViewModel: WallpaperViewModel by viewModels()
    private var wallpaperDataList: MutableList<WallpaperData> = mutableListOf()
    private lateinit var homeItemAdapter: HomeItemAdapter

    override fun getFragmentContentView(): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initViews() {
        super.initViews()
        initRecyclerView()
        initRefreshLayout()
    }

    private fun initRefreshLayout() {
        binding.homeRefreshLayout.setOnRefreshListener {
            if (wallpaperViewModel.isLoading.get()) {
                return@setOnRefreshListener
            }
            lifecycleScope.launch(Dispatchers.IO) {
                wallpaperViewModel.updateWallpaper()
            }
        }

        binding.homeRefreshLayout.setOnLoadMoreListener {
            if (wallpaperViewModel.isLoading.get()) {
                return@setOnLoadMoreListener
            }
            wallpaperViewModel.loadWallpaper()
        }

        wallpaperViewModel.getData().observe(this) { list ->
            wallpaperDataList.clear()
            wallpaperDataList.addAll(list)
            onMain {
                binding.homeRefreshLayout.finishRefresh()
                binding.homeRefreshLayout.finishLoadMore()
                homeItemAdapter.notifyDataSetChanged()
            }
        }
    }

    private fun initRecyclerView() {
        wallpaperViewModel.updateWallpaper()
        homeItemAdapter =
            HomeItemAdapter(requireContext(), wallpaperDataList, object : OnHomeItemClickListener {
                override fun onItemClick(position: Int, wallpaperData: WallpaperData) {
//                       val intent = Intent(requireContext())
                    Log.d("home_item_root", "item has been click!")
                }
            })
        binding.homeRecyclerview.run {
            layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
            adapter = homeItemAdapter
        }
    }
}