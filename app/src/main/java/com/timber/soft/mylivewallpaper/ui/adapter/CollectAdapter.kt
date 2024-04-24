package com.timber.soft.mylivewallpaper.ui.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.timber.soft.mylivewallpaper.R
import com.timber.soft.mylivewallpaper.data.WallpaperData
import com.timber.soft.mylivewallpaper.tools.AppFinalString
import com.timber.soft.mylivewallpaper.ui.activity.DetailActivity

class CollectAdapter(
    private val context: Context,
) : RecyclerView.Adapter<CollectAdapter.CollectItemViewHolder>() {

    private var wallpaperDataList: List<WallpaperData> = emptyList()

    fun updateData(dataList: List<WallpaperData>) {
        wallpaperDataList = dataList
        notifyDataSetChanged()
    }

    inner class CollectItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wallpaperItem = itemView.findViewById<ImageView>(R.id.item_home_img)
        val rootView = itemView.findViewById<CardView>(R.id.item_home_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_wallpaper, parent,false)
        return CollectItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallpaperDataList.size
    }

    override fun onBindViewHolder(holder: CollectItemViewHolder, position: Int) {
        wallpaperDataList[position].run {
            try {
                Glide.with(context).load(thumbnail)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .transition(DrawableTransitionOptions.withCrossFade()).apply(
                        RequestOptions().placeholder(R.drawable.img_loading)
                    ).error(R.drawable.img_loading_err).into(holder.wallpaperItem)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            holder.rootView.setOnClickListener() {
                context.startActivity(Intent(context, DetailActivity::class.java).apply {
                    putExtra(AppFinalString.KEY_EXTRA, this@run)
                })
            }
        }
    }
}