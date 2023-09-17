package com.anksite.movindex.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anksite.movindex.api.model.Video
import com.anksite.movindex.databinding.RowListTrailerBinding
import com.bumptech.glide.Glide

class RecyclerTrailer(
    private val listTrailer: List<Video>,
    val onClickTrailer: (String) -> Unit
) :
    RecyclerView.Adapter<RecyclerTrailer.ViewHolderHuruf>() {
    lateinit var mContext: Context
    lateinit var binding: RowListTrailerBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHuruf {
        mContext = parent.context
        binding = RowListTrailerBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolderHuruf(binding)
    }

    override fun getItemCount(): Int = listTrailer.size

    override fun onBindViewHolder(holder: ViewHolderHuruf, position: Int) {
        val dataTrailer = listTrailer[position]
        Glide.with(mContext).load("https://img.youtube.com/vi/vvazj_Ycw_w/0.jpg").into(holder.binding.ivThumbnail)
        holder.binding.tvTitle.text = dataTrailer.name
        holder.binding.tvRelease.text = dataTrailer.published_at

        holder.binding.root.setOnClickListener { onClickTrailer(dataTrailer.key) }
    }

    class ViewHolderHuruf(b: RowListTrailerBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }
}