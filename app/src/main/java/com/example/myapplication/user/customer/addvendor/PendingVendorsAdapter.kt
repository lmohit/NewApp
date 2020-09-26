package com.example.myapplication.user.customer.addvendor

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.user.User
import kotlinx.android.synthetic.main.accept_request_item.view.*

class PendingVendorsAdapter(
    private var pendingVendorsList: List<User?>,
    private val listener: RequestListener
) : RecyclerView.Adapter<PendingVendorsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PendingVendorsViewHolder {
        Log.d(TAG, "onCreateViewHolder")
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.accept_request_item, parent, false)
        return PendingVendorsViewHolder(view)
    }

    override fun getItemCount(): Int {
        Log.d(TAG, "getItemCount")
        return pendingVendorsList.size
    }

    override fun onBindViewHolder(holder: PendingVendorsViewHolder, position: Int) {
        Log.d(TAG, "onBindViewHolder")
        val vendor = pendingVendorsList[position]
        holder.itemView.userName.text = vendor?.userName
        holder.itemView.accept.setOnClickListener {
            listener.onAcceptedRequest(vendor)
        }

        holder.itemView.deny.setOnClickListener {
            listener.onRejectRequest(vendor)
        }
    }

    fun addPendingVendors(pendingVendors: List<User?>) {
        pendingVendorsList = pendingVendors
        notifyDataSetChanged()
    }

    interface RequestListener {
        fun onAcceptedRequest(vendor: User?)

        fun onRejectRequest(vendor: User?)
    }

    companion object {
        private const val TAG = "PendingVendorsAdapter"
    }
}