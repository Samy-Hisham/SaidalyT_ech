package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.saidalytech.databinding.ItemOrderBinding
import com.android.saidalytech.model.ModelOrder
import com.bumptech.glide.Glide

class AdapterItemRecycle : RecyclerView.Adapter<AdapterItemRecycle.Holder>() {

    var list: MutableList<ModelOrder>? = null
    var count = 1

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setList1")
    fun setList(list: MutableList<ModelOrder>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.nameItem.text = data?.name_Item
        var quantity: Int

        Glide.with(holder.itemView.context)
            .load(data?.image_Item)
            .into(holder.binding.imgItem)

        holder.binding.btnAdd.setOnClickListener {
            count++
            holder.binding.textView2.text = count.toString()
            quantity = count * data?.price_Item!!.toInt()
            holder.binding.priceItem.text = quantity.toString()
        }

        holder.binding.btnSub.setOnClickListener {
            count--
            holder.binding.textView2.text = count.toString()
            quantity = count * data?.price_Item!!.toInt()
            holder.binding.priceItem.text = quantity.toString()
        }

        holder.binding.textView2.text = count.toString()
        quantity = count * data?.price_Item!!.toInt()
        holder.binding.priceItem.text = quantity.toString()
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {

    }
}