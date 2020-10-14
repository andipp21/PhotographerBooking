package com.tugasakhir.photographerbooking.viewModel.photographer

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.model.services.photographer.profile.PhotographerProfile
import toothpick.Toothpick
import javax.inject.Inject

class PhotographerProfileViewModel : ViewModel(){
    @Inject
    lateinit var photographerProfile: PhotographerProfile

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    private val _responseListPortofolio = MutableLiveData<List<Portofolio>>()
    val responseListPortofolio = _responseListPortofolio

    private val _responseLiveUser = MutableLiveData<User>()
    val responseLiveUser = _responseLiveUser

    private val _responseLiveUri = MutableLiveData<Uri>()
    val responseLiveUri = _responseLiveUri

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun updateUserData(user: User){
        photographerProfile.updateUserData(user){
            _responseLiveData.postValue(it)

            Log.d("Update user",it)
        }
    }

    fun storeImage(path: Uri){
        photographerProfile.changeProfilePicture(path){
            _responseLiveData.postValue(it)

            Log.d("View Model Image", it)
        }
    }

    fun addPortofolio(path: Uri){
        photographerProfile.addPortofolio(path){
            _responseLiveData.postValue(it)

            Log.d("Add Portofolio", it)
        }
    }

    fun fetchPortofolio(){
        photographerProfile.fetchPortofolio {
            _responseListPortofolio.postValue(it)
        }
    }
}