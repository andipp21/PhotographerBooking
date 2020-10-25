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
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerEditPackageBinding
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.photographerPackage.PhotographerBenefitAdapter
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.bottomSheet.PhotographerProfilEditPackageBenefitAddFragment
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.activity_photographer_edit_package.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PhotographerEditPackageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerEditPackageBinding

    private lateinit var adapter: PhotographerBenefitAdapter

    private var formBenefit: MutableList<String> = mutableListOf()
    private var formPrice: Long = 0
    private var formType: String = ""
    private var formTime: String = ""
    private var formTitle: String = ""

    lateinit var data: Package

    private val viewModel: PhotographerProfileViewModel
        get() = ViewModelProvider(this).get(PhotographerProfileViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerEditPackageBinding.inflate(layoutInflater)
        adapter = PhotographerBenefitAdapter(this)
        setContentView(binding.root)

        data = intent.getParcelableExtra("packageData")!!

        formTitle = data.title
        formTime = data.time
        formType = data.type
        formPrice = data.price
        formBenefit.addAll(data.benefit)

        //title
        binding.packageTitle.setText(formTitle)

        binding.packageTitle.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    formTitle = s.toString()
                    enableButton()
                } else {
                    title = ""
                    disableButton()
                }
            }
        })

        //type
        val photoshootTypArray = resources.getStringArray(R.array.photoshoot_package)

        val photoshootType = binding.photoshootType

        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, photoshootTypArray)
        photoshootType.adapter = spinnerAdapter

        if (formType != "") {
            val spinnerPosition: Int = spinnerAdapter.getPosition(formType)
            photoshootType.setSelection(spinnerPosition)
        }

        photoshootType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                formType = photoshootTypArray[position]
                enableButton()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        //time
        if (formTime == "Daily") {
            rb_daily.isChecked = true
            rb_hourly.isChecked = false
        } else if (formTime == "Hourly") {
            rb_hourly.isChecked = true
            rb_daily.isChecked = false
        }

        binding.rbPhotoshootTime.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rb_daily -> {
                    formTime = "Daily"
                    enableButton()
                }
                R.id.rb_hourly -> {
                    formTime = "Hourly"
                    enableButton()
                }
            }
        }

        //price
        binding.photoshootPrice.setText(convertMoney(formPrice.toString()))

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

        //benefit
        binding.rvBenefit.adapter = adapter
        adapter.updateLists(formBenefit)

        adapter.setOnItemClickCallback(object : PhotographerBenefitAdapter.OnItemClickCallback {
            override fun deleteButtonOnClick(position: Int) {
                formBenefit.removeAt(position)
                adapter.updateLists(formBenefit)
            }
        })

        btnAddBenefit.setOnClickListener {
            PhotographerProfilEditPackageBenefitAddFragment().show(
                supportFragmentManager,
                "Add Benefit"
            )
        }


    }

    fun addBenefit(data: String) {
        formBenefit.add(data)

        adapter.updateLists(formBenefit)
    }

    private fun convertMoney(input: String): String {
        var textInput = input

        if (textInput.contains(".")) {
            textInput = textInput.replace("[.]".toRegex(), "")
        }

        if (textInput.contains("Rp ")) {
            textInput = textInput.replace("Rp ".toRegex(), "")
        }

        formPrice = if (textInput == "") {
            0
        } else {
            textInput.toLong()
        }

        when {
            formPrice <= 0 -> {
                disableButton()
            }
            else -> {
                enableButton()
            }
        }

        val formatter =
            NumberFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat

        var formattedString = formatter.format(formPrice).toString()

        if (formattedString.contains("Rp")) {
            formattedString = formattedString.replace("Rp".toRegex(), "Rp ")
        }

        if (formattedString.contains(",00")) {
            formattedString = formattedString.replace(",00".toRegex(), "")
        }

        return formattedString
    }

    private fun disableButton() {
        binding.btnAddPackage.setBackgroundResource(R.drawable.button_disabled)
        binding.btnAddPackage.isClickable = false
    }

    private fun enableButton() {
        binding.btnAddPackage.setBackgroundResource(R.drawable.button_enabled)
        binding.btnAddPackage.isClickable = true

        binding.btnAddPackage.setOnClickListener {
            GlobalScope.launch {
                data.apply {
                    title = formTitle
                    time = formTime
                    type = formType
                    benefit = formBenefit
                    price = formPrice
                }

                viewModel.updatePackage(data)
            }

            observerViewModel(viewModel)
        }
    }

    private fun observerViewModel(viewModel: PhotographerProfileViewModel) {
        viewModel.responseLiveData.observe(this, {
            Log.d("Update Package", it)
            runOnUiThread {
                if (it == "Successfully Update Package") {
                    finish()
                }
            }
        })
    }
}
