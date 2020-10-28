package com.tugasakhir.photographerbooking.view.client.activity.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.photographerbooking.databinding.ActivityOrderProcessingBinding

class OrderProcessingActivity : AppCompatActivity() {
    lateinit var binding: ActivityOrderProcessingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderProcessingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnClose.setOnClickListener {
            finish()
        }
    }
}