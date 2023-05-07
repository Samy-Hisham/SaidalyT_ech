package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.saidalytech.databinding.ItemRecycleBinding
import com.android.saidalytech.model.ModelDataItem
import com.bumptech.glide.Glide

class AdapterRecyclerItems : RecyclerView.Adapter<AdapterRecyclerItems.Holder>() {

    var list: MutableList<ModelDataItem>? = null

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setList1")
    fun setList(list: MutableList<ModelDataItem>?) {
        this.list = list
        notifyDataSetChanged()
    }

    var onUserClicks: OnUserClicks? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding = ItemRecycleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.apply {
            txtItem.text = data?.itemName
            txtPrice.text = data?.salesPrice.toString()
        }

        Glide.with(holder.itemView.context)
            .load(data?.imageName)
            .into(holder.binding.imgItem)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<ModelDataItem>) {
        list?.clear()
        list?.addAll(data)
        notifyDataSetChanged()
    }

    inner class Holder(val binding: ItemRecycleBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                onUserClicks?.onClick(list?.get(layoutPosition)!!.itemId)
            }
        }
    }

    interface OnUserClicks {
        fun onClick(id: Int)
    }
}