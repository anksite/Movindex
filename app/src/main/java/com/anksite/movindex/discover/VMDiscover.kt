package com.anksite.movindex.discover

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anksite.movindex.api.ApiUtils
import com.anksite.movindex.api.model.ResponseDiscover
import kotlinx.coroutines.launch

class VMDiscover : ViewModel() {
    val TAG = "VMDiscover"
    val mApi = ApiUtils().getApiService()

    private val mResponseDiscover = MutableLiveData<ResponseDiscover>()
    val responseDiscover: LiveData<ResponseDiscover> = mResponseDiscover

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    fun discover(page: Int) {
        viewModelScope.launch {
            mApi.discover(page).onSuccess {
                mResponseDiscover.postValue(it)
            }.onFailure {
                val errorMessage = it.localizedMessage
                mError.postValue(errorMessage)
            }

        }
    }

}