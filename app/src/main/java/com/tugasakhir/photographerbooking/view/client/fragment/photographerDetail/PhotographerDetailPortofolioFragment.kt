package com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Portofolio
import com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail.dialog.PhotographerDetailShowPortofolioFragment
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.portofolio.PhotographerPortofolioAdapter
import kotlinx.android.synthetic.main.fragment_photographer_detail_portofolio.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerDetailPortofolioFragment(
    private val listPortofolio: MutableList<Portofolio>
) : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var adapter: PhotographerPortofolioAdapter

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
        return inflater.inflate(
            R.layout.fragment_photographer_detail_portofolio,
            container,
            false
        )
    }

    override fun onResume() {
        super.onResume()

        Log.d("List Portofolio", listPortofolio.toString())

        adapter = PhotographerPortofolioAdapter()
        rvPhotographerDetailPortofolio.adapter = adapter

        adapter.updateLists(listPortofolio)

        adapter.setOnItemClickCallback(object :
            PhotographerPortofolioAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Portofolio) {
                PhotographerDetailShowPortofolioFragment(data).show(
                    childFragmentManager,
                    "Show Photo Detail"
                )
            }
        })
    }
}