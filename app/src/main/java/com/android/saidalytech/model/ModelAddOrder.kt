package com.android.saidalytech.model

data class ModelAddOrder(
    val customerId: String,
    val items: List<ItemX>,
    val userId: String
)