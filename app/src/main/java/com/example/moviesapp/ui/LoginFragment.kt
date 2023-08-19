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
import com.example.moviesapp.databinding.FragmentLoginBinding
import com.example.moviesapp.databinding.ToolbarBinding
import com.example.moviesapp.remote.EndPoints
import com.example.moviesapp.remote.RetrofitBuilder
import com.example.moviesapp.request.SigninRequest
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch
import java.io.IOException

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var sharedpreferences: SharedPreferences
        private lateinit var bottomNavigationView : BottomNavigationView
        private lateinit var topNavView: View

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLoginBinding.inflate(layoutInflater,container,false)
        initviews()
        return binding.root
    }

    private fun initviews() {
        topNavView = requireActivity().findViewById(R.id.top_bar)
        bottomNavigationView = requireActivity().findViewById<BottomNavigationView>(R.id.bot_bar)
        sharedpreferences = requireActivity().getSharedPreferences("mySharedpref",Context.MODE_PRIVATE)
        if (sharedpreferences.getString("loginStatus","") == "loggedin") {
            findNavController().popBackStack()
            findNavController().navigate(R.id.nav_to_home)
        }
        binding.signinbtn.setOnClickListener{
            signIn()
        }
        /****************************************************************/
        binding.signuptext.setOnClickListener{

            findNavController().navigate(R.id.nav_to_signup)
            val editor: SharedPreferences.Editor = sharedpreferences.edit()
            editor.putString("signupStatus", "")
            editor.apply()

        }
    }



    private fun signIn() {
        lifecycleScope.launch {
            try {
                val api = RetrofitBuilder.retrofit.create(EndPoints::class.java)
                val response = api.signIn(
                    SigninRequest(
                        email = binding.EditMail.text.toString(),
                        password = binding.EditPass.text.toString()
                    )
                )
                when (response.code()) {
                    200 -> {
                        val info = response.body()
                        Toast.makeText(
                            requireContext(),
                            "welcome back",
                            Toast.LENGTH_SHORT
                        ).show()
                        findNavController().navigate(R.id.nav_to_home)
                        val editor: SharedPreferences.Editor = sharedpreferences.edit()
                        editor.apply()

                    }

                    401-> {
                        val errorResponse = response.errorBody()?.string()
                        Toast.makeText(requireContext(), errorResponse, Toast.LENGTH_SHORT).show()

                    }
                }
            } catch (e: IOException) {
                Log.i("exception", e.message.toString())
            }
        }
    }
    override fun onResume() {
        super.onResume()
        topNavView.visibility = View.GONE
        bottomNavigationView.visibility = View.GONE
    }

    override fun onStop() {
        super.onStop()
        topNavView.visibility = View.VISIBLE
        bottomNavigationView.visibility = View.VISIBLE
    }
}