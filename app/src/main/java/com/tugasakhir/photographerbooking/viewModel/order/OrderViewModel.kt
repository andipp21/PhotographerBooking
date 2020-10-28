package com.tugasakhir.photographerbooking.viewModel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.services.order.OrderServices
import toothpick.Toothpick
import javax.inject.Inject

class OrderViewModel: ViewModel() {
    @Inject
    lateinit var orderServices: OrderServices

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun createOrder(order: Order){
        orderServices.createOrder(order){
            _responseLiveData.postValue(it)
        }
    }

}