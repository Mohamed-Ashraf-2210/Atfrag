package com.example.moviesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentHomeBinding
import com.example.moviesapp.databinding.ItemMovieBinding
import com.example.moviesapp.model.Movies
import com.example.moviesapp.ui.HomeFragmentDirections
import com.example.moviesapp.ui.MovieDetailsFragment

class MovieAdapter(private val listOfMovie : List<Movies>, val context : Context) : RecyclerView.Adapter<MovieViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ItemMovieBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MovieViewHolder(itemBinding)
    }

    override fun getItemCount(): Int   = listOfMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.movieTitle.text = listOfMovie[position].name
        holder.movieData.text = listOfMovie[position].dateOfProduction
        Glide.with(context).load(listOfMovie[position].image).into(holder.movieImg)
        // responsible for each item
        holder.itemView.setOnClickListener{
            val action = HomeFragmentDirections.navToMovieDetails(listOfMovie[position].Id)
            it.findNavController().navigate(action)
//            val action = HomeFragmentDirections.navToMovieDetails(listOfMovie[position].Id)
//            it.findNavController().navigate(action)
        }
    }

}

class MovieViewHolder(itemMovieBinding: ItemMovieBinding ) : RecyclerView.ViewHolder(itemMovieBinding.root){
    val movieTitle = itemMovieBinding.movieTitle
    val movieData = itemMovieBinding.movieData
    val movieImg = itemMovieBinding.movieImage
}