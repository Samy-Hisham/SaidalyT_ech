package com.android.saidalytech.model

data class ModelPreOrderItem(
    val date: String,
    val items: List<Item>,
    val orderId: Int
)