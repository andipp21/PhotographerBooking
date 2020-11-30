package com.tugasakhir.photographerbooking.view.client.fragment.review

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Review
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.fragment_review_form.*

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReviewFormFragment(val viewModel: OrderViewModel, private val idOrder: String) :
    BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var score: Int = 0
    var review: String = ""

    var stateScore = false

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
        return inflater.inflate(R.layout.fragment_review_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonState()

        etScore.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val nilai = if (s.toString() == ""){
                    0
                } else {
                    s.toString().toInt()
                }

                when {
                    nilai < 1 -> {
                        stateScore = false
                        warningContent.visibility = View.VISIBLE
                        warningContent.text = "The lowest score is 1"
                    }
                    nilai > 5 -> {
                        stateScore = false
                        warningContent.visibility = View.VISIBLE
                        warningContent.text = "The highest score is 5"
                    }
                    else -> {
                        stateScore = true
                        warningContent.visibility = View.GONE
                        score = nilai
                    }
                }

                buttonState()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })

        etReviewText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                review = s.toString()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        btnClose.setOnClickListener {
            dismiss()
        }
    }

    fun buttonState() {
        if (stateScore) {
            btnReview.isClickable = true
            btnReview.setBackgroundResource(R.drawable.button_enabled)
            btnReview.setOnClickListener {
                val data = Review(
                    review = review,
                    score = score
                )


                viewModel.createReview(idOrder, data)
                observerViewModel(viewModel, viewLifecycleOwner)
            }
        } else {
            btnReview.isClickable = false
            btnReview.setBackgroundResource(R.drawable.button_disabled)
        }
    }

    private fun observerViewModel(viewModel: OrderViewModel, lifecycleOwner: LifecycleOwner) {
        viewModel.responseLiveData.observe(lifecycleOwner, Observer{
            if (it == "Successfully Make Review") {
                dismiss()
                activity?.finish()
            }
        })
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment ReviewFormFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            ReviewFormFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}