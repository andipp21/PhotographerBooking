package com.tugasakhir.photographerbooking.view.client.adapter.explore

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.auth.User
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import kotlinx.android.synthetic.main.item_client_explore.view.*
import kotlinx.android.synthetic.main.item_portofolio_photographer.view.*

class ClientExploreAdapter(val listPhotographer: MutableList<User> = mutableListOf()) :
    RecyclerView.Adapter<ClientExploreAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: User) {

            Glide.with(itemView.context)
                .load(item.profilePicture)
                .into(itemView.ivProfilePicturePhotographer)

            itemView.photographerName.text = item.fullname
            itemView.photographerLocation.text = item.city

            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_client_explore, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhotographer[position])
    }

    override fun getItemCount(): Int {
        return  listPhotographer.size
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun updateLists(newList: List<User>) {
        listPhotographer.clear()
        listPhotographer.addAll(newList)
        notifyDataSetChanged()
    }
}