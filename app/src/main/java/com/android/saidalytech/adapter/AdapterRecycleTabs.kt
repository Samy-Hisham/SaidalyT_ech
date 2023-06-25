package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.android.saidalytech.R
import com.android.saidalytech.databinding.ItemRecycleTapsBinding
import com.android.saidalytech.databinding.ItemTabBinding
import com.android.saidalytech.model.ModelAllCategoriesItem
class AdapterRecycleTabs : RecyclerView.Adapter<AdapterRecycleTabs.Holder>() {

    var list: List<ModelAllCategoriesItem>? = null

    var onTapClick: OnTapClick? = null
    var context: Context? = null
    var selectedItem = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemTabBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    @SuppressLint("ResourceAsColor", "ResourceType")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.apply {

            if (selectedItem == position) {
                textType.setTextColor(ContextCompat.getColor(context!!, R.color.black))
            } else {
                textType.setTextColor(ContextCompat.getColor(context!!, R.color.gray))
            }
            textType.text = data?.name
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    inner class Holder(val binding: ItemTabBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            context = binding.root.context

            itemView.setOnClickListener() {

                selectedItem = layoutPosition
                onTapClick?.onClick(list?.get(layoutPosition)!!)

                notifyDataSetChanged()
            }
        }
    }

    interface OnTapClick {
        fun onClick(item: ModelAllCategoriesItem)
    }
}