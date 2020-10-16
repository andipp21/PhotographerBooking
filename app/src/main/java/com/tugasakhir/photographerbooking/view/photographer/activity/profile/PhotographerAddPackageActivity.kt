package com.tugasakhir.photographerbooking.view.photographer.activity.profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tugasakhir.photographerbooking.databinding.ActivityPhotographerAddPackageBinding

class PhotographerAddPackageActivity : AppCompatActivity() {
    lateinit var binding: ActivityPhotographerAddPackageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhotographerAddPackageBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}