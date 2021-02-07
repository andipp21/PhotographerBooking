package com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.utils.FormValidationHelper
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

    var stateName = true
    var stateEmail = true
    var statePhone = true

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

        val items = resources.getStringArray(R.array.provinsi_indonesia)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        selectCity?.setAdapter(adapter)

        val selectedIndex = adapter.getPosition(user.city)

//        selectCity.listSelection = selectedIndex
//        selectCity.setSelection(selectedIndex)


        etFullname.setText(user.fullname)
        etEmail.setText(user.email)
        selectCity.setText(adapter.getItem(selectedIndex), false)
        etPhoneNumber.setText(user.phoneNumber)
        etAboutMePhotographer.setText(user.about)

        selectCity.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, _, _ ->
                buttonState()
            }

        etFullname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    if (FormValidationHelper.isPersonName(s.toString())) {
                        stateName = true
                        etFullnameLayout.error = null
                    } else {
                        stateName = false
                        etFullnameLayout.error = "Nama tidak valid"
                    }
                } else {
                    stateName = false
                    etFullnameLayout.error = "Nama harus diisi"
                }

                buttonState()

            }
        })

        etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    if (FormValidationHelper.isValidEmail(s.toString())) {
                        stateEmail = true
                        etEmailLayout.error = null
                    } else {
                        stateEmail = false
                        etEmailLayout.error = "Alamat email tidak valid"
                    }
                } else {
                    stateEmail = false
                    etEmailLayout.error = "Alamat email harus diisi"
                }

                buttonState()

            }
        })

//        etCity.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                enableButton()
//            }
//        })

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    if (s.toString().first() == '0') {
                        if (FormValidationHelper.rangeLength(s.toString(), 10, 13)) {
                            statePhone = true
                            etPhoneNumberLayout.error = null
                        } else {
                            statePhone = false
                            etPhoneNumberLayout.error =
                                "Panjang karakter nomor telepon minimal 10 dan maksimal 13 angka"
                        }
                    } else {
                        statePhone = false
                        etPhoneNumberLayout.error =
                            "Nomor telepon harus diawali angka 0"
                    }
                } else {
                    statePhone = false
                    etPhoneNumberLayout.error =
                        "Nomor telepon harus diisi"
                }

                buttonState()

            }
        })

        etAboutMePhotographer.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                buttonState()
            }
        })

        btnSaveAboutMe.setOnClickListener {
            GlobalScope.launch {
                val data = User(
                    fullname = etFullname.text.toString(),
                    password = user.password,
                    role = user.role,
                    email = etEmail.text.toString(),
                    city = selectCity.text.toString(),
                    phoneNumber = etPhoneNumber.text.toString(),
                    about = etAboutMePhotographer.text.toString(),
                    profilePicture = user.profilePicture,
                    numberGopay = user.numberGopay,
                    numberOvo = user.numberOvo,
                    numberLinkAja = user.numberLinkAja,
                    numberDana = user.numberDana
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

    fun buttonState() {
        if (stateEmail && stateName && statePhone) {
            enableButton()
        } else {
            disableButton()
        }
    }

    fun enableButton() {
        btnSaveAboutMe.setBackgroundResource(R.drawable.button_enabled)
        btnSaveAboutMe.isClickable = true
    }

    private fun disableButton() {
        btnSaveAboutMe.setBackgroundResource(R.drawable.button_disabled)
        btnSaveAboutMe.isClickable = false
    }

}