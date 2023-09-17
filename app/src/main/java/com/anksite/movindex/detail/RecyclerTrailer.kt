package com.anksite.movindex.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anksite.movindex.ToolBatch
import com.anksite.movindex.api.model.Video
import com.anksite.movindex.databinding.RowListTrailerBinding
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.Locale

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
        Glide.with(mContext).load("https://img.youtube.com/vi/${dataTrailer.key}/0.jpg").into(holder.binding.ivThumbnail)
        holder.binding.tvTitle.text = dataTrailer.name
        holder.binding.tvRelease.text = ToolBatch.formatDateFull(dataTrailer.published_at.split("T").first())

        holder.binding.root.setOnClickListener { onClickTrailer(dataTrailer.key) }
    }

    class ViewHolderHuruf(b: RowListTrailerBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }
}