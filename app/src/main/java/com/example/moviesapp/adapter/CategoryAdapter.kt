package com.example.moviesapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviesapp.ui.CategoryFragmentDirections
import com.example.moviesapp.databinding.ItemCategoryBinding
import com.example.moviesapp.ui.CategoryFragment
import com.example.moviesapp.model.Category


class CategoryAdapter (private val listOfCategories : List<Category>, val context : Context) : RecyclerView.Adapter<CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.CategoryName.text = listOfCategories[position].name
        Glide.with(context).load(listOfCategories[position].image).into(holder.categoryImg)
        holder.itemView.setOnClickListener{view ->
            val action = CategoryFragmentDirections.navToMoviesFragment(listOfCategories[position]._id)
            view.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int = listOfCategories.size
}

class CategoryViewHolder(itemCategoryBinding: ItemCategoryBinding): RecyclerView.ViewHolder(itemCategoryBinding.root){
    val CategoryName = itemCategoryBinding.categoryName
    val categoryImg = itemCategoryBinding.categoryImage
}