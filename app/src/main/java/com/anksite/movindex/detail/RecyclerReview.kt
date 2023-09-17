package com.anksite.movindex.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anksite.movindex.api.model.Review
import com.anksite.movindex.api.model.Video
import com.anksite.movindex.databinding.RowListReviewBinding
import com.bumptech.glide.Glide

class RecyclerReview(
    private val listReview: List<Review>
) :
    RecyclerView.Adapter<RecyclerReview.ViewHolderHuruf>() {
    lateinit var mContext: Context
    lateinit var binding: RowListReviewBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHuruf {
        mContext = parent.context
        binding = RowListReviewBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolderHuruf(binding)
    }

    override fun getItemCount(): Int = listReview.size

    override fun onBindViewHolder(holder: ViewHolderHuruf, position: Int) {
        val dataReview = listReview[position]
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w45/"+dataReview.author_details.avatar_path).into(holder.binding.ivAvatar)
        holder.binding.tvAuthor.text = dataReview.author
        holder.binding.tvDate.text = dataReview.updated_at
        holder.binding.tvOverview.text = dataReview.content
        holder.binding.tvRating.text = dataReview.author_details.rating
    }

    class ViewHolderHuruf(b: RowListReviewBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }
}