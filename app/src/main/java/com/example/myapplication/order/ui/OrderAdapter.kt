package com.example.myapplication.order.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class OrderAdapter (private val orderList: MutableList<Order>) : RecyclerView.Adapter<OrderViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orderList[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }
}