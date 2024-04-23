package com.timber.soft.mylivewallpaper.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.service.autofill.OnClickAction
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.timber.soft.mylivewallpaper.databinding.FragmentRateBinding
import com.timber.soft.mylivewallpaper.databinding.ItemRateStartBinding

class RateStartAdapter(
    private val context: Context,
    private var clickAction: (Int) -> Unit
) : RecyclerView.Adapter<RateStartAdapter.RateViewHolder>() {

    private var clickPos: Int? = null
    fun getRate(): Int? = clickPos

    @SuppressLint("NotifyDataSetChanged")
    inner class RateViewHolder(private val binding: ItemRateStartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
//            binding.root.setOnClickListener {
//                val position = bindingAdapterPosition
//                if (position != RecyclerView.NO_POSITION) {
//                    clickAction.invoke(position)
//                }
//            }
        }

        fun bind(position: Int) {
            clickPos?.let {
                binding.itemStart.isSelected = position <= it
            }
            binding.consStart.setOnClickListener {
                clickAction.invoke(position)
                clickPos = position
                notifyDataSetChanged()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder =
        RateViewHolder(
            ItemRateStartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun getItemCount(): Int = 5

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(position)
    }
}