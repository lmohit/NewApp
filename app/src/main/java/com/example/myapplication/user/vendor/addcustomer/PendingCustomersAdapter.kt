package com.example.myapplication.user.vendor.addcustomer

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.user.User
import kotlinx.android.synthetic.main.accept_request_item.view.*

class PendingCustomersAdapter(
    private var pendingCustomersList: List<User?>,
    private val listener: RequestListener
) : RecyclerView.Adapter<PendingCustomersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingCustomersViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.accept_request_item, parent, false)
        return PendingCustomersViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return pendingCustomersList.size
    }

    override fun onBindViewHolder(holder: PendingCustomersViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        val vendor = pendingCustomersList[position]
        holder.itemView.userName.text = vendor?.userName
        holder.itemView.accept.setOnClickListener {
            listener.onAcceptedRequest(vendor)
        }

        holder.itemView.deny.setOnClickListener {
            listener.onRejectRequest(vendor)
        }
    }

    fun addPendingVendors(pendingCustomers: List<User?>) {
        pendingCustomersList = pendingCustomers
        notifyDataSetChanged()
    }

    interface RequestListener {
        fun onAcceptedRequest(customer: User?)

        fun onRejectRequest(customer: User?)
    }

    companion object {
        private const val TAG = "PendingCustomersAdapter"
    }
}