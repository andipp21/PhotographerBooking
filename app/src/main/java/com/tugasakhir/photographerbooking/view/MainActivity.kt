package com.tugasakhir.photographerbooking.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityMainBinding
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.utils.AuthHelper
import com.tugasakhir.photographerbooking.view.auth.login.LoginActivity
import com.tugasakhir.photographerbooking.view.auth.register.RegisterActivity
import com.tugasakhir.photographerbooking.view.client.ClientActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.viewModel.auth.login.LoginViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: LoginViewModel
        get() = ViewModelProvider(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (AuthHelper.checkAuth()) {
            val currentUser: FirebaseUser? = FirebaseAuth.getInstance().getCurrentUser()
            Log.d("Current User",currentUser.toString())

            viewModel.getUser()

            observerUser(viewModel)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.btnRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun observerUser(viewModel: LoginViewModel){
        viewModel.responseLiveUser.observe(this, {
            //binding.progressSignup.visibility = View.GONE

            Snackbar.make(binding.root, "Got it", Snackbar.LENGTH_LONG)
                .setAction("Close") {
                }
                .setBackgroundTint(getColor(R.color.colorPrimaryDark))
                .setTextColor(getColor(R.color.colorWhite))
                .setActionTextColor(getColor(R.color.colorWhite))
                .show()

            val user: User = it

            if (user.role == "photographer"){
                val intent = Intent(this, PhotographerActivity::class.java)
                intent.putExtra("user",user)

                startActivity(intent)

                finish()
            } else if (user.role == "client"){
                val intent = Intent(this, ClientActivity::class.java)
                intent.putExtra("user",user)

                startActivity(intent)

                finish()
            }
        })
    }
}