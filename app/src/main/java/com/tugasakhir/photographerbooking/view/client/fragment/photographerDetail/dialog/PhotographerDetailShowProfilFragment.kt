package com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Portofolio
import kotlinx.android.synthetic.main.fragment_photographer_profil_portofolio_detail.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerDetailShowPortofolioFragment(val portofolio: Portofolio) : DialogFragment() {
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photographer_detail_show_portofolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnClose.setOnClickListener {
            dismiss()
        }

        Glide.with(this)
            .load(portofolio.portofolioImage)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .onlyRetrieveFromCache(true)
            .into(previewImage)
    }
}