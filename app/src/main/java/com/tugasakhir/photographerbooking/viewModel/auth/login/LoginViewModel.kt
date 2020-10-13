package com.tugasakhir.photographerbooking.viewModel.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.services.auth.Auth
import toothpick.Toothpick
import javax.inject.Inject

class LoginViewModel : ViewModel() {
    @Inject
    lateinit var auth: Auth
    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    private val _responseLiveUser = MutableLiveData<User>()
    val responseLiveUser = _responseLiveUser

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun login(email: String, password: String) {
        auth.login(email, password) { response ->
            _responseLiveData.postValue(response)
        }
    }

    fun getUser(){
        auth.getUser(){
            _responseLiveUser.postValue(it)
        }
    }
}