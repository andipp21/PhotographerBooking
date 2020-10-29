package com.tugasakhir.photographerbooking.viewModel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.model.services.order.OrderServices
import toothpick.Toothpick
import javax.inject.Inject

class OrderViewModel: ViewModel() {
    @Inject
    lateinit var orderServices: OrderServices

    private val _responseLiveData = MutableLiveData<String>()
    val responseLiveData = _responseLiveData

    private val _responseListOrder = MutableLiveData<List<Order>>()
    val responseListOrder = _responseListOrder

    private val _responseListUser = MutableLiveData<List<User>>()
    val responseListUser = _responseListUser

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun createOrder(order: Order){
        orderServices.createOrder(order){
            _responseLiveData.postValue(it)
        }
    }

    fun fetchOrderPhotographer(){
        orderServices.fetchOrderPhotographer {
            _responseListOrder.postValue(it)
        }
    }

    fun fetchClient(){
        orderServices.fetchClient {
            _responseListUser.postValue(it)
        }
    }

}