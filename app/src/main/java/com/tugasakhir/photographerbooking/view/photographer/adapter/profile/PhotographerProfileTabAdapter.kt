package com.tugasakhir.photographerbooking.view.photographer.adapter.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.ProfilePhotographerDataFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.ProfilePhotographerPackageFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.ProfilePhotographerPortofolioFragment

class PhotographerProfileTabAdapter(fragmentManager: FragmentManager, behavior: Int) :
    FragmentStatePagerAdapter(fragmentManager, behavior) {
    private val tabName: Array<String> = arrayOf("About Me", "Portofolio", "Package")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> ProfilePhotographerDataFragment()
        1 -> ProfilePhotographerPortofolioFragment()
        2 -> ProfilePhotographerPackageFragment()
        else -> ProfilePhotographerDataFragment()
    }

    override fun getCount(): Int = 3
    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}