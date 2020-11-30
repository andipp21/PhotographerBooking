package com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail.review

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.Review
import com.tugasakhir.photographerbooking.model.pojo.ReviewDetail
import com.tugasakhir.photographerbooking.model.pojo.User
import com.tugasakhir.photographerbooking.viewModel.order.OrderViewModel
import kotlinx.android.synthetic.main.item_review.view.*

class PhotographerDetailReviewAdapter(
    val listReview: MutableList<Review> = mutableListOf()
) : RecyclerView.Adapter<PhotographerDetailReviewAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_review, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listReview.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.reviewScore.text = "${listReview[position].score}/5"
        holder.itemView.reviewText.text = listReview[position].review
    }

    fun updateList(newList: List<Review>){
        listReview.clear()
        listReview.addAll(newList)
        notifyDataSetChanged()
    }




}