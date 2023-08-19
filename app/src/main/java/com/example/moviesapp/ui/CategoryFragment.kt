package com.example.moviesapp.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.moviesapp.adapter.CategoryAdapter
import com.example.moviesapp.databinding.FragmentCategoryBinding
import com.example.moviesapp.remote.EndPoints
import com.example.moviesapp.remote.RetrofitBuilder
import com.example.moviesapp.model.Category
import kotlinx.coroutines.launch
import java.io.IOException

class CategoryFragment : Fragment() {

    private lateinit var binding : FragmentCategoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoryBinding.inflate(layoutInflater,container,false)
        initViews()
        return binding.root
    }

    private fun initViews() {
        binding.progressBar.visibility = View.VISIBLE
        getCategories()
    }

    private fun getCategories() {
        try {
            lifecycleScope.launch {
                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
                val response = api.getCategories()
                when(response.code()){
                    200 -> {
                        val listOfCategories = response.body()
                        initCategoriesAdapter(listOfCategories)
                    }
                }
            }
        }catch (e: IOException){
            Log.i("exception",e.message.toString())
        }
    }

    private fun initCategoriesAdapter(listOfCategories: List<Category>?) {
        listOfCategories?.let {
            binding.progressBar.visibility = View.GONE
            val categoriesAdapter = CategoryAdapter(it,requireContext())
            binding.categoryRV.adapter = categoriesAdapter
        }

    }

}