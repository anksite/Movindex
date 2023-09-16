package com.anksite.movindex.discover

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.anksite.movindex.detail.ActivityDetail
import com.anksite.movindex.Cons
import com.anksite.movindex.DialogCustom
import com.anksite.movindex.DialogLoading
import com.anksite.movindex.api.model.ResponseDiscover
import com.anksite.movindex.databinding.ActivityDiscoverBinding

class ActivityDiscover : AppCompatActivity() {

    val b by lazy { ActivityDiscoverBinding.inflate(layoutInflater) }
    val vmDiscover: VMDiscover by viewModels()
    val mDialogLoading by lazy { DialogLoading(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        mDialogLoading.show()
        vmDiscover.responseDiscover.observe(this, ::handleResponse)
        vmDiscover.error.observe(this, ::onError)

        vmDiscover.discover()
    }

    fun handleResponse(response: ResponseDiscover) {
        mDialogLoading.cancel()
        val mAdapter = RecyclerDiscover(response.results) { dataMovie ->
            Intent(this, ActivityDetail::class.java).also {
                it.putExtra(Cons.ID, dataMovie.id)
                startActivity(it)
            }
        }

        b.rvDiscover.apply {
            layoutManager = GridLayoutManager(this@ActivityDiscover, 2)
            adapter = mAdapter
        }
    }

    fun onError(response: String) {
        mDialogLoading.cancel()
        DialogCustom(this)
            .setMessage("Error!")
            .setMessage(response)
            .show()
    }
}