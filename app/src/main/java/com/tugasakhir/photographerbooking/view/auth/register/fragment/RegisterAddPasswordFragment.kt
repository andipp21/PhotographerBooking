package com.tugasakhir.photographerbooking.view.auth.register.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.view.auth.register.RegisterActivity
import kotlinx.android.synthetic.main.fragment_register_add_password.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterAddPasswordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterAddPasswordFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var stateCheckHurufKecil = false
    private var stateCheckAngka = false
    private var stateCheckHurufKapital = false
    private var stateCheckMinChar = false

    private var statePassword = false
    private var stateConfirmPassword = false

    private lateinit var password: String

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
        return inflater.inflate(R.layout.fragment_register_add_password, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                etPassword.removeTextChangedListener(this)

                checkPass(p0.toString())

                etPassword.text?.length?.let { etPassword.setSelection(it) }
                etPassword.addTextChangedListener(this)
            }
        })

        etConfirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                checkConfirmPassword(p0.toString())
//                etConfirmPassword.removeTextChangedListener(this)
//
//                checkConfirmPassword(p0.toString())
//
//                etConfirmPassword.text?.length?.let { etConfirmPassword.setSelection(it) }
//                etConfirmPassword.addTextChangedListener(this)
            }

        })

        btnContinue.setOnClickListener {
            (activity as RegisterActivity).password = password

            (activity as RegisterActivity).register()
        }
    }

    fun checkPass(et: String) {
        password = et

        stateCheckAngka = if (et.matches((".*\\d.*").toRegex())) {
            tvAngka.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password_oke, 0, 0, 0)
            tvAngka.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorPrimary
                )
            )
            true
        } else {
            tvAngka.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password_no, 0, 0, 0)
            tvAngka.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorRed
                )
            )
            false
        }

        stateCheckHurufKecil = if (et.matches((".*[a-z].*").toRegex())) {
            tvHurufKecil.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_oke,
                0,
                0,
                0
            )
            tvHurufKecil.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorPrimary
                )
            )

            true
        } else {
            tvHurufKecil.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_password_no, 0, 0, 0)
            tvHurufKecil.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorRed
                )
            )

            false
        }

        stateCheckHurufKapital = if (et.matches((".*[A-Z].*").toRegex())) {
            tvHurufKapital.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_oke,
                0,
                0,
                0
            )
            tvHurufKapital.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorPrimary
                )
            )

            true
        } else {
            tvHurufKapital.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_no,
                0,
                0,
                0
            )
            tvHurufKapital.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorRed
                )
            )

            false
        }

        stateCheckMinChar = if (et.length >= 8) {
            tvMinKarakter.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_oke,
                0,
                0,
                0
            )
            tvMinKarakter.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorPrimary
                )
            )

            true
        } else {
            tvMinKarakter.setCompoundDrawablesWithIntrinsicBounds(
                R.drawable.ic_password_no,
                0,
                0,
                0
            )
            tvMinKarakter.setTextColor(
                ContextCompat.getColor(
                    (activity as RegisterActivity),
                    R.color.colorRed
                )
            )

            false
        }

        checkState()
    }

    private fun checkState() {
        if (stateCheckAngka && stateCheckHurufKapital && stateCheckHurufKecil && stateCheckMinChar) {
            passwordChecker.visibility = View.GONE
            statePassword = true
        } else {
            statePassword = false
            passwordChecker.visibility = View.VISIBLE
        }

        checkStateButton()
    }

    fun checkConfirmPassword(et: String) {
        if (et == password){
            etConfirmPasswordLayout.error = null
            stateConfirmPassword = true
        } else {
            etConfirmPasswordLayout.error = getString(R.string.password_tidak_sama)
            stateConfirmPassword = false
        }

        checkStateButton()
    }

    private fun checkStateButton() {
        if (statePassword && stateConfirmPassword) {
            btnContinue.isClickable = true
            btnContinue.isEnabled = true
//            btnContinue.setBackgroundResource(R.drawable.button_enabled)
        } else {
            btnContinue.isClickable = false
            btnContinue.isEnabled = false
//            btnContinue.setBackgroundResource(R.drawable.button_disabled)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RegisterAddPasswordFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterAddPasswordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}