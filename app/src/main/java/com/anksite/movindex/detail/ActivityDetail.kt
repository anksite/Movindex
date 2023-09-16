package com.anksite.movindex.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.anksite.movindex.Cons
import com.anksite.movindex.DialogCustom
import com.anksite.movindex.DialogLoading
import com.anksite.movindex.R
import com.anksite.movindex.api.model.ResponseMovie
import com.anksite.movindex.api.model.ResponseReview
import com.anksite.movindex.api.model.ResponseVideo
import com.anksite.movindex.databinding.ActivityDetailBinding
import com.anksite.movindex.databinding.ActivityDiscoverBinding
import com.anksite.movindex.discover.VMDiscover
import com.bumptech.glide.Glide

class ActivityDetail : AppCompatActivity() {

    val TAG = "ActivityDetail"
    val b by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    val vmDetail: VMDetail by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }
    var mResponseCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        mDialogLoading.show()
        vmDetail.responseMovie.observe(this, ::handleMovie)
        vmDetail.responseVideo.observe(this, ::handleVideo)
        vmDetail.responseReview.observe(this, ::handleReview)
        vmDetail.error.observe(this, ::onError)

        intent.getStringExtra(Cons.ID)?.let {
            vmDetail.movie(it)
            vmDetail.video(it)
            vmDetail.reviews(it)
        }
    }

    fun handleMovie(responseMovie: ResponseMovie) {
        responseCount()
        Glide.with(this).load("https://image.tmdb.org/t/p/w780"+responseMovie.backdropPath).into(b.ivBackdrop)
        b.tvRuntime.text = responseMovie.runtime.toString()
        b.tvGenres.text = responseMovie.genres.toString()
        b.tvVoteAvg.text = responseMovie.voteAverage.toString()
        b.tvVoteCount.text = responseMovie.voteCount.toString()
        b.tvReleaseDate.text = responseMovie.releaseDate
        b.tvTitle.text = responseMovie.title
        b.tvTagline.text = responseMovie.tagline
        b.tvOverview.text = responseMovie.overview
    }

    fun handleVideo(responseVideo: ResponseVideo) {
        responseCount()
        b.rvTrailer
    }

    fun handleReview(responseReview: ResponseReview) {
        responseCount()
        b.rvReview
    }

    fun onError(response: String) {
        responseCount()
        DialogCustom(this)
            .setMessage("Error!")
            .setMessage(response)
            .show()
    }

    fun responseCount(){
        ++mResponseCount
        if(mResponseCount==3){
            mDialogLoading.cancel()
        }
    }

}
