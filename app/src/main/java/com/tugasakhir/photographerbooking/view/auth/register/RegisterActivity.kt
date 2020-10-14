package com.tugasakhir.photographerbooking.view.auth.register

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityRegisterBinding
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.utils.AuthHelper
import com.tugasakhir.photographerbooking.view.auth.register.fragment.RegisterAddDataFragment
import com.tugasakhir.photographerbooking.view.auth.register.fragment.RegisterAddPasswordFragment
import com.tugasakhir.photographerbooking.view.auth.register.fragment.RegisterSelectRoleFragment
import com.tugasakhir.photographerbooking.viewModel.auth.register.RegisterViewModel

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel
        get() = ViewModelProvider(this).get(RegisterViewModel::class.java)

    lateinit var fullname: String
    lateinit var email: String
    lateinit var password: String
    lateinit var role: String
    lateinit var city : String
    lateinit var phoneNumber: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        //set default fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContainerRegister, RegisterSelectRoleFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    fun saveRole(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContainerRegister, RegisterAddDataFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    fun saveData(){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flContainerRegister, RegisterAddPasswordFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    fun register(){
        binding.progressSignup.visibility = View.VISIBLE
        val user = User(
            fullname = fullname,
            email = email,
            password = AuthHelper.hashPassword(password).toString(),
            role = role,
            city = city,
            phoneNumber = phoneNumber,
            profilePicture = "https://firebasestorage.googleapis.com/v0/b/photographerbooking-7d086.appspot.com/o/blank_profile_picture.png?alt=media&token=d6258a77-c499-4fa8-a019-9f780bba1d31",
            about = ""
        )
        viewModel.registerUser(user)

        observerViewModel(viewModel)
    }

    private fun observerViewModel(viewModel: RegisterViewModel){
        viewModel.responseLiveData.observe(this, {
            binding.progressSignup.visibility = View.GONE

            Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG)
                .setAction("Close") { }
                .setBackgroundTint(getColor(R.color.colorPrimaryDark))
                .setTextColor(getColor(R.color.colorWhite))
                .setActionTextColor(getColor(R.color.colorWhite))
                .show()

            finish()
        })
    }
}