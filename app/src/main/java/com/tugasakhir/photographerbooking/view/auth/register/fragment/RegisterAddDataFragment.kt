package com.tugasakhir.photographerbooking.view.auth.register.fragment

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
import com.tugasakhir.photographerbooking.utils.FormValidationHelper
import com.tugasakhir.photographerbooking.view.auth.register.RegisterActivity
import kotlinx.android.synthetic.main.fragment_register_add_data.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterAddDataFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterAddDataFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var stateCity = false
    private var stateName = false
    private var statePhoneNumber = false
    private var stateEmail = false

    private lateinit var city: String


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
        return inflater.inflate(R.layout.fragment_register_add_data, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val items = resources.getStringArray(R.array.provinsi_indonesia)
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        provinsi?.setAdapter(adapter)

        provinsi.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                stateCity = true
                buttonState()
            }

//        provinsi.setOnItemClickListener { parent, _, position, _ ->
//            val selection = parent.getItemAtPosition(position) as String
//            city = selection
//            stateCity = true
//            buttonState()
//        }

//        val provinsiArray = resources.getStringArray(R.array.provinsi_indonesia)
//
////        val spinner = provinsi
//
//        val spinnerAdapter = activity?.let {
//            ArrayAdapter(
//                it,
//                android.R.layout.simple_spinner_dropdown_item,
//                provinsiArray
//            )
//        }
//        provinsi.adapter = spinnerAdapter

//        provinsi.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                city = provinsiArray[position]
//                stateCity = true
//                buttonState()
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//        }

        etPhoneNumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (s?.length != 0) {
                    if (s.toString().first() == '0') {
                        if (FormValidationHelper.rangeLength(s.toString(), 10, 13)) {
                            statePhoneNumber = true
                            etPhoneNumberLayout.error = null
                        } else {
                            statePhoneNumber = false
                            etPhoneNumberLayout.error =
                                "Panjang karakter nomor telepon minimal 10 dan maksimal 13 angka"
                        }
                    } else {
                        statePhoneNumber = false
                        etPhoneNumberLayout.error =
                            "Nomor telepon harus diawali angka 0"
                    }
                } else {
                    statePhoneNumber = false
                    etPhoneNumberLayout.error =
                        "Nomor telepon harus diisi"
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
                        etFullnameLayout.error = "Nama tidak valid"
                    }
                } else {
                    stateName = false
                    etFullnameLayout.error = "Nama harus diisi"
                }

                buttonState()
            }
        })

        buttonState()
    }

    fun buttonState() {

        if (stateCity && stateEmail && stateName && statePhoneNumber) {
            Log.d("button state", "true")
            btnContinue.isEnabled = true
            btnContinue.isClickable = true

            btnContinue.setOnClickListener {
                (activity as RegisterActivity).city = provinsi.text.toString()
                (activity as RegisterActivity).phoneNumber = etPhoneNumber.text.toString()
                (activity as RegisterActivity).fullname = etFullname.text.toString()
                (activity as RegisterActivity).email = etEmail.text.toString()

                (activity as RegisterActivity).saveData()
            }
        } else {
            Log.d("button state", "false")
            btnContinue.isClickable = false
            btnContinue.isEnabled = false
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterAddDataFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterAddDataFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}