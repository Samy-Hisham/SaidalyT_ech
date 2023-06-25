package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.saidalytech.databinding.ItemOrderBinding
import com.android.saidalytech.model.ItemX
import com.android.saidalytech.model.ModelOrder
import com.bumptech.glide.Glide

class AdapterItemRecycle : RecyclerView.Adapter<AdapterItemRecycle.Holder>() {

    var list: List<ItemX>? = null
    var onUserClicks: OnUserClicks? = null

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setList1")
    fun setList(list: List<ItemX>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.nameItem.text = data?.itemName
        holder.binding.textView2.text = data?.qty.toString()

        Glide.with(holder.itemView.context)
            .load(data?.itemImage)
            .into(holder.binding.imgItem)

        val fullItemPriceWithCurrentQuantity =
            list?.get(position)?.qty?.times(list?.get(position)?.price!!)
        holder.binding.priceItem.text = fullItemPriceWithCurrentQuantity.toString()
    }

    override fun getItemCount(): Int = list?.size ?: 0
    fun updateItem(item: ItemX) {
        val itemIdx = list?.indexOfFirst { itemX -> itemX.itemId == item.itemId }
        list?.toMutableList()?.set(itemIdx!!, item)
        notifyDataSetChanged()
        Log.i("TAG", "Adapter Update --> ${list?.get(itemIdx!!)?.qty}")
    }

    inner class Holder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.btnAdd.setOnClickListener {
                onUserClicks?.onClickPlus(list?.get(layoutPosition)!!.itemId)
            }
            binding.btnSub.setOnClickListener {
                onUserClicks?.onClickMines(list?.get(layoutPosition)!!.itemId)
            }
        }
    }

    interface OnUserClicks {
        fun onClickPlus(id: Int)
        fun onClickMines(id: Int)
    }
}