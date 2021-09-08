package com.demoapp.adapter

import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.demoapp.R
import com.demoapp.adapterutils.DelegateAdapter
import com.demoapp.adapterutils.LayoutViewHolder
import kotlinx.android.synthetic.main.item_data.view.*

class DataViewDA : DelegateAdapter<DataViewDA.ViewHolder, DataViewRM> {

    override fun onCreateViewHolder(parent: ViewGroup?): ViewHolder = ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, item: DataViewRM) = holder.bind(item)

    inner class ViewHolder(parent: ViewGroup?) :
        LayoutViewHolder<DataViewRM>(parent!!, R.layout.item_data) {

        override fun bind(item: DataViewRM) {
            itemView.data_name.text = item.name
            Glide.with(itemView.image).load(item.image_link).error(R.color.light_grey)
                .into(itemView.image)
        }
    }

}
