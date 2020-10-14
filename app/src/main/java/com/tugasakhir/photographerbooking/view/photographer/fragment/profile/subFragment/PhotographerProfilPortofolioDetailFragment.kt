package com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_photographer_profil_portofolio_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerProfilPortofolioDetailFragment(
    val viewModel: PhotographerProfileViewModel,
    private val portofolio: Portofolio
) : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_photographer_profil_portofolio_detail,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this)
            .load(portofolio.portofolioImage)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .onlyRetrieveFromCache(true)
            .into(previewImage)

        btnClose.setOnClickListener {
            dismiss()
        }

        btnUpdateImage.setOnClickListener {
            imagePicker()
        }

        btnDeleteImage.setOnClickListener {
            portofolio.uid?.let { it1 -> viewModel.deletePortofolio(it1) }
            observerViewModel(viewModel)
        }
    }

    private fun imagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
        startActivityForResult(intent, 221)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 221) {
            previewImage.setImageURI(data?.data) // handle chosen image

            data?.data?.let { it1 -> portofolio.uid?.let { it2 -> viewModel.updatePortofolio(it2,it1) } }
            observerViewModel(viewModel)
        }
    }

    private fun observerViewModel(viewModel: PhotographerProfileViewModel) {
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            Log.d("Portofolio Update", it)

            if (it == "Successfully Update Portofolio" || it == "Successfully Delete Portofolio") {
                dismiss()
            }
        })
    }
}