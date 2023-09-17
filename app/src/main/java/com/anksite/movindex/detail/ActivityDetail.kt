package com.anksite.movindex.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.anksite.movindex.Cons
import com.anksite.movindex.DialogCustom
import com.anksite.movindex.DialogLoading
import com.anksite.movindex.R
import com.anksite.movindex.ToolBatch
import com.anksite.movindex.api.model.Genre
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
            .placeholder(R.drawable.i_movie)
            .into(b.ivBackdrop)
        b.b.tvGenres.text = getGenres(responseMovie.genres)
        b.b.tvVoteAvg.text = responseMovie.voteAverage.substring(0, 3)
        b.b.tvVoteCount.text = responseMovie.voteCount.toString() + " votes"
        b.b.tvReleaseDate.text = ToolBatch.formatDate(responseMovie.releaseDate)
        b.b.tvRuntime.text = formatRuntime(responseMovie.runtime)
        b.b.tvOverview.text = responseMovie.overview
    }

    fun handleVideo(responseVideo: ResponseVideo) {
        responseCount()

        if(responseVideo.results.isEmpty()){
            b.b.tvTrailer.visibility = View.GONE
        } else {
            var trailer = responseVideo.results.filter { it.type == "Trailer" }
            trailer = trailer.filter { it.site == "YouTube" }
            trailer = trailer.filter { it.official }

            val mAdapter = RecyclerTrailer(trailer) { videoId ->
                startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("http://www.youtube.com/watch?v=$videoId")
                    )
                )
            }

            b.b.rvTrailer.apply {
                layoutManager = LinearLayoutManager(
                    this@ActivityDetail,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                adapter = mAdapter
            }
        }
    }

    fun handleReview(responseReview: ResponseReview) {
        responseCount()

        if(responseReview.results.isEmpty()){
            b.b.tvReview.visibility = View.GONE
        } else {
            val mAdapter = RecyclerReview(responseReview.results)
            b.b.rvReview.apply {
                layoutManager = LinearLayoutManager(this@ActivityDetail)
                adapter = mAdapter
            }
        }
    }

    fun onError(response: String) {
        responseCount()
        DialogCustom(this)
            .setTitle("Error!")
            .setMessage(response)
            .setCancelable(false)
            .setOnPositiveListener { finish() }
            .show()
    }

    fun responseCount() {
        ++mResponseCount
        if (mResponseCount == 3) {
            mDialogLoading.cancel()
        }
    }

    fun getGenres(listGenre: List<Genre>): String {
        var genres = ""
        listGenre.forEach {
            genres = genres + ", " + it.name
        }
        return genres.substring(2)
    }

    fun formatRuntime(runtime: Int): String {
        var duration = ""
        if (runtime >= 60) {
            val h = runtime / 60
            val m = runtime % 60
            duration = h.toString() + "h " + m.toString() + "m"
        } else {
            duration = runtime.toString() + "m"
        }

        return duration
    }

}
