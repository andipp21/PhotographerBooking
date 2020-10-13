package com.tugasakhir.photographerbooking.viewModel.photographer

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.services.auth.Auth
import com.tugasakhir.photographerbooking.model.services.photographer.profile.PhotographerProfile
import toothpick.Toothpick
import javax.inject.Inject

class PhotographerViewModel : ViewModel() {
    @Inject
    lateinit var photographerProfile: PhotographerProfile
    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    private val _responseLiveUser = MutableLiveData<User>()
    val responseLiveUser = _responseLiveUser

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun getUser(){
        photographerProfile.getUser(){
            _responseLiveUser.postValue(it)
        }
    }
}