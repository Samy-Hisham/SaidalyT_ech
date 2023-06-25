package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.android.saidalytech.databinding.ItemPerOrderBinding
import com.android.saidalytech.model.*
import java.text.SimpleDateFormat
import java.util.Locale
class AdapterPreOrderRecycle : RecyclerView.Adapter<AdapterPreOrderRecycle.Holder>() {

    var list: MutableList<ModelPreOrderItem>? = null
    var onUserClicks: OnUserClicks? = null
    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setList1")
    fun setList(list: MutableList<ModelPreOrderItem>?) {
        this.list = list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            ItemPerOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: Holder, position: Int) {

        val data = list?.get(position)

        holder.binding.apply {
            txtOrder.text = data?.orderId.toString()

            if (data?.date?.length == 23){
                txtDate.text = data.date.dropLast(13)
            }else{
                txtDate.text = data?.date?.dropLast(12)
            }
        }
    }

    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(val binding: ItemPerOrderBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                onUserClicks?.onClick(list?.get(layoutPosition)!!.orderId)
            }
        }
    }

    interface OnUserClicks {
        fun onClick(id: Int)
    }
}