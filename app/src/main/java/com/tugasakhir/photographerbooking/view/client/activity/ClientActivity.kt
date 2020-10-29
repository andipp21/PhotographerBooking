package com.tugasakhir.photographerbooking.view.client.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityClientBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.fragment.ClientInboxFragment
import com.tugasakhir.photographerbooking.view.client.fragment.profile.ClientProfileFragment
import com.tugasakhir.photographerbooking.view.client.fragment.ClientScheduleFragment
import com.tugasakhir.photographerbooking.view.client.fragment.explore.ClientExploreFragment

class ClientActivity : AppCompatActivity() {
    lateinit var binding: ActivityClientBinding
    private lateinit var user: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)

        intent.getParcelableExtra<User>("user")?.let {
            user = it
        }

        //default Fragment
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flClientMain, ClientExploreFragment())
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        binding.botNavClient.setOnNavigationItemSelectedListener {
            return@setOnNavigationItemSelectedListener when (it.itemId) {
                R.id.clientExplore -> {
                    goFragment(ClientExploreFragment())
                    true
                }
                R.id.clientSchedule -> {
                    goFragment(ClientScheduleFragment())
                    true
                }
                R.id.clientInbox -> {
                    goFragment(ClientInboxFragment())
                    true
                }
                R.id.clientProfile -> {
                    goFragment(ClientProfileFragment(user))
                    true
                }
                else -> false
            }
        }
    }

    private fun goFragment(fm: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.flClientMain, fm)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()
    }
}