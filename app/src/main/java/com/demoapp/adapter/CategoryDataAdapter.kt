package com.demoapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.demoapp.R
import com.demoapp.model.DataEntity
import de.hdodenhof.circleimageview.CircleImageView

class CategoryDataAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_CATEGORY = 1
        const val VIEW_TYPE_DATA = 2
    }

    fun setData(list: List<DataEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    var list: List<DataEntity> = emptyList()

    private inner class CategoryHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.category_name)
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.category_name
        }
    }

    private inner class DataHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var message: TextView = itemView.findViewById(R.id.data_name)
        var image: CircleImageView = itemView.findViewById(R.id.image)
        fun bind(position: Int) {
            val recyclerViewModel = list[position]
            message.text = recyclerViewModel.name
            Glide.with(image).load(recyclerViewModel.image_link).error(R.color.light_grey).into(image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_CATEGORY) {
            return CategoryHolder(
                LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
            )
        }
        return DataHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].is_category) {
            (holder as CategoryHolder).bind(position)
        } else {
            (holder as DataHolder).bind(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (list[position].is_category) VIEW_TYPE_CATEGORY else VIEW_TYPE_DATA
    }
}
