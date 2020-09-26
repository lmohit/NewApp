package com.example.myapplication.user.vendor

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.user.User
import kotlinx.android.synthetic.main.item_customer_info.view.*

class CustomerListAdapter(
    private var customerList: List<User>,
    private val customerListener: CustomerSelectionListener
) : RecyclerView.Adapter<CustomerListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_customer_info, parent, false)
        return CustomerListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return customerList.size
    }

    override fun onBindViewHolder(holder: CustomerListViewHolder, position: Int) {
        val vendor = customerList[position]
        holder.itemView.customerName.text = vendor.userName
        holder.itemView.customerPhone.text = vendor.phoneNumber
        holder.itemView.customerLayout.setOnClickListener {
            customerListener.onVendorSelected(vendor)
        }
    }

    fun addCustomers(customersList: List<User>) {
        Log.d(TAG, "Customers List : $customersList")
        this.customerList = customersList
        notifyDataSetChanged()
    }

    interface CustomerSelectionListener {
        fun onVendorSelected(user: User)
    }

    companion object {
        private const val TAG = "CustomerListAdapter"
    }
}