package com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.Portofolio
import com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail.PhotographerDetailAboutFragment
import com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail.PhotographerDetailPackageFragment
import com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail.PhotographerDetailPortofolioFragment

class PhotographerDetailTabAdapter(
    fm: FragmentManager,
    behavior: Int,
    val user: User,
    val listPortofolio: MutableList<Portofolio> = mutableListOf(),
    val listPackage: MutableList<Package> = mutableListOf()
) :
    FragmentStatePagerAdapter(fm, behavior) {
    private val tabName: Array<String> = arrayOf("About", "Portfolio", "Package")

    override fun getCount(): Int = 3

    override fun getItem(position: Int): Fragment = when (position) {
        0 -> PhotographerDetailAboutFragment(user)
        1 -> PhotographerDetailPortofolioFragment(listPortofolio)
        2 -> PhotographerDetailPackageFragment(listPackage)
        else -> PhotographerDetailAboutFragment(user)
    }

    override fun getPageTitle(position: Int): CharSequence? = tabName[position]

    fun updateListPortofolio(newList: List<Portofolio>) {
        listPortofolio.clear()
        listPortofolio.addAll(newList)
    }

    fun updateListPackage(newList: List<Package>) {
        listPackage.clear()
        listPackage.addAll(newList)
    }
}