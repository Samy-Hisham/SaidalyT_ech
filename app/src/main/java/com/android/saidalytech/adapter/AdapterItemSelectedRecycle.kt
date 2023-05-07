package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.saidalytech.databinding.ItemSelectedPerOrderBinding
import com.android.saidalytech.model.Item
import com.bumptech.glide.Glide

class AdapterItemSelectedRecycle : RecyclerView.Adapter<AdapterItemSelectedRecycle.Holder>() {

    var list: MutableList<Item>?= null

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setList1")
    fun setList(list: MutableList<Item>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemSelectedPerOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.apply {
            txtName.text = data?.name
            txtNote.text = data?.notes
            txtQuantity.text = data?.qty.toString()
            txtPrice.text = data?.price.toString()
            txtTotal.text = data?.total.toString()
        }

        Glide.with(holder.itemView.context)
            .load(data?.imageName)
            .into(holder.binding.imgItem)
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(val binding: ItemSelectedPerOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }
}