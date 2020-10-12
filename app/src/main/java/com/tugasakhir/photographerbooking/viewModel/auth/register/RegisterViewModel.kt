package com.tugasakhir.photographerbooking.viewModel.auth.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.services.auth.Auth
import toothpick.Toothpick
import javax.inject.Inject

class RegisterViewModel : ViewModel() {
    @Inject
    lateinit var auth: Auth

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun registerUser(user: User){
        auth.register(user) {
            Log.d("Firebase", it)
            _responseLiveData.postValue(it)
        }
    }
}