package com.tugasakhir.photographerbooking.view.photographer.adapter.profile

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.ProfilePhotographerDataFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.ProfilePhotographerPackageFragment
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.ProfilePhotographerPortofolioFragment
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel

class PhotographerProfileTabAdapter(
    fragmentManager: FragmentManager,
    behavior: Int,
    val viewModel: PhotographerProfileViewModel,
    val user: User
) :
    FragmentStatePagerAdapter(fragmentManager, behavior) {
    private val tabName: Array<String> = arrayOf("About Me", "Portfolio", "Package")

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> ProfilePhotographerDataFragment(viewModel, user)
        1 -> ProfilePhotographerPortofolioFragment(viewModel)
        2 -> ProfilePhotographerPackageFragment(viewModel)
        else -> ProfilePhotographerDataFragment(viewModel, user)
    }

    override fun getCount(): Int = 3
    override fun getPageTitle(position: Int): CharSequence? = tabName[position]
}