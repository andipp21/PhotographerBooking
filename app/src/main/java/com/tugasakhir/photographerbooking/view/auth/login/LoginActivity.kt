package com.tugasakhir.photographerbooking.view.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityLoginBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.utils.AuthHelper
import com.tugasakhir.photographerbooking.utils.FormValidationHelper
import com.tugasakhir.photographerbooking.view.client.activity.ClientActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.viewModel.auth.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel
        get() = ViewModelProvider(this).get(LoginViewModel::class.java)

    var stateEmail = false
    var statePassword = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.isEnabled = false
        binding.btnLogin.isClickable = false

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    if (FormValidationHelper.isValidEmail(s.toString())) {
                        stateEmail = true
                        binding.etEmailLayout.error = null
                    } else {
                        stateEmail = false
                        binding.etEmailLayout.error = "Pastikan format email benar"
                    }
                } else {
                    stateEmail = false
                    binding.etEmailLayout.error = "Email harus diisi"
                }

                buttonState()
            }

        })

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    statePassword = true
                    binding.etPasswordLayout.error = null
                } else {
                    statePassword = false
                    binding.etPasswordLayout.error = "Password harus diisi"
                }

                buttonState()
            }

        })
    }

    fun buttonState() {
        if (stateEmail && statePassword) {
            binding.btnLogin.isEnabled = true
            binding.btnLogin.isClickable = true

            binding.btnLogin.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()

                viewModel.login(email, AuthHelper.hashPassword(password).toString())
                observerViewModel(viewModel)
                Log.d("response", "${viewModel.responseLiveData}")
            }
        } else {
            binding.btnLogin.isEnabled = false
            binding.btnLogin.isClickable = false
        }
    }

    private fun observerViewModel(viewModel: LoginViewModel) {
        viewModel.responseLiveData.observe(this, {
            //binding.progressSignup.visibility = View.GONE


            if (it == "Login Successfully") {
                Snackbar.make(binding.root, it.toString(), Snackbar.LENGTH_LONG)
                    .setAction("Close") { }
                    .setBackgroundTint(getColor(R.color.colorPrimaryDark))
                    .setTextColor(getColor(R.color.colorWhite))
                    .setActionTextColor(getColor(R.color.colorAccent))
                    .show()

                viewModel.getUser()

                observerUser(viewModel)
            } else if (it == "There is no user record corresponding to this identifier. The user may have been deleted."){
                Snackbar.make(
                    binding.root,
                    "Login Gagal, Email anda tidak terdaftar atau telah terhapus",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Close") { }
                    .setBackgroundTint(getColor(R.color.colorRed))
                    .setTextColor(getColor(R.color.colorWhite))
                    .setActionTextColor(getColor(R.color.colorPrimary))
                    .show()
            } else {
                Snackbar.make(
                    binding.root,
                    "Login Gagal, Password anda salah",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Close") { }
                    .setBackgroundTint(getColor(R.color.colorRed))
                    .setTextColor(getColor(R.color.colorWhite))
                    .setActionTextColor(getColor(R.color.colorPrimary))
                    .show()
            }
        })
    }

    private fun observerUser(viewModel: LoginViewModel) {
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

            if (user.role == "photographer") {
                val intent = Intent(this, PhotographerActivity::class.java)
                intent.putExtra("user", user)

                startActivity(intent)

                finish()
            } else if (user.role == "client") {
                val intent = Intent(this, ClientActivity::class.java)
                intent.putExtra("user", user)

                startActivity(intent)

                finish()
            }
        })
    }


}