package com.tugasakhir.photographerbooking.view.photographer.adapter.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.User
import kotlinx.android.synthetic.main.item_order_photographer.view.*

class PhotographerOrderAdapter(
    private val listOrder: MutableList<Order> = mutableListOf(),
    val listUser: MutableList<User> = mutableListOf()
) : RecyclerView.Adapter<PhotographerOrderAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Order) {
            for (dt in listUser){
                if (dt.uid == item.clientID){

                    Glide.with(itemView.context)
                        .load(dt.profilePicture)
                        .circleCrop()
                        .into(itemView.clientPhoto)

                    itemView.yourClientName.text = dt.fullname

                    if (item.isConfirmed && item.isDone){
                        itemView.orderStatus.setText(R.string.status_order_3)
                    } else if (item.isConfirmed && !item.isDone){
                        itemView.orderStatus.setText(R.string.status_order_2)
                    } else if (!item.isConfirmed && !item.isDone){
                        itemView.orderStatus.setText(R.string.status_order_1)
                    }

                    itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item, dt) }
                }
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_photographer, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listOrder[position])
    }

    override fun getItemCount(): Int {
        return listOrder.size
    }

    fun updateListsOrder(newList: List<Order>) {
        listOrder.clear()
        listOrder.addAll(newList)
        notifyDataSetChanged()
    }

    fun updateListUser(newList: List<User>){
        listUser.clear()
        listUser.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearList(){
        listUser.clear()
        listOrder.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(order: Order, client: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}