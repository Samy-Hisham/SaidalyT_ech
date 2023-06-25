package com.android.saidalytech.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.saidalytech.databinding.ItemPerOrderBinding
import com.android.saidalytech.databinding.ItemSelectedPerOrderBinding
import com.android.saidalytech.model.*
import com.bumptech.glide.Glide

class AdapterRoshetarRecycle : RecyclerView.Adapter<AdapterRoshetarRecycle.Holder>() {

    var list:  List<ItemXX>? = null
    var onUserClicks: AdapterPreOrderRecycle.OnUserClicks? = null

    @SuppressLint("NotifyDataSetChanged")
    @JvmName("setList1")
    fun setList(list: List<ItemXX>) {
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

//        holder.binding.apply {
//            txtOrder.text = data?.orderId.toString()
//            if (data?.date?.length == 23){
//                txtDate.text = data.date.dropLast(13)
//            }else{
//                txtDate.text = data?.date?.dropLast(12)
//            }

        holder.binding.apply {

            txtName.text = data?.name

            if (data?.notes.equals("string")) {
                txtNote.text = " "
            } else {
                txtNote.text = data?.notes
            }
            txtQuantity.text = data?.qty.toString()
            txtPrice.text = "${data?.price.toString()} EGP"
            txtTotal.text = "${data?.total.toString()} EGP"
        }

        Glide.with(holder.itemView.context)
            .load(data?.imageName)
            .into(holder.binding.imgItem)
    }


    override fun getItemCount(): Int = list?.size ?: 0

    inner class Holder(val binding: ItemSelectedPerOrderBinding) : RecyclerView.ViewHolder(binding.root) {
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