package com.tugasakhir.photographerbooking.view.photographer.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.MainActivity
import com.tugasakhir.photographerbooking.view.photographer.fragment.PhotographerHomeFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.PhotographerInboxFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.order.PhotographerOrderFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.PhotographerProfileFragment

class PhotographerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)

        intent.getParcelableExtra<User>("user")?.let {
            user = it
        }

        //default Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flPhotographerMain, PhotographerHomeFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        binding.botNavPhotographer.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when(it.itemId){
                R.id.photographerHome -> {
                    goFragment(PhotographerHomeFragment())
                    true
                }
                R.id.photographerInbox -> {
                    goFragment(PhotographerInboxFragment())
                    true
                }
                R.id.photographerOrder -> {
                    goFragment(PhotographerOrderFragment())
                    true
                }
                R.id.photographerProfile -> {
                    goFragment(PhotographerProfileFragment.getUSer(user))
                    true
                }
                else -> false
            }
        }
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
        return when (item.itemId){
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