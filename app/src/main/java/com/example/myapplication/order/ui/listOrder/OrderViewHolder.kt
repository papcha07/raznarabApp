package com.example.myapplication.order.ui.listOrder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

class OrderViewHolder (view: View) :  RecyclerView.ViewHolder(view){

    private val titleTextView: TextView = itemView.findViewById(R.id.titleIdTextView)
    private val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextViewId)
    private val priceTextView: TextView = itemView.findViewById(R.id.priceTextViewId)
    private val dateTextView: TextView = itemView.findViewById(R.id.dateTextViewId)
    private val imageView: ImageView = itemView.findViewById(R.id.imageId)

    fun bind(order: Order){
        titleTextView.text = order.category
        categoryTextView.text = order.address
        priceTextView.text = order.price.toInt().toString()
        val currentDate = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val formattedDate = currentDate.format(formatter)
        dateTextView.text = formattedDate
        imageView.setImageURI(order.imageUri.toUri())

    }
}
