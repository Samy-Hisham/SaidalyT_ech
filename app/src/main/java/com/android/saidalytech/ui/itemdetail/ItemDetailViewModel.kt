package com.android.saidalytech.ui.itemdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.saidalytech.model.ModelFailure
import com.android.saidalytech.model.ModelItemDetail
import com.android.saidalytech.network.SingleLiveEvent
import com.android.saidalytech.network.WebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemDetailViewModel
@Inject constructor(private val webServices: WebServices) : ViewModel() {

    private var _successMD = SingleLiveEvent<ModelItemDetail>()
    val successMD get() = _successMD

    private var _failureMD = SingleLiveEvent<ModelFailure>()
    val failureMD get() = _failureMD

    private var _progress = SingleLiveEvent<Boolean>()
    val progressMD get() = _progress

    fun getItemDetail(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                val data = webServices.getItemDetail(id)
                successMD.postValue(data)
                progressMD.postValue(false)
            } catch (e: Exception) {

                failureMD.postValue(ModelFailure(e.toString()))
                progressMD.postValue(false)
            }
        }
    }


}