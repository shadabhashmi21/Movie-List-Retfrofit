package com.e.retrofit

import android.util.Log.d
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.e.models.MovieModel
import com.squareup.picasso.Picasso

class MovieAdapter(private val movies: List<MovieModel>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieTitle : TextView = itemView.findViewById(R.id.movieTitle)
        val moviePoster : ImageView = itemView.findViewById(R.id.moviePoster)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.movie_grid, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = movies[position]
        val imgUrl = "https://image.tmdb.org/t/p/w500/${movie.poster_path}"
        d("Example", "Name : ${movie.title}")
        holder.movieTitle.text = movie.title
        Picasso.get().load(imgUrl).into(holder.moviePoster)
    }

    override fun getItemCount() = movies.size

}
