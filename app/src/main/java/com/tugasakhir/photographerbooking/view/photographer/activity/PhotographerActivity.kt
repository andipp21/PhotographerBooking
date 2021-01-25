package com.tugasakhir.photographerbooking.view.photographer.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.MainActivity
import com.tugasakhir.photographerbooking.view.photographer.fragment.home.PhotographerHomeFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.order.PhotographerOrderFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.PhotographerProfileFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.review.PhotographerReviewFragment
import com.tugasakhir.photographerbooking.viewModel.auth.login.LoginViewModel
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel

class PhotographerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerBinding
    private lateinit var user: User

    private var viewModelPhotographer: PhotographerProfileViewModel? = null
    private var viewModelOrder: OrderViewModel? = null
    private var viewModelAuth: LoginViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        viewModelPhotographer =
            ViewModelProvider(this).get(PhotographerProfileViewModel::class.java)
        viewModelOrder = ViewModelProvider(this).get(OrderViewModel::class.java)
        viewModelAuth = ViewModelProvider(this).get(LoginViewModel::class.java)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)

        intent.getParcelableExtra<User>("user")?.let {
            user = it
        }

        //default Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flPhotographerMain,
                PhotographerHomeFragment(viewModelOrder!!)
            )
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        binding.botNavPhotographer.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.photographerHome -> {
                    goFragment(PhotographerHomeFragment(viewModelOrder!!))
                    true
                }
                R.id.photographerOrder -> {
                    goFragment(PhotographerOrderFragment(viewModelOrder!!))
                    true
                }
                R.id.photographerProfile -> {
                    goFragment(PhotographerProfileFragment.getUSer(user, viewModelPhotographer!!))
                    true
                }
                else -> false
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModelAuth?.getUser()
        viewModelAuth?.let { observeUser(it) }
    }

    private fun observeUser(viewModel: LoginViewModel) {
        viewModel.responseLiveUser.observe(this, Observer{
            user = it
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModelPhotographer = null
        viewModelOrder = null
        viewModelAuth = null
    }

    private fun goFragment(fm: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flPhotographerMain, fm)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.logout_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.btnLogout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}