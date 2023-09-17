package com.anksite.movindex.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.anksite.movindex.Cons
import com.anksite.movindex.DialogCustom
import com.anksite.movindex.DialogLoading
import com.anksite.movindex.api.model.ResponseMovie
import com.anksite.movindex.api.model.ResponseReview
import com.anksite.movindex.api.model.ResponseVideo
import com.anksite.movindex.databinding.ActivityDetailBinding
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

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

        b.toolbar.setNavigationOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    fun handleMovie(responseMovie: ResponseMovie) {
        responseCount()
        b.toolbar.title = responseMovie.title
        b.tvSubtitle.text = responseMovie.tagline
        Glide.with(this).load("https://image.tmdb.org/t/p/w780" + responseMovie.backdropPath)
            .into(b.ivBackdrop)
        b.tvRuntime.text = responseMovie.runtime.toString()
        b.tvGenres.text = responseMovie.genres.toString()
        b.tvVoteAvg.text = responseMovie.voteAverage.toString()
        b.tvVoteCount.text = responseMovie.voteCount.toString()
        b.tvReleaseDate.text = responseMovie.releaseDate
        b.tvOverview.text = responseMovie.overview
    }

    fun handleVideo(responseVideo: ResponseVideo) {
        responseCount()

        val mAdapter = RecyclerTrailer(responseVideo.results) { videoId ->
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=$videoId")
                )
            )
        }

        b.rvTrailer.apply {
            layoutManager = LinearLayoutManager(
                this@ActivityDetail,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = mAdapter
        }
    }

    fun handleReview(responseReview: ResponseReview) {
        responseCount()
        val mAdapter = RecyclerReview(responseReview.results)
        b.rvReview.apply {
            layoutManager = LinearLayoutManager(this@ActivityDetail)
            adapter = mAdapter
        }
    }

    fun onError(response: String) {
        responseCount()
        DialogCustom(this)
            .setMessage("Error!")
            .setMessage(response)
            .show()
    }

    fun responseCount() {
        ++mResponseCount
        if (mResponseCount == 3) {
            mDialogLoading.cancel()
        }
    }

}
