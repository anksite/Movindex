package com.anksite.movindex.discover

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.anksite.movindex.api.model.Movie
import com.anksite.movindex.api.model.ResponseDiscover
import com.anksite.movindex.databinding.ActivityDiscoverBinding
import com.anksite.movindex.detail.ActivityDetail
import com.anksite.movindex.util.Cons
import com.anksite.movindex.util.DialogCustom
import com.anksite.movindex.util.DialogLoading

class ActivityDiscover : AppCompatActivity() {

    val TAG = "ActivityDiscover"
    val b by lazy { ActivityDiscoverBinding.inflate(layoutInflater) }
    val vmDiscover: VMDiscover by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }
    var mCurrentPage = 1
    var mTotalPage = 0
    val mListMovie = mutableListOf<Movie>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.rvDiscover.apply {
            layoutManager = GridLayoutManager(this@ActivityDiscover, 2)
            adapter = RecyclerDiscover(mListMovie, ::onEndScroll, ::onClickMovie)
        }

        mDialogLoading.show()
        vmDiscover.responseDiscover.observe(this, ::handleResponse)
        vmDiscover.error.observe(this, ::onError)

        vmDiscover.discover(mCurrentPage)
    }

    fun handleResponse(response: ResponseDiscover) {
        mDialogLoading.cancel()
        b.pbLoading.visibility = View.GONE
        mTotalPage = response.total_pages
        (b.rvDiscover.adapter as RecyclerDiscover).addList(response.results)

    }

    fun onError(response: String) {
        mDialogLoading.cancel()
        b.pbLoading.visibility = View.GONE
        DialogCustom(this)
            .setTitle("Error!")
            .setMessage(response)
            .show()
    }

    fun onClickMovie(dataMovie: Movie){
        Intent(this, ActivityDetail::class.java).also {
            it.putExtra(Cons.ID, dataMovie.id)
            startActivity(it)
        }
    }

    fun onEndScroll() {
        b.pbLoading.visibility = View.VISIBLE
        if (mCurrentPage < mTotalPage) {
            vmDiscover.discover(++mCurrentPage)
        }
    }
}
