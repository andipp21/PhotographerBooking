package com.tugasakhir.photographerbooking.viewModel.photographer

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
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

    private val _responseListPackage = MutableLiveData<List<Package>>()
    val responseListPackage = _responseListPackage

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

    fun updatePortofolio(idPortofoli: String, path:Uri){
        photographerProfile.changePortofolio(idPortofoli,path){
            _responseLiveData.postValue(it)
        }
    }

    fun deletePortofolio(idPortofoli: String){
        photographerProfile.deletePortofolio(idPortofoli){
            _responseLiveData.postValue(it)
        }
    }

    fun addPackage(photoshootPackage : Package){
        photographerProfile.addPackage(photoshootPackage){
            _responseLiveData.postValue(it)
        }
    }

    fun fetchPackage(){
        photographerProfile.fetchPackage {
            _responseListPackage.postValue(it)
        }
    }

    fun deletePackage(photoshootPackage: Package){
        photographerProfile.deletePackage(photoshootPackage){
            _responseLiveData.postValue(it)
        }
    }

    fun updatePackage(photoshootPackage: Package){
        photographerProfile.editPackage(photoshootPackage){
            _responseLiveData.postValue(it)
        }
    }
}