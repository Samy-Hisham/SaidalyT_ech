package com.android.saidalytech.ui.order

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.saidalytech.model.ItemX
import com.android.saidalytech.model.ModelAddOrder
import com.android.saidalytech.model.ModelFailure
import com.android.saidalytech.network.SingleLiveEvent
import com.android.saidalytech.network.WebServices
import com.android.saidalytech.uitls.MySharedPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
@HiltViewModel
class OrderViewModel
@Inject constructor(private val webServices: WebServices) : ViewModel() {

    private var _successMD = SingleLiveEvent<ModelAddOrder>()
    val successMD get() = _successMD

    private var _failureMD = SingleLiveEvent<ModelFailure>()
    val failureMD get() = _failureMD

    private var _progress = SingleLiveEvent<Boolean>()
    val progressMD get() = _progress

    fun sendOrder(items: List<ItemX>) {
        viewModelScope.launch(Dispatchers.IO) {
            progressMD.postValue(true)

            try {

                val newOrder = ModelAddOrder(customerId = "10", items = items, userId = MySharedPreference.getUserId().toString())
                val data = webServices.sendOrder(newOrder)
                successMD.postValue(data)
                progressMD.postValue(false)

            } catch (e: Exception) {

                failureMD.postValue(ModelFailure(e.toString()))
                progressMD.postValue(false)
            }
        }
    }

}