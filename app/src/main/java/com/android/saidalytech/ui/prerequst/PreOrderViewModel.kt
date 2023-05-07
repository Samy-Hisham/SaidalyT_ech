package com.android.saidalytech.ui.prerequst

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.saidalytech.model.ModelFailure
import com.android.saidalytech.model.ModelPreOrder
import com.android.saidalytech.network.SingleLiveEvent
import com.android.saidalytech.network.WebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("NAME_SHADOWING")
@HiltViewModel
class PreOrderViewModel
@Inject constructor(private val webServices: WebServices) : ViewModel() {

    private var _successMD = SingleLiveEvent<ModelPreOrder>()
    val successMD get() = _successMD

    private var _failureMD = SingleLiveEvent<ModelFailure>()
    val failureMD get() = _failureMD

    private var _progress = SingleLiveEvent<Boolean>()
    val progressMD get() = _progress

    fun getPreOrder(userId: String) {

        viewModelScope.launch(Dispatchers.IO) {
            progressMD.postValue(true)

            try {
                val data = webServices.getPreOrder(userId)
                successMD.postValue(data)
                progressMD.postValue(false)
            } catch (e: Exception) {
                failureMD.postValue(ModelFailure(e.toString()))
                progressMD.postValue(false)
            }
        }
    }
}