package com.tugasakhir.photographerbooking.view.client.adapter.order

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Order
import com.tugasakhir.photographerbooking.model.pojo.User
import kotlinx.android.synthetic.main.item_order_client.view.*
import kotlinx.android.synthetic.main.item_order_photographer.view.orderStatus
import kotlinx.android.synthetic.main.item_order_photographer.view.orderTime
import java.util.*

class ClientOrderAdapter(
    private val listOrder: MutableList<Order> = mutableListOf(),
    private val listUser: MutableList<User> = mutableListOf()
) : RecyclerView.Adapter<ClientOrderAdapter.ViewHolder>() {
    private var onItemClickCallback: OnItemClickCallback? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private fun bulanString(bulan: Int): String {
        return when (bulan + 1) {
            1 -> "Jan"
            2 -> "Feb"
            3 -> "Mar"
            4 -> "Apr"
            5 -> "May"
            6 -> "Jun"
            7 -> "Jul"
            8 -> "Aug"
            9 -> "Sep"
            10 -> "Oct"
            11 -> "Nov"
            12 -> "Dec"
            else -> ""
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_client, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("data user", listUser.toString())
        Log.d("data order", listOrder.toString())
        val item = listOrder[position]
        for (dt in listUser){
            if (dt.uid == item.photographerID){

                Glide.with(holder.itemView.context)
                    .load(dt.profilePicture)
                    .circleCrop()
                    .into(holder.itemView.photographerPhoto)

                holder.itemView.yourPhotographerName.text = dt.fullname

                if (item.isConfirmed && item.isPayed && item.isDone){
                    holder.itemView.orderStatus.setText(R.string.status_order_3)
                    holder.itemView.orderStatus.setBackgroundResource(R.drawable.button_enabled)
                    holder.itemView.orderStatus.setTextColor(Color.WHITE)
                } else if (item.isConfirmed && item.isPayed && !item.isDone){
                    holder.itemView.orderStatus.setText(R.string.status_order_4)
                    holder.itemView.orderStatus.setTextColor(Color.parseColor("#2BD6A9"))
                } else if (item.isConfirmed && !item.isPayed && !item.isDone){
                    holder.itemView.orderStatus.setText(R.string.status_order_2)
                    holder.itemView.orderStatus.setTextColor(Color.parseColor("#2BD6A9"))
                } else if (!item.isConfirmed && !item.isPayed && !item.isDone){
                    holder.itemView.orderStatus.setText(R.string.status_order_1)
                    holder.itemView.orderStatus.setTextColor(Color.DKGRAY)
                }

                val cal = Calendar.getInstance()
                cal.time = item.orderTime
                val year = cal.get(Calendar.YEAR)
                val month = cal.get(Calendar.MONTH)
                val day = cal.get(Calendar.DAY_OF_MONTH)
                val hour = cal.get(Calendar.HOUR_OF_DAY)
                val minute = cal.get(Calendar.MINUTE)

                val bulan: String = bulanString(month)

                val hari: String = if (day < 10) {
                    "0$day"
                } else {
                    "$day"
                }

                val jam: String = if (hour < 10) {
                    "0$hour"
                } else {
                    "$hour"
                }

                val menit: String = if (minute < 10) {
                    "0$minute"
                } else {
                    "$minute"
                }

                holder.itemView.orderTime.text = "$hari $bulan $year, $jam:$menit"

                holder.itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item, dt) }
            }
        }
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
        fun onItemClicked(order: Order, photographer: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}