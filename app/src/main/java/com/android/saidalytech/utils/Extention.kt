package com.android.saidalytech.utils

import android.content.Context
import android.view.View
import android.widget.Toast

fun Any.showToast(context: Context, massage: Any?) {
    Toast.makeText(context, "$massage", Toast.LENGTH_LONG).show()
}

fun Any.showToastShow(context: Context, massage: Any?) {
    Toast.makeText(context, "$massage", Toast.LENGTH_SHORT).show()
}

fun View.visibilityGone() {
    this.visibility = View.GONE
}

fun View.visibilityVisible() {
    this.visibility = View.VISIBLE
}

