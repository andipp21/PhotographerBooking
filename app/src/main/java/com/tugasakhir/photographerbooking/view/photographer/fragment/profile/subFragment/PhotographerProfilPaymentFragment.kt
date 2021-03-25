package com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.utils.FormValidationHelper
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_photographer_profil_payment.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerProfilPaymentFragment(
    val viewModel: PhotographerProfileViewModel,
    val user: User
) : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var numberOvo = ""
    var numberGopay = ""
    var numberLinkAja = ""
    var numberDana = ""

    var stateOvo = false
    var stateGopay = false
    var stateLinkAja = false
    var stateDana = false

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
        return inflater.inflate(R.layout.fragment_photographer_profil_payment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkState()

        Glide.with(this)
            .load(R.drawable.logo_ovo)
            .into(ivPaymentMethodOvo)

        Glide.with(this)
            .load(R.drawable.logo_dana)
            .into(ivPaymentMethodDana)

        Glide.with(this)
            .load(R.drawable.logo_gopay)
            .into(ivPaymentMethodGopay)

        Glide.with(this)
            .load(R.drawable.logo_link_aja)
            .into(ivPaymentMethodLinkAja)

        numberLinkAja = user.numberLinkAja
        numberOvo = user.numberOvo
        numberDana = user.numberDana
        numberGopay = user.numberGopay

        numberPaymentMethodOvo.setText(numberOvo)
        numberPaymentMethodLinkAja.setText(numberLinkAja)
        numberPaymentMethodGopay.setText(numberGopay)
        numberPaymentMethodDana.setText(numberDana)

        numberPaymentMethodDana.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    if (FormValidationHelper.isNumeric(s.toString())){
                        numberDana = s.toString()
                        stateDana = true

                        layoutDana.error = null
                    } else {
                        stateDana = false

                        layoutDana.error = "Inputan harus berupa angka"
                    }
                } else {
                    stateDana = false
                }

                checkState()
            }

        })

        numberPaymentMethodGopay.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    if (FormValidationHelper.isNumeric(s.toString())){
                        numberGopay = s.toString()
                        stateGopay = true

                        layoutGopay.error = null
                    } else {
                        stateGopay = false
                        layoutGopay.error = "Inputan harus berupa angka"
                    }

                } else {
                    stateGopay = false
                }

                checkState()
            }

        })

        numberPaymentMethodLinkAja.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    if (FormValidationHelper.isNumeric(s.toString())){
                        numberLinkAja = s.toString()
                        stateLinkAja = true

                        layoutLinkAja.error = null
                    } else {
                        stateLinkAja = false

                        layoutLinkAja.error = "Inputan harus berupa angka"
                    }

                } else {
                    stateLinkAja = false
                }

                checkState()
            }

        })

        numberPaymentMethodOvo.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length!! > 0) {
                    Log.d("Text Ovo", s.toString())

                    if (FormValidationHelper.isNumeric(s.toString())){
                        numberOvo = s.toString()
                        stateOvo = true

                        layoutOvo.error = null
                    } else {
                        stateOvo = false

                        layoutOvo.error = "Inputan harus berupa angka"
                    }
                } else {
                    stateOvo = false
                }

                checkState()
            }

        })
    }

    fun checkState() {
        if (stateDana || stateGopay || stateLinkAja || stateOvo) {
            buttonEnable()
        } else {
            buttonDisable()
        }
    }

    private fun buttonEnable() {
        btnAddPayment.isClickable = true

        btnAddPayment.isEnabled = true

        btnAddPayment.setOnClickListener {
            val dt = User(
                fullname = user.fullname,
                password = user.password,
                role = user.role,
                email = user.email,
                city = user.city,
                phoneNumber = user.phoneNumber,
                about = user.about,
                profilePicture = user.profilePicture,
                numberGopay = numberGopay,
                numberOvo = numberOvo,
                numberLinkAja = numberLinkAja,
                numberDana = numberDana
            )

            viewModel.updateUserData(dt)

            observerViewModel(viewModel)
        }
//        btnAddPayment.setBackgroundResource(R.drawable.button_enabled)
    }

    private fun buttonDisable() {
        btnAddPayment.isClickable = false
        btnAddPayment.isEnabled = false
//        btnAddPayment.setBackgroundResource(R.drawable.button_disabled)
    }

    private fun observerViewModel(viewModel: PhotographerProfileViewModel) {
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            Log.d("Data Update", it)
            stateOvo = false
            stateDana = false
            stateGopay = false
            stateLinkAja = false
            checkState()
        })
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PhotographerProfilPaymentFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotographerProfilPaymentFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}