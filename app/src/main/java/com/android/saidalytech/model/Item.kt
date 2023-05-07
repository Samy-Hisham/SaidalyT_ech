package com.android.saidalytech.model

data class Item(
    val imageName: String,
    val itemUnitId: Int,
    val name: String,
    val notes: String,
    val orderId: Int,
    val price: Double,
    val qty: Double,
    val total: Double
)