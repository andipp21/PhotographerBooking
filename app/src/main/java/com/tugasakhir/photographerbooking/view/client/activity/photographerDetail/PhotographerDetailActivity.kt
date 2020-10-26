package com.tugasakhir.photographerbooking.view.client.activity.photographerDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerDetailBinding
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail.PhotographerDetailTabAdapter
import com.tugasakhir.photographerbooking.viewModel.client.ClientHomeViewModel
import kotlinx.android.synthetic.main.activity_photographer_detail.view.*

class PhotographerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerDetailBinding

    lateinit var photographer: User

    private var listPortofolio: MutableList<Portofolio> = mutableListOf()
    private var listPackage: MutableList<Package> = mutableListOf()

    lateinit var tabAdapter: PhotographerDetailTabAdapter

    private val viewModel: ClientHomeViewModel
        get() = ViewModelProvider(this).get(ClientHomeViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        photographer = intent.getParcelableExtra("photographer")!!

        Glide.with(this)
            .load(photographer.profilePicture)
            .into(binding.ivPhotographerPhotoProfil)

        binding.namaPhotographer.text = photographer.fullname
        binding.kotaTinggal.text = photographer.city

        tabAdapter = PhotographerDetailTabAdapter(
            supportFragmentManager,
            FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
            photographer,
        )

        binding.viewPagerPhotographerDetail.adapter = tabAdapter
        binding.viewPagerPhotographerDetail.tabLayout.setupWithViewPager(binding.viewPagerPhotographerDetail)

        photographer.uid?.let {
            getData(it)
        }
    }

    private fun getData(photographerID: String) {
        viewModel.getPhotographerPortofolio(photographerID)
        observePortofolio(viewModel, this)
        viewModel.getPhotographerPackage(photographerID)
        observePackage(viewModel, this)


    }

    private fun observePortofolio(actionDelegate: ClientHomeViewModel, lifecycleOwner: LifecycleOwner) {
        actionDelegate.responseListPortofolio.observe(lifecycleOwner, {
//            listPortofolio.addAll(it)
            tabAdapter.updateListPortofolio(it)

            Log.d("Portofolio Activity", it.toString())
        })
    }

    private fun observePackage(actionDelegate: ClientHomeViewModel, lifecycleOwner: LifecycleOwner) {
        actionDelegate.responseListPackage.observe(lifecycleOwner, {
//            listPackage.addAll(it)
            tabAdapter.updateListPackage(it)
            Log.d("Package Activity", it.toString())
        })
    }
}