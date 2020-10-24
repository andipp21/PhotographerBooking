package com.tugasakhir.photographerbooking.view.photographer.adapter.profile.photographerPackage

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.photographerbooking.R
import kotlinx.android.synthetic.main.item_benefit_photoshoot.view.*

class PhotographerBenefitAdapter(
    val context: Context,
    var listBenefit: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<PhotographerBenefitAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(benefit: String) {
            Log.d("benefit Adapter", benefit)

            itemView.benefitView.text = benefit
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PhotographerBenefitAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_benefit_photoshoot, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhotographerBenefitAdapter.ViewHolder, position: Int) {
        holder.bind(listBenefit[position])

        holder.itemView.btnDeleteBenefit.setOnClickListener {
            onItemClickCallback?.deleteButtonOnClick(position)
        }

        Log.d("benefit Adapter", listBenefit.toString())
    }

    override fun getItemCount(): Int {
        return listBenefit.size
    }

    fun updateLists(newList: List<String>) {
        Log.d("benefit list", newList.toString())
        listBenefit.clear()
        listBenefit.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun deleteButtonOnClick(position: Int)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
}