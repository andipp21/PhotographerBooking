package com.tugasakhir.photographerbooking.view.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityLoginBinding
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.utils.AuthHelper
import com.tugasakhir.photographerbooking.view.client.activity.ClientActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.viewModel.auth.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel
        get() = ViewModelProvider(this).get(LoginViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            viewModel.login(email, AuthHelper.hashPassword(password).toString())
            observerViewModel(viewModel)
            Log.d("response","${viewModel.responseLiveData}")
        }
    }

    private fun observerViewModel(viewModel: LoginViewModel){
        viewModel.responseLiveData.observe(this, {
            //binding.progressSignup.visibility = View.GONE

            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                .setAction("Close") { }
                .setBackgroundTint(getColor(R.color.colorPrimaryDark))
                .setTextColor(getColor(R.color.colorPrimary))
                .setActionTextColor(getColor(R.color.colorAccent))
                .show()

            Log.d("res", it)

            if (it == "Login Successfully") {
                viewModel.getUser()

                observerUser(viewModel)
            }
        })
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