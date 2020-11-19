package com.tugasakhir.photographerbooking.view.client.activity.photographerDetail

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerDetailBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.view.client.activity.order.orderPhotographer.OrderPhotographerActivity
import com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail.PhotographerDetailTabAdapter
import com.tugasakhir.photographerbooking.viewModel.client.ClientHomeViewModel
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.activity_photographer_detail.view.*

class PhotographerDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerDetailBinding

    lateinit var photographer: User

    private lateinit var tabAdapter: PhotographerDetailTabAdapter
    private var listPackage: MutableList<Package> = mutableListOf()

    private var viewModel: ClientHomeViewModel? = null
    private var orderViewModel: OrderViewModel? = null

    override fun onDestroy() {
        super.onDestroy()

        viewModel = null
        orderViewModel = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerDetailBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(ClientHomeViewModel::class.java)
        orderViewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        setContentView(binding.root)

        photographer = intent.getParcelableExtra("photographer")!!

        Glide.with(this)
            .load(photographer.profilePicture)
            .into(binding.ivPhotographerPhotoProfil)

        binding.namaPhotographer.text = photographer.fullname
        binding.kotaTinggal.text = photographer.city

        if (photographer.uid != null){
            orderViewModel?.getOrderAmount(photographer.uid!!)
            orderViewModel?.let { observeOrderAmount(it, this) }
        }

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

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnOrder.setOnClickListener {
            val intent = Intent(this, OrderPhotographerActivity::class.java)
            intent.putExtra("photographer", photographer)
            intent.putExtra("listPackage", ArrayList<Package>(listPackage))

            startActivity(intent)
        }
    }

    private fun getData(photographerID: String) {
        viewModel?.getPhotographerPortofolio(photographerID)
        viewModel?.let { observePortofolio(it, this) }
        viewModel?.getPhotographerPackage(photographerID)
        viewModel?.let { observePackage(it, this) }
    }

    private fun observePortofolio(
        actionDelegate: ClientHomeViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        actionDelegate.responseListPortofolio.observe(lifecycleOwner, {
//            listPortofolio.addAll(it)
            tabAdapter.updateListPortofolio(it)

            Log.d("Portofolio Activity", it.toString())
        })
    }

    private fun observePackage(
        actionDelegate: ClientHomeViewModel,
        lifecycleOwner: LifecycleOwner
    ) {
        actionDelegate.responseListPackage.observe(lifecycleOwner, {
            listPackage.clear()
            listPackage.addAll(it)
            tabAdapter.updateListPackage(it)
            Log.d("Package Activity", it.toString())
        })
    }

    private fun observeOrderAmount(
        actionDelegate: OrderViewModel,
        lifecycleOwner: LifecycleOwner
    ){
        actionDelegate.responseAmount.observe(lifecycleOwner, {
            Log.d("amount", it.toString())

            binding.jumlahOrder.text = "Order Amount : $it"
        })
    }

    fun goOrderFromPackage(data: Package) {
        val intent = Intent(this, PhotographerDetailPackageActivity::class.java)
        intent.putExtra("photographer", photographer)
        intent.putExtra("package", data)
        startActivity(intent)
    }
}