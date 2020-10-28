package com.tugasakhir.photographerbooking.view.client.activity.order.orderPhotographer

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityOrderPhotographerBinding
import com.tugasakhir.photographerbooking.model.pojo.Package
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.fragment.orderReview.ClientOrderReviewFragment
import java.util.*
import kotlin.collections.ArrayList

class OrderPhotographerActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderPhotographerBinding
    private var listPackageTitle: ArrayList<String> = arrayListOf()
    lateinit var packageSelected: Package

    private var selectedDate = Calendar.getInstance().time
    private var yearSelected: Int = 0
    private var monthSelected: Int = 0
    private var daySelected: Int = 0
    private var hourSelected: Int = 0
    private var minuteSelected: Int = 0

    private var stateType = false
    private var statePackage = false
    private var stateDate = false
    private var stateTime = false

    lateinit var photographer: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderPhotographerBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)
        supportActionBar?.title = "Order Photoshoot"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnOrder.isClickable = false

        val paket = intent.getParcelableArrayListExtra<Package>("listPackage")
        photographer = intent.getParcelableExtra("photographer")!!

        binding.photographerName.text = photographer.fullname
        binding.photographerLocation.text = photographer.city

        val photoshootTypArray =
            resources.getStringArray(R.array.photoshoot_package)

        val spinnerPackageAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listPackageTitle)

        val spinnerTypeAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, photoshootTypArray)

        val typeSpinner = binding.photoshootType

        typeSpinner.adapter = spinnerTypeAdapter

        typeSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (paket != null) {
                    for (data in paket) {
                        listPackageTitle.clear()
                        listPackageTitle.add("Select Package")
                        if (data.type == photoshootTypArray[position]) {
                            listPackageTitle.add(data.title)
                        }
                    }
                }

                stateType = true

                binding.packageTitle.visibility = View.VISIBLE
                binding.photoshootPackage.visibility = View.VISIBLE

                val packageSpinner = binding.photoshootPackage
                packageSpinner.adapter = spinnerPackageAdapter

                packageSpinner.onItemSelectedListener =
                    object : AdapterView.OnItemSelectedListener {
                        override fun onItemSelected(
                            parent: AdapterView<*>?,
                            view: View?,
                            position: Int,
                            id: Long
                        ) {
                            if (paket != null) {
                                for (data in paket) {
                                    if (data.title == listPackageTitle[position]) {
                                        packageSelected = data
                                    }
                                }
                            }

                            binding.datePicker.visibility = View.VISIBLE
                            statePackage = true

                            stateButton()
                        }

                        override fun onNothingSelected(parent: AdapterView<*>?) {

                        }

                    }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        binding.datePicker.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val year: Int = cal.get(Calendar.YEAR)
            val month: Int = cal.get(Calendar.MONTH)
            val day: Int = cal.get(Calendar.DAY_OF_MONTH)

            val dialog = DatePickerDialog(
                this,
                { _, sYear, monthOfYear, dayOfMonth ->
                    dateListener(
                        sYear,
                        monthOfYear,
                        dayOfMonth
                    )
                },
                year,
                month,
                day
            )

            dialog.datePicker.minDate = cal.timeInMillis
            dialog.show()
        }

        binding.timePicker.setOnClickListener {
            val cal: Calendar = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)


            val dialog = TimePickerDialog(
                this, { _, sHour, sMinute ->
                    timeListener(sHour, sMinute)
                }, hour, minute, true
            )

            dialog.show()
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun dateListener(year: Int, month: Int, day: Int) {
        yearSelected = year
        monthSelected = month
        daySelected = day
        stateDate = true

        val bulan: String = if (month + 1 < 10) {
            "0${month + 1}"
        } else {
            "${month + 1}"
        }

        val hari: String = if (day < 10) {
            "0$day"
        } else {
            "$day"
        }

        binding.selectedDate.text = "$day / $bulan / $hari"

        binding.selectedDate.visibility = View.VISIBLE
        binding.timePicker.visibility = View.VISIBLE

        stateButton()
    }

    private fun timeListener(hour: Int, minute: Int) {
        hourSelected = hour
        minuteSelected = minute

        stateTime = true

        val jam: String = if (hour < 10) {
            "0$hour"
        } else {
            "$hour"
        }

        val menit: String = if (minute < 10) {
            "0$minute"
        } else {
            "$minute"
        }

        binding.selectedTime.visibility = View.VISIBLE
        binding.selectedTime.text = "$jam : $menit"

        stateButton()
    }

    private fun stateButton() {
        if (stateDate && stateTime) {
            binding.btnOrder.isClickable = true
            binding.btnOrder.setBackgroundResource(R.drawable.button_enabled)

            binding.btnOrder.setOnClickListener {
                val cal = Calendar.getInstance()
                cal.set(Calendar.YEAR, yearSelected)
                cal.set(Calendar.MONTH, monthSelected)
                cal.set(Calendar.DAY_OF_MONTH, daySelected)
                cal.set(Calendar.HOUR_OF_DAY, hourSelected)
                cal.set(Calendar.MINUTE, minuteSelected)

                selectedDate = cal.time

                ClientOrderReviewFragment(packageSelected,photographer,selectedDate).show(supportFragmentManager, "Order Review")
            }
        }
    }
}