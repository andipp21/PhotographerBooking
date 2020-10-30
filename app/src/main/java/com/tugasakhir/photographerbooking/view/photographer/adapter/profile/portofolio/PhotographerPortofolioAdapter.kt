package com.tugasakhir.photographerbooking.view.photographer.adapter.profile.portofolio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Portofolio
import kotlinx.android.synthetic.main.item_portofolio_photographer.view.*


class PhotographerPortofolioAdapter(val listPortofolio: MutableList<Portofolio> = mutableListOf()) :
    RecyclerView.Adapter<PhotographerPortofolioAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Portofolio) {

            Glide.with(itemView.context)
                .load(item.portofolioImage)
                .into(itemView.ivPortofolio)

            itemView.setOnClickListener { onItemClickCallback?.onItemClicked(item) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_portofolio_photographer, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPortofolio[position])
    }

    override fun getItemCount(): Int {
        return listPortofolio.size
    }

    fun updateLists(newList: List<Portofolio>) {
        listPortofolio.clear()
        listPortofolio.addAll(newList)
        notifyDataSetChanged()
    }

    fun clearList(){
        listPortofolio.clear()
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: Portofolio)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}