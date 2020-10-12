package com.tugasakhir.photographerbooking.view.photographer.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerBinding

class PhotographerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotographerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}