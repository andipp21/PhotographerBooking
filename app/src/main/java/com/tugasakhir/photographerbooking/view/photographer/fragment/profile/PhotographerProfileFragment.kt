package com.tugasakhir.photographerbooking.view.photographer.fragment.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.view.MainActivity
import com.tugasakhir.photographerbooking.view.photographer.activity.PhotographerActivity
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.PhotographerProfileTabAdapter
import com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.dialog.PhotographerProfilPictureFragment
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_photographer_profile.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotographerProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotographerProfileFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var user: User? = null

    private val viewModel: PhotographerProfileViewModel
        get() = ViewModelProvider(this).get(PhotographerProfileViewModel::class.java)

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

            user = it.getParcelable("user")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_photographer_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as PhotographerActivity).supportActionBar?.title = "Profile"

        auth = FirebaseAuth.getInstance()

        Glide.with(this)
            .load(user?.profilePicture)
            .circleCrop()
            .into(ivPhotographerPhotoProfil)

        namaPhotographer.text = user?.fullname
        kotaTinggal.text = user?.city

        ivPhotographerPhotoProfil.setOnClickListener {
            PhotographerProfilPictureFragment(viewModel).show(
                childFragmentManager,
                "Upload Profile Picture"
            )
        }

        viewPagerProfile.adapter = user?.let {
            PhotographerProfileTabAdapter(
                childFragmentManager,
                FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,
                viewModel,
                it
            )
        }
        tabLayout.setupWithViewPager(viewPagerProfile)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotographerProfileFragment.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotographerProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }

        fun getUSer(user: User) =
            PhotographerProfileFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("user", user)
                }
            }
    }
}