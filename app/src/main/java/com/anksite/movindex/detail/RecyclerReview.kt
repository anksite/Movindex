package com.anksite.movindex.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anksite.movindex.R
import com.anksite.movindex.api.model.Review
import com.anksite.movindex.databinding.RowListReviewBinding
import com.bumptech.glide.Glide
import com.github.marlonlom.utilities.timeago.TimeAgo
import com.github.marlonlom.utilities.timeago.TimeAgoMessages
import java.text.SimpleDateFormat
import java.util.Locale


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
        Glide.with(mContext)
            .load("https://image.tmdb.org/t/p/w45/"+dataReview.author_details.avatar_path)
            .circleCrop()
            .placeholder(R.drawable.i_account)
            .into(holder.binding.ivAvatar)
        holder.binding.tvAuthor.text = dataReview.author
        holder.binding.tvDate.text = getTimeAgo(dataReview.updated_at)
        holder.binding.tvReview.text = dataReview.content
        holder.binding.tvRating.text = dataReview.author_details.rating

        if(dataReview.author_details.rating==null){
            holder.binding.ivStar.visibility = View.GONE
            holder.binding.tvRating.visibility = View.GONE
        }
    }

    class ViewHolderHuruf(b: RowListReviewBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }

    fun getTimeAgo(date: String): String{
        val dateTime = date.split(".").first()
        val milis = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US).parse(dateTime).time

        val LocaleBylanguageTag = Locale.forLanguageTag("en")
        val messages = TimeAgoMessages.Builder().withLocale(LocaleBylanguageTag).build()
        return TimeAgo.using(milis, messages)
    }
}
