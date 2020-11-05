package com.tugasakhir.photographerbooking.view.client.activity.order.orderConfirmed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityOrderConfirmedBinding
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.view.client.activity.order.payOrder.PayOrderActivity
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class OrderConfirmedActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderConfirmedBinding
    lateinit var order: Order
    lateinit var photographer: User
    lateinit var totalAmount: String

    var viewModel: OrderViewModel? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderConfirmedBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(this).get(OrderViewModel::class.java)

        order = intent.getParcelableExtra("order")!!
        photographer = intent.getParcelableExtra("photographer")!!
        setContentView(binding.root)

        val appBar = binding.appBarLayout.toolbar
        setSupportActionBar(appBar)
        supportActionBar?.title = "Order Detail"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        Glide.with(this)
            .load(photographer.profilePicture)
            .circleCrop()
            .into(binding.photographerProfilePicture)

        binding.photographerName.text = photographer.fullname

        if (order.isConfirmed && order.isPayed && order.isDone) {
            binding.orderStatus.setText(R.string.status_order_3)
            binding.orderStatus.setBackgroundResource(R.drawable.button_enabled)
            binding.orderStatus.setTextColor(getColor(R.color.colorWhite))
            binding.paymentBTN.visibility = View.GONE
        } else if (order.isConfirmed && order.isPayed && !order.isDone){
            binding.orderStatus.setText(R.string.status_order_4)
            binding.orderStatus.setTextColor(getColor(R.color.colorPrimary))
            binding.paymentBTN.visibility = View.GONE
        } else if (order.isConfirmed && !order.isPayed && !order.isDone) {
            binding.orderStatus.setText(R.string.status_order_2)
            binding.orderStatus.setTextColor(getColor(R.color.colorPrimary))
            binding.paymentBTN.setOnClickListener {
                val intent = Intent(this, PayOrderActivity::class.java)
                intent.putExtra("photographer", photographer)
                intent.putExtra("idOrder", order.uid)
                intent.putExtra("totalAmount", totalAmount)
                startActivity(intent)
//                order.uid?.let { it1 -> viewModel!!.confirmationOrderDone(it1) }
//                observeViewModel(viewModel!!, this)
            }
        }

        if (viewModel != null) {
            viewModel!!.getPackage(order.packageID)
            observeViewModel(viewModel!!, this)
        }

        val cal = Calendar.getInstance()
        cal.time = order.photoshootTime

        val year = cal.get(Calendar.YEAR)
        val month = cal.get(Calendar.MONTH)
        val day = cal.get(Calendar.DAY_OF_MONTH)
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val bulan = bulanString(month)

        val hari: String = if (day < 10) {
            "0$day"
        } else {
            "$day"
        }

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

        binding.dateContent.text = "$hari $bulan $year"
        binding.timeContent.text = "$jam : $menit"

    }

    private fun bulanString(bulan: Int): String {
        return when (bulan + 1) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()

        viewModel = null
    }

    private fun observeViewModel(actionDelegate: OrderViewModel, lifecycleOwner: LifecycleOwner) {
        actionDelegate.responsePackage.observe(lifecycleOwner, {
            binding.typeContent.text = it.type
            binding.packageContent.text = it.title
            totalAmount = convertMoney(it.price)
            binding.totalFee.text = totalAmount
        })
//
//        actionDelegate.responseLiveData.observe(lifecycleOwner, {
//            if (it == "Order Confirmated" || it == "Order Done"){
//                finish()
//            }
//        })
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