package com.example.myapplication.user.customer

import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class VendorListViewHolder(view: View): RecyclerView.ViewHolder(view) {

    var vendorName: TextView = view.findViewById(R.id.vendorName)
    var vendorPhone: TextView = view.findViewById(R.id.vendorPhone)
    var vendorItem: TextView = view.findViewById(R.id.vendorItem)
    var vendorLayout: LinearLayout = view.findViewById(R.id.vendorLayout)
}