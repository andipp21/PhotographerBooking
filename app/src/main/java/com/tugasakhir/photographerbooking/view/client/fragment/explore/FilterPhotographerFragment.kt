package com.tugasakhir.photographerbooking.view.client.fragment.explore

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.view.client.adapter.explore.ClientExploreAdapter
import com.tugasakhir.photographerbooking.viewModel.client.ClientHomeViewModel
import kotlinx.android.synthetic.main.fragment_filter_photographer.*


class FilterPhotographerFragment(val viewModel: ClientHomeViewModel, val adapter: ClientExploreAdapter) : BottomSheetDialogFragment() {


    var city = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_filter_photographer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val arrayCity = listOf("Show All", "Jakarta", "Bandung", "Yogyakarta", "Semarang", "Surabaya")
        val items = resources.getStringArray(R.array.array_filter)
        val adapterSpinner =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, items)
        citySpinner?.setAdapter(adapterSpinner)

        citySpinner.onItemClickListener =
            AdapterView.OnItemClickListener { parent, arg1, position, id ->
                city = citySpinner.text.toString()
            }
//        val spinnerAdapter =
//            context?.let { ArrayAdapter(it, android.R.layout.simple_spinner_dropdown_item, arrayCity) }
//        citySpinner.adapter = spinnerAdapter
//
//        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//                city = arrayCity[position]
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//        }

        btnClose.setOnClickListener {
            dismiss()
        }

        btnFilter.setOnClickListener {
            val data = viewModel.fetchPhotographerByCity(city)

            adapter.updateLists(data)

            dismiss()
        }
    }
}