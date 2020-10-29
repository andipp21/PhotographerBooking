package com.tugasakhir.photographerbooking.view.photographer.activity.order

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerOrderDetailBinding

class PhotographerOrderDetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityPhotographerOrderDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerOrderDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}