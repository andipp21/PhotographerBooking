package com.tugasakhir.photographerbooking.view.client.activity.order.payOrder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityPayOrderBinding
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.fragment.order.howToPay.PayDanaFragment
import com.tugasakhir.photographerbooking.view.client.fragment.order.howToPay.PayGopayFragment
import com.tugasakhir.photographerbooking.view.client.fragment.order.howToPay.PayLinkAjaFragment
import com.tugasakhir.photographerbooking.view.client.fragment.order.howToPay.PayOvoFragment
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.activity_pay_order.*

class PayOrderActivity : AppCompatActivity() {
    lateinit var binding: ActivityPayOrderBinding

    lateinit var photographer: User
    private lateinit var idOrder: String
    private lateinit var paymentAmount: String

    lateinit var selectedPayment: String

    private var viewModel: OrderViewModel? = null

    val listPaymentMethod: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPayOrderBinding.inflate(layoutInflater)
        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)
        setContentView(binding.root)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)
        supportActionBar?.title = "Pay Order"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        photographer = intent.getParcelableExtra("photographer")!!
        idOrder = intent.getStringExtra("idOrder")!!
        paymentAmount = intent.getStringExtra("totalAmount")!!

        if (photographer.numberDana != "") {
            listPaymentMethod.add("Dana")
        }
        if (photographer.numberOvo != "") {
            listPaymentMethod.add("Ovo")
        }
        if (photographer.numberLinkAja != "") {
            listPaymentMethod.add("Link Aja")
        }
        if (photographer.numberGopay != "") {
            listPaymentMethod.add("Gopay")
        }

        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listPaymentMethod)
        binding.paymentMethodSpinner.adapter = spinnerAdapter

        binding.paymentMethodSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    selectedPayment = listPaymentMethod[position]

                    when (selectedPayment) {
                        "Dana" -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.frameHowToPay,
                                    PayDanaFragment(
                                        paymentAmount = paymentAmount,
                                        accountNumber = photographer.numberDana
                                    )
                                )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                        "Ovo" -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.frameHowToPay,
                                    PayOvoFragment(
                                        paymentAmount = paymentAmount,
                                        accountNumber = photographer.numberOvo
                                    )
                                )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                        "Link Aja" -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.frameHowToPay,
                                    PayLinkAjaFragment(
                                        paymentAmount = paymentAmount,
                                        accountNumber = photographer.numberLinkAja
                                    )
                                )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                        "Gopay" -> {
                            supportFragmentManager
                                .beginTransaction()
                                .replace(
                                    R.id.frameHowToPay,
                                    PayGopayFragment(
                                        paymentAmount = paymentAmount,
                                        accountNumber = photographer.numberGopay
                                    )
                                )
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                                .commit()
                        }
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

            }

        buttonSaveDisable()
        addPaymentImageBTN.setOnClickListener {
            imagePicker()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    private fun imagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        startActivityForResult(intent, 196)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 196) {
            frameHowToPay.visibility = View.GONE
            imagePreview.visibility = View.VISIBLE
            imagePreview.setImageURI(data?.data) // handle chosen image

            buttonSaveEnable()

            payOrder.setOnClickListener {
                Log.d("btn", "Btn Save On Click")

                data?.data?.let { it1 -> viewModel?.payOrder(it1, idOrder) }
                viewModel?.let { it1 -> observerViewModel(it1) }
            }
        }
    }

    private fun observerViewModel(viewModel: OrderViewModel) {
        viewModel.responseLiveData.observe(this, {
            if (it == "Successfully Pay Order") {
                finish()
            }
        })
    }

    private fun buttonSaveDisable() {
        addPaymentImageBTN.visibility = View.VISIBLE
        payOrder.visibility = View.GONE
    }

    private fun buttonSaveEnable() {
        addPaymentImageBTN.visibility = View.GONE
        payOrder.visibility = View.VISIBLE
    }
}