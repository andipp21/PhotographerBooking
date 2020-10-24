package com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.view.photographer.activity.profile.PhotographerAddPackageActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.profile.PhotographerEditPackageActivity
import kotlinx.android.synthetic.main.fragment_photographer_profil_package_add.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerProfilEditPackageBenefitAddFragment : BottomSheetDialogFragment() {
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
        return inflater.inflate(R.layout.fragment_photographer_profil_package_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAddBenefit.setOnClickListener {
            (activity as PhotographerEditPackageActivity).addBenefit(etBenefit.text.toString())
            dismiss()
        }

        etBenefit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    enableButton()
                } else {
                    disableButton()
                }
            }
        })

        disableButton()
    }

    fun disableButton() {
        btnAddBenefit.setBackgroundResource(R.drawable.button_disabled)
    }

    fun enableButton() {
        btnAddBenefit.setBackgroundResource(R.drawable.button_enabled)
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PhotographerProfilPackageAddFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotographerProfilPackageBenefitAddFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}