package com.example.moviesapp.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.moviesapp.R
import com.example.moviesapp.databinding.FragmentSigupBinding
import com.example.moviesapp.remote.EndPoints
import com.example.moviesapp.remote.RetrofitBuilder
import com.example.moviesapp.request.SignupRequest
import kotlinx.coroutines.launch
import java.io.IOException


class SigupFragment : Fragment() {
    private lateinit var binding: FragmentSigupBinding
    private lateinit var sharedpreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSigupBinding.inflate(layoutInflater, container, false)
        initviews()
        return binding.root
    }

    private fun initviews() {
        sharedpreferences = requireActivity().getSharedPreferences(
            "mySharedpref",
            Context.MODE_PRIVATE
        )
        if (sharedpreferences.getString("signupStatus","") == "signedup") {
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_to_home)
        }
        binding.signupbtn.setOnClickListener {
            signUp()
        }
        /*****************************************************/
        binding.login.setOnClickListener {
            findNavController().navigate(R.id.nav_to_login)
        }

    }

    private fun signUp() {
        lifecycleScope.launch {
            try {
                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
                val response = api.signUp(
                    SignupRequest(
                        name = binding.EditName.text.toString(),
                        age = binding.EditAge.text.toString().toInt(),
                        email = binding.EditMail.text.toString(),
                        password = binding.EditPass.text.toString()
                    )
                )
                when (response.code()) {
                    200 -> {
                        val user = response.body()
                        Toast.makeText(requireContext(), "welcome ${user?.name}", Toast.LENGTH_LONG)
                            .show()
                        findNavController().navigate(R.id.nav_to_home)
                        val editor: SharedPreferences.Editor = sharedpreferences.edit()
                        editor.putString("signupStatus", "signedup")
                        editor.apply()

                    }

                    400 -> {
                        val errorResponse = response.errorBody()?.string()
                        Toast.makeText(requireContext(), errorResponse, Toast.LENGTH_LONG).show()

                    }
                }
            }catch (e:IOException)
            {
                Log.i("exception",e.message.toString())
            }
        }
    }
}