package com.anksite.movindex.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anksite.movindex.api.ApiUtils
import com.anksite.movindex.api.model.ResponseMovie
import com.anksite.movindex.api.model.ResponseReview
import com.anksite.movindex.api.model.ResponseVideo
import kotlinx.coroutines.launch

class VMDetail : ViewModel() {
    val TAG = "VMDetail"
    val mApi = ApiUtils().getApiService()

    private val mResponseMovie = MutableLiveData<ResponseMovie>()
    val responseMovie: LiveData<ResponseMovie> = mResponseMovie

    private val mResponseVideo = MutableLiveData<ResponseVideo>()
    val responseVideo: LiveData<ResponseVideo> = mResponseVideo

    private val mResponseReview = MutableLiveData<ResponseReview>()
    val responseReview: LiveData<ResponseReview> = mResponseReview

    private val mError = MutableLiveData<String>()
    val error: LiveData<String> = mError

    fun movie(id: String) {
        viewModelScope.launch {
            mApi.movie(id).onSuccess {
                mResponseMovie.postValue(it)
            }.onFailure {
                val errorMessage = it.localizedMessage
                mError.postValue(errorMessage)
            }
        }
    }

    fun reviews(id: String) {
        viewModelScope.launch {
            mApi.reviews(id).onSuccess {
                mResponseReview.postValue(it)
            }.onFailure {
                val errorMessage = it.localizedMessage
                mError.postValue(errorMessage)
            }
        }
    }

    fun video(id: String) {
        viewModelScope.launch {
            mApi.video(id).onSuccess {
                mResponseVideo.postValue(it)
            }.onFailure {
                val errorMessage = it.localizedMessage
                mError.postValue(errorMessage)
            }
        }
    }

}
