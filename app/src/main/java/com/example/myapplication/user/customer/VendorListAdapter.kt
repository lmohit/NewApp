package com.example.myapplication.user.customer

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.user.User

class VendorListAdapter(
    private var vendorsList: List<User>,
    private val context: Context,
    private val vendorListener: VendorSelectionListener
) : RecyclerView.Adapter<VendorListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VendorListViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_vendor_info, null)
        return VendorListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return vendorsList.size
    }

    override fun onBindViewHolder(holder: VendorListViewHolder, position: Int) {
        val vendor = vendorsList[position]
        holder.vendorName.text = vendor.name
        holder.vendorPhone.text = vendor.phoneNumber
        holder.vendorItem.text = vendor.itemSupplied.name
        holder.vendorLayout.setOnClickListener {
            vendorListener.onVendorSelected(vendor)
        }
    }

    fun addVendors(vendorsList: List<User>) {
        Log.d(TAG, "Vendors List : $vendorsList")
        this.vendorsList = vendorsList
        notifyDataSetChanged()
    }

    interface VendorSelectionListener {
        fun onVendorSelected(user: User)
    }

    companion object {
        private const val TAG = "VendorListAdapter"
    }
}