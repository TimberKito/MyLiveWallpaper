package com.timber.soft.mylivewallpaper.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.timber.soft.mylivewallpaper.R
import com.timber.soft.mylivewallpaper.databinding.FragmentRateBinding
import com.timber.soft.mylivewallpaper.ui.adapter.RateStartAdapter

class RateFragment : DialogFragment() {
    private lateinit var binding: FragmentRateBinding
    private lateinit var rateStartAdapter: RateStartAdapter

    companion object {
        val EXTRA_KEY = "task"
        val ADD_TASK_TYPE_KEY = "add_task_type_key"

        @JvmStatic
        fun newInstance(param1: Int, param2: Int) = RateFragment().apply {
            arguments = Bundle().apply {
                putInt(EXTRA_KEY, param1)
                putInt(ADD_TASK_TYPE_KEY, param2)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentRateBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        dialog?.run {
            setCanceledOnTouchOutside(false)
            setCancelable(false)
            window?.run {
                attributes = attributes.apply {
                    gravity = Gravity.CENTER
                    width = WindowManager.LayoutParams.WRAP_CONTENT
                    height = WindowManager.LayoutParams.WRAP_CONTENT
                }
            }
            val decorView = window!!.decorView
            decorView.setPadding(0, 0, 0, 0)
            decorView.background = ColorDrawable(Color.TRANSPARENT)
        }
        initDialog()
    }

    private fun initDialog() {
        binding.run {
            rateStartAdapter = RateStartAdapter(requireContext()) {
                tvRateIt.run {
                    isSelected = true
                    isClickable = true
                }
            }
            recyclerStart.apply {
                adapter = rateStartAdapter
                layoutManager = LinearLayoutManager(requireContext()).apply {
                    orientation = LinearLayoutManager.HORIZONTAL
                }
            }
            tvRateIt.run {
                isSelected = false
                isClickable = false
            }
            rateCancel.setOnClickListener() { dismiss() }
            tvRateIt.setOnClickListener {
                rateStartAdapter.getRate()?.let {
                    if (it >= 3) {
                        goRate()
                        dismiss()
                    } else {
                        dismiss()
                    }
                }
            }
        }
    }

    private fun goRate() {
        val intent = Intent("android.intent.action.VIEW")
        val stringBuilder = getString(R.string.set_shop_link) + (requireContext().packageName)
        intent.data = Uri.parse(stringBuilder)
        startActivity(intent)
    }
}