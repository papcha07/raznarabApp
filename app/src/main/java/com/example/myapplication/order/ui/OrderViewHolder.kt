package com.example.myapplication.order.ui

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class OrderViewHolder (view: View) :  RecyclerView.ViewHolder(view){

    private val titleTextView: TextView = itemView.findViewById(R.id.titleIdTextView)
    private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextViewId)
    private val priceTextView: TextView = itemView.findViewById(R.id.priceTextViewId)
    private val dateTextView: TextView = itemView.findViewById(R.id.dateTextViewId)


    fun bind(order: Order){
        titleTextView.text = order.title
        categoryTextView.text = order.category
        priceTextView.text = order.price
        dateTextView.text = order.created
    }
}
