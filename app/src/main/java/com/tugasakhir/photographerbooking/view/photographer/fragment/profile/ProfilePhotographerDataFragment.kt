package com.tugasakhir.photographerbooking.view.photographer.fragment.profile

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_profile_photographer_data.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProfilePhotographerDataFragment(val viewModel: PhotographerProfileViewModel, val user: User) :
    Fragment() {
    private var param1: String? = null
    private var param2: String? = null

//    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

//            user = it.getParcelable("user")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile_photographer_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disableButton()

        etFullname.setText(user.fullname)
        etEmail.setText(user.email)
        etCity.setText(user.city)
        etPhoneNumber.setText(user.phoneNumber)
        etAboutMePhotographer.setText(user.about)

        etFullname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                enableButton()
            }
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                enableButton()
            }
        })

        etCity.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                enableButton()
            }
        })

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                enableButton()
            }
        })

        etAboutMePhotographer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                enableButton()
            }
        })

        btnSaveAboutMe.setOnClickListener {
            GlobalScope.launch {
                val data = User(
                    fullname = etFullname.text.toString(),
                    password = user.password,
                    role = user.role,
                    email = etEmail.text.toString(),
                    city = etCity.text.toString(),
                    phoneNumber = etPhoneNumber.text.toString(),
                    about = etAboutMePhotographer.text.toString(),
                    profilePicture = user.profilePicture
                )

                viewModel.updateUserData(data)

            }

            observerViewModel(viewModel)


            Log.d("Btn On CLick", "Save About Me")
        }
    }

    private fun observerViewModel(viewModel: PhotographerProfileViewModel) {
        viewModel.responseLiveData.observe(viewLifecycleOwner, {
            Log.d("Data Update", it)
            activity?.runOnUiThread {
                disableButton()
            }
        })
    }

    fun enableButton() {
        btnSaveAboutMe.setBackgroundResource(R.drawable.button_enabled)
        btnSaveAboutMe.isClickable = true
    }

    private fun disableButton() {
        btnSaveAboutMe.setBackgroundResource(R.drawable.button_disabled)
        btnSaveAboutMe.isClickable = false
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ProfilePhotographerDataFragment.
//         */
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ProfilePhotographerDataFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
////
////        fun getUSer(user: User) =
////            PhotographerProfileFragment().apply {
////                arguments = Bundle().apply {
////                    putParcelable("user", user)
////                }
////            }
//    }
}