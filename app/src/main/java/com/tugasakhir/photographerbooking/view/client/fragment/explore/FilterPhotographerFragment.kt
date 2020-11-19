package com.tugasakhir.photographerbooking.view.client.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.view.client.adapter.explore.ClientExploreAdapter
import com.tugasakhir.photographerbooking.viewModel.client.ClientHomeViewModel
import kotlinx.android.synthetic.main.fragment_filter_photographer.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FilterPhotographerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FilterPhotographerFragment(val viewModel: ClientHomeViewModel, val adapter: ClientExploreAdapter) : BottomSheetDialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var city = ""

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
        return inflater.inflate(R.layout.fragment_filter_photographer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arrayCity = listOf("Show All", "Jakarta", "Bandung", "Yogyakarta", "Semarang", "Surabaya")

        val spinnerAdapter =
            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, arrayCity) }
        citySpinner.adapter = spinnerAdapter

        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                city = arrayCity[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

        }

        btnClose.setOnClickListener {
            dismiss()
        }

        btnFilter.setOnClickListener {
            val data = viewModel.fetchPhotographerByCity(city)

            adapter.updateLists(data)

            dismiss()
        }
    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment FilterPhotographerFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            FilterPhotographerFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}