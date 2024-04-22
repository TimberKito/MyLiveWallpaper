package com.timber.soft.mylivewallpaper.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
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
import com.timber.soft.mylivewallpaper.ui.listener.OnHomeItemClickListener

class HomeItemAdapter(
    private val context: Context,
    private val wallpaperDataList: MutableList<WallpaperData>,
    private val listener: OnHomeItemClickListener
) : RecyclerView.Adapter<HomeItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val wallpaperItem = itemView.findViewById<ImageView>(R.id.item_home_img)
        val rootView = itemView.findViewById<CardView>(R.id.item_home_root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_home_wallpaper, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return wallpaperDataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val wallpaperData = wallpaperDataList[position]
        try {
            Glide.with(context).load(wallpaperData.thumbnail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade()).apply(
                    RequestOptions().placeholder(R.drawable.img_loading)
                ).error(R.drawable.img_loading_err).into(holder.wallpaperItem)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        holder.rootView.setOnClickListener() {
            listener.onItemClick(position, wallpaperData)
        }
    }
}