package com.tugasakhir.photographerbooking.viewModel.order

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.Package
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

    private val _responsePackage = MutableLiveData<Package>()
    val responsePackage = _responsePackage

    init {
        val scope = Toothpick.openScope(this)
        Toothpick.inject(this, scope)
    }

    fun createOrder(order: Order){
        orderServices.createOrder(order){
            _responseLiveData.postValue(it)
        }
    }

    fun fetchOrderPhotographer(usrID: String){
        orderServices.fetchOrderPhotographer(usrID) {
            _responseListOrder.postValue(it)
        }
    }

    fun fetchOrderClient(usrID: String){
        orderServices.fetchOrderClient(usrID) {
            _responseListOrder.postValue(it)
        }
    }

    fun fetchClient(){
        orderServices.fetchClient {
            _responseListUser.postValue(it)
        }
    }

    fun fetchPhotographer(){
        orderServices.fetchPhotographer {
            _responseListUser.postValue(it)
        }
    }

    fun getPackage(id: String){
        orderServices.getPackagebyID(id){
            _responsePackage.postValue(it)
        }
    }

    fun confirmationOrder(id:String){
        orderServices.confirmOrder(id){
            _responseLiveData.postValue(it)
        }
    }

    fun confirmationOrderDone(id:String){
        orderServices.confirmOrderDone(id){
            _responseLiveData.postValue(it)
        }
    }

    fun payOrder(path: Uri, idOrder: String){
        orderServices.makePayment(path, idOrder){
            _responseLiveData.postValue(it)
        }
    }

}
