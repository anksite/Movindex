package com.anksite.movindex.discover

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anksite.movindex.R
import com.anksite.movindex.api.model.Movie
import com.anksite.movindex.databinding.RowListDiscoverBinding
import com.bumptech.glide.Glide

class RecyclerDiscover(
    private val listMovie: MutableList<Movie>,
    val onEndScroll: () -> Unit,
    val onClickMovie: (Movie) -> Unit
) :
    RecyclerView.Adapter<RecyclerDiscover.ViewHolderHuruf>() {
    val TAG = "RecyclerDiscover"
    lateinit var mContext: Context
    lateinit var binding: RowListDiscoverBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderHuruf {
        mContext = parent.context
        binding = RowListDiscoverBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return ViewHolderHuruf(binding)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: ViewHolderHuruf, position: Int) {
        val dataMovie = listMovie[position]
        Glide.with(mContext).load("https://image.tmdb.org/t/p/w185" + dataMovie.poster_path)
            .placeholder(R.drawable.i_movie)
            .into(holder.binding.ivPoster)
        holder.binding.tvTitle.text = dataMovie.title

        holder.binding.clParent.setOnClickListener { onClickMovie(dataMovie) }

        if(position==listMovie.size-1){
            onEndScroll()
        }
    }

    class ViewHolderHuruf(b: RowListDiscoverBinding) : RecyclerView.ViewHolder(b.root) {
        var binding = b
    }

    public fun addList(list: List<Movie>){
        val currentSize = listMovie.size
        list.forEachIndexed { index, movie ->
            listMovie.add(movie)
            notifyItemInserted(currentSize+index)
        }
    }
}