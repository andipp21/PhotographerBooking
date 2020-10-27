package com.tugasakhir.photographerbooking.view.client.adapter.photographerDetail.`package`

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tugasakhir.photographerbooking.R
import kotlinx.android.synthetic.main.item_benefit_photoshoot.view.*
import kotlinx.android.synthetic.main.item_benefit_photoshoot.view.benefitView
import kotlinx.android.synthetic.main.item_package_benefit.view.*

class PhotographerDetailPackageBenefitAdapter(
    private val listBenefit: MutableList<String> = mutableListOf()
) : RecyclerView.Adapter<PhotographerDetailPackageBenefitAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(benefit: String) {
            Log.d("benefit Adapter", benefit)

            itemView.benefitView.text = benefit
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_package_benefit, parent, false)

        return ViewHolder(view)
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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listBenefit[position])

        holder.itemView.viewNumber.text = "${position + 1}."
    }
}