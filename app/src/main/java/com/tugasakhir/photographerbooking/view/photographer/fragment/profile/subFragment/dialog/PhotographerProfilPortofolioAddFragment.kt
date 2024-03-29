package com.tugasakhir.photographerbooking.view.photographer.fragment.profile.subFragment.dialog

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.viewModel.photographer.PhotographerProfileViewModel
import kotlinx.android.synthetic.main.fragment_photographer_add_portofolio.*
import kotlinx.android.synthetic.main.fragment_photographer_profil_picture.*
import kotlinx.android.synthetic.main.fragment_photographer_profil_picture.btnSavePicture
import kotlinx.android.synthetic.main.fragment_photographer_profil_picture.btnSelectImage
import kotlinx.android.synthetic.main.fragment_photographer_profil_picture.previewImage

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PhotographerProfilPictureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PhotographerProfilPortofolioAddFragment(val viewModel: PhotographerProfileViewModel) : DialogFragment() {

    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_photographer_add_portofolio, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        buttonSaveDisable()

        btnSelectImage.setOnClickListener {
            imagePicker()
        }
    }

    private fun imagePicker() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_PICK
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1)
        startActivityForResult(intent, 212)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 212){
            previewImage.setImageURI(data?.data) // handle chosen image

            buttonSaveEnable()

            btnSavePicture.setOnClickListener {
                Log.d("btn","Btn Save On Click")

                buttonSaveDisable()

                data?.data?.let { it1 -> viewModel.addPortofolio(it1) }
                observerViewModel(viewModel)
            }
//            Glide.with(this)
//                .load(data?.data)
//                .into(previewImage)
        }
    }

    private fun observerViewModel(viewModel: PhotographerProfileViewModel){
        viewModel.responseLiveData.observe(viewLifecycleOwner, Observer{
            Log.d("Add Portofolio",it)

            if (it == "Successfully Add Portofolio"){
                dismiss()
                buttonSaveDisable()
            }
        })
    }


    private fun buttonSaveDisable(){
        btnSavePicture.isEnabled = false
        btnSavePicture.isClickable = false
//        btnSavePicture.setBackgroundResource(R.drawable.button_disabled)
    }

    private fun buttonSaveEnable(){
        btnSavePicture.isEnabled = true
        btnSavePicture.isClickable = true
//        btnSavePicture.setBackgroundResource(R.drawable.button_white)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
//            if(data == null || data.data == null){
//                return
//            }
//            filePath = data.data
//            try {
//                Glide.with(this)
//                    .load(filePath)
//                    .circleCrop()
//                    .into(previewImage)
//
//                Log.d("Imgae Path", "$filePath")
////                usrID?.let { presenter.uploadImage(filePath, it) }
//            } catch (e: IOException) {
//                e.printStackTrace()
//            }
//        }
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment PhotographerProfilPictureFragment.
//         */

//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            PhotographerProfilPictureFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }
}