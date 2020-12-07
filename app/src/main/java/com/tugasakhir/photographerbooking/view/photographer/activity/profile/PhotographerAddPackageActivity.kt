package com.tugasakhir.photographerbooking.view.photographer.activity.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerAddPackageBinding
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.photographerPackage.PhotographerBenefitAdapter
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.bottomSheet.PhotographerProfilPackageBenefitAddFragment
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.activity_photographer_add_package.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PhotographerAddPackageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerAddPackageBinding

    private var benefit: MutableList<String> = mutableListOf()
    private var price: Long = 0
    private var statePrice: Boolean = false
    private var type: String = ""
    private var stateType: Boolean = false
//    private var time: String = ""
//    private var stateTime: Boolean = false
    private var title: String = ""
    private var stateTitle: Boolean = false

    private lateinit var adapter: PhotographerBenefitAdapter

    private val viewModel: PhotographerProfileViewModel
        get() = ViewModelProvider(this).get(PhotographerProfileViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerAddPackageBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)
        supportActionBar?.title = "Add New Package"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonState()

        adapter = PhotographerBenefitAdapter(this)

        rvBenefit.adapter = adapter

        adapter.setOnItemClickCallback(object : PhotographerBenefitAdapter.OnItemClickCallback {
            override fun deleteButtonOnClick(position: Int) {
                benefit.removeAt(position)

                adapter.updateLists(benefit)
            }
        })

        btnAddBenefit.setOnClickListener {
            PhotographerProfilPackageBenefitAddFragment().show(
                supportFragmentManager,
                "Add new benefit"
            )
        }

        binding.packageTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    title = s.toString()
                    stateTitle = true
                } else {
                    title = ""
                    stateTitle = false
                }

                buttonState()
            }
        })

        binding.photoshootPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                binding.photoshootPrice.removeTextChangedListener(this)

                binding.photoshootPrice.setText(convertMoney(s.toString()))

                binding.photoshootPrice.setSelection(binding.photoshootPrice.text.length)
                binding.photoshootPrice.addTextChangedListener(this)

            }
        })

        val photoshootTypArray = resources.getStringArray(R.array.photoshoot_package)

        val photoshootType = binding.photoshootType

        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, photoshootTypArray)
        photoshootType.adapter = spinnerAdapter

        photoshootType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                type = photoshootTypArray[position]
                stateType = true

                buttonState()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

//        binding.rbPhotoshootTime.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.rb_daily -> {
//                    time = "Daily"
//                    stateTime = true
//                    buttonState()
//                }
//                R.id.rb_hourly -> {
//                    time = "Hourly"
//                    stateTime = true
//                    buttonState()
//                }
//            }
//        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    fun addBenefit(data: String) {
        benefit.add(data)

        adapter.updateLists(benefit)
    }

    fun convertMoney(input: String): String {
        var textInput = input

        if (textInput.contains(".")) {
            textInput = textInput.replace("[.]".toRegex(), "")
        }

        if (textInput.contains("Rp ")) {
            textInput = textInput.replace("Rp ".toRegex(), "")
        }

        price = if (textInput == "") {
            0
        } else {
            textInput.toLong()
        }

        statePrice = when {
            price <= 0 -> {
                false
            }
            else -> {
                true
            }
        }

        val formatter =
            NumberFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat

        var formattedString = formatter.format(price).toString()

        if (formattedString.contains("Rp")) {
            formattedString = formattedString.replace("Rp".toRegex(), "Rp ")
        }

        if (formattedString.contains(",00")) {
            formattedString = formattedString.replace(",00".toRegex(), "")
        }

        buttonState()

        return formattedString
    }

    fun buttonState() {
//        Log.d("button", "price : $statePrice, time : $stateTime, type : $stateType")

        if (statePrice && stateType && stateTitle) {
            enableButton()
        } else {
            disableButton()
        }
    }

    private fun disableButton() {
        binding.btnAddPackage.setBackgroundResource(R.drawable.button_disabled)
    }

    private fun enableButton() {
        binding.btnAddPackage.setBackgroundResource(R.drawable.button_enabled)

        binding.btnAddPackage.setOnClickListener {
            GlobalScope.launch {
                val data = FirebaseAuth.getInstance().uid?.let { it1 ->
                    Package(
                        title = title,
                        type = type,
                        price = price,
                        benefit = benefit,
                        userID = it1
                    )
                }

                if (data != null) {
                    viewModel.addPackage(data)
                }
            }

            observerViewModel(viewModel)
        }
    }

    private fun observerViewModel(viewModel: PhotographerProfileViewModel) {
        viewModel.responseLiveData.observe(this, androidx.lifecycle.Observer{
            Log.d("Add Package", it)
            runOnUiThread {
                if (it == "Successfully Add Package") {
                    finish()
                }
            }
        })
    }
}
