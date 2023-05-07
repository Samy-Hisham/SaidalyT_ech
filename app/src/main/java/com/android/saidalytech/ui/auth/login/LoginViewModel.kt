package com.android.saidalytech.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.saidalytech.model.ModelFailure
import com.android.saidalytech.model.ModelLogin
import com.android.saidalytech.model.ModelLoginRegisterResponses
import com.android.saidalytech.network.WebServices
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject constructor(private val webServices: WebServices) : ViewModel() {

    //    private var _successMD = MutableLiveData<ModelLogin>()
//    val successMD get() = _successMD
//
    private var _failureMD = MutableLiveData<ModelFailure>()
    val failureMD get() = _failureMD

    private var _progress = MutableLiveData<Boolean>()
    val progressMD get() = _progress

    private var _loginLiveData = MutableLiveData<ModelLoginRegisterResponses>()
    val loginLiveData get() = _loginLiveData


    fun login(email: String, pass: String) {

        viewModelScope.launch(Dispatchers.IO) {
            progressMD.postValue(true)
            try {
                val data = webServices.login(ModelLogin(email, pass))

                loginLiveData.postValue(data)
                progressMD.postValue(false)

            } catch (e: Exception) {
                failureMD.postValue(ModelFailure(e.toString()))
                progressMD.postValue(false)
            }
        }
    }
}