package com.tugasakhir.photographerbooking.view.photographer.adapter.profile.photographerPackage

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tugasakhir.photographerbooking.R
import com.tugasakhir.photographerbooking.model.pojo.photographer.Package
import com.tugasakhir.photographerbooking.model.pojo.photographer.Portofolio
import com.tugasakhir.photographerbooking.view.photographer.adapter.profile.portofolio.PhotographerPortofolioAdapter
import kotlinx.android.synthetic.main.item_package_photographer.view.*
import kotlinx.android.synthetic.main.item_portofolio_photographer.view.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

class PhotographerPackageAdapter(val listPackage: MutableList<Package> = mutableListOf()) : RecyclerView.Adapter<PhotographerPackageAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Package) {

            itemView.tvTitlePackage.text = item.title
            itemView.tvPhotoshootType.text = item.type
            itemView.tvTimerType.text = item.time
            itemView.tvPhotoshootRate.text = convertMoney(item.price)

            itemView.btnEditPackage.setOnClickListener {
                onItemClickCallback?.editPackageClicked(item)
            }

            itemView.btnDeletePackage.setOnClickListener {
                onItemClickCallback?.deletePacakageClicked(item)
            }
        }

        fun convertMoney(input: Long): String {
            val formatter =
                NumberFormat.getCurrencyInstance(Locale("in", "ID")) as DecimalFormat

            var formattedString = formatter.format(input).toString()

            if (formattedString.contains("Rp")) {
                formattedString = formattedString.replace("Rp".toRegex(), "Rp ")
            }

            if (formattedString.contains(",00")) {
                formattedString = formattedString.replace(",00".toRegex(), "")
            }

            return formattedString
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_package_photographer, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listPackage.size
    }

    fun updateLists(newList: List<Package>) {
        Log.d("list Package", newList.toString())

        listPackage.clear()
        listPackage.addAll(newList)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun editPackageClicked(data: Package)
        fun deletePacakageClicked(data: Package)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPackage[position])
    }
}