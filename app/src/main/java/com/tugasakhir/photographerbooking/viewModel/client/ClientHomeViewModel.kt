package com.tugasakhir.photographerbooking.viewModel.client

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.model.services.client.home.ClientHome
import com.tugasakhir.photographerbooking.model.services.photographer.profile.PhotographerProfile
import toothpick.Toothpick
import javax.inject.Inject

class ClientHomeViewModel : ViewModel() {
    @Inject
    lateinit var clientHome: ClientHome

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    private val _responseListPortofolio = MutableLiveData<List<Portofolio>>()
    val responseListPortofolio = _responseListPortofolio

    private val _responseListPackage = MutableLiveData<List<Package>>()
    val responseListPackage = _responseListPackage

    private val _responseLivePhotographer = MutableLiveData<List<User>>()
    val responseLivePhotographer = _responseLivePhotographer

    private val _responseLiveUri = MutableLiveData<Uri>()
    val responseLiveUri = _responseLiveUri

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun fetchPhotographer(){
        clientHome.fetchPhotographer {
            _responseLivePhotographer.postValue(it)
        }
    }
}