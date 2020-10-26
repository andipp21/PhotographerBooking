package com.tugasakhir.photographerbooking.view.client.fragment.photographerDetail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.portofolio.PhotographerPortofolioAdapter
import kotlinx.android.synthetic.main.fragment_photographer_detail_portofolio.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotographerDetailPortofolioFragment(private val listPortofolio: MutableList<Portofolio>) : Fragment() {
    // TODO: Rename and change types of parameters
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photographer_detail_portofolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


//
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
//         * @return A new instance of fragment PhotographerDetailPortofolioFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic fun newInstance(param1: String, param2: String) =
//                PhotographerDetailPortofolioFragment().apply {
//                    arguments = Bundle().apply {
//                        putString(ARG_PARAM1, param1)
//                        putString(ARG_PARAM2, param2)
//                    }
//                }
//    }
}