package com.tugasakhir.photographerbooking.view.client.activity.photographerDetail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerDetailPackageBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.view.client.activity.order.orderFromPackage.ClientOrderFromPackageActivity
import com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail.`package`.PhotographerDetailPackageBenefitAdapter
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PhotographerDetailPackageActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhotographerDetailPackageBinding

    private lateinit var adapterBenefit: PhotographerDetailPackageBenefitAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerDetailPackageBinding.inflate(layoutInflater)
        adapterBenefit = PhotographerDetailPackageBenefitAdapter()
        setContentView(binding.root)

        val photographer = intent.getParcelableExtra<User>("photographer")

        Log.d("Detail Phorographer", photographer.toString())
        val data = intent.getParcelableExtra<Package>("package")

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)
        supportActionBar?.title = data?.title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.photoshootType.text = data?.type
        binding.photoshootPrice.text = data?.price?.let { convertMoney(it) }

        binding.rvPackageBenefit.adapter = adapterBenefit
        data?.benefit?.let { adapterBenefit.updateLists(it) }

        binding.btnOrderFromPackage.setOnClickListener {
            val intent = Intent(this, ClientOrderFromPackageActivity::class.java)
            intent.putExtra("photographer", photographer)
            intent.putExtra("package", data)
            startActivity(intent)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun convertMoney(input: Long): String {
        val formatter =
            NumberFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat

        var formattedString = formatter.format(input).toString()

        if (formattedString.contains("Rp")) {
            formattedString = formattedString.replace("Rp".toRegex(), "Rp ")
        }

        if (formattedString.contains(",00")) {
            formattedString = formattedString.replace(",00".toRegex(), "")
        }

        return formattedString
    }
}