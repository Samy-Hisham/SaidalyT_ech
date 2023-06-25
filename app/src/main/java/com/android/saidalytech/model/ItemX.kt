package com.android.saidalytech.model

data class ItemX(
    var itemId: Int,
    var notes: String,
    var price: Double,
    var qty: Int,
    var itemImage : String = "",
    var itemName: String = "")
