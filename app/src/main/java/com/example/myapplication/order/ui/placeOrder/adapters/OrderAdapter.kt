package com.example.myapplication.order.ui.placeOrder.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.CONST
import com.example.myapplication.R
import com.example.myapplication.order.data.dto.order.OrderDto
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

class OrderAdapter(
    private val context: Context,
    private val orders: MutableList<OrderDto>,
    private val onOrderClick: (OrderDto) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageId)
        val titleTextView: TextView = itemView.findViewById(R.id.titleIdTextView)
        val categoryTextView: TextView = itemView.findViewById(R.id.categoryTextViewId)
        val priceTextView: TextView = itemView.findViewById(R.id.priceTextViewId)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextViewId)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.order_view, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        Glide.with(context)
            .load("${CONST.BASE_URL}image/show/${order.mainImagePath}")
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)

        holder.statusTextView.text = order.status.name

        when(order.status.name){
            "Создан" -> {
                holder.statusTextView.setTextColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.status_created)
                )
            }
            "Выполняется" -> {
                holder.statusTextView.setTextColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.status_in_progress)
                )
            }
            "Завершен" -> {
                holder.statusTextView.setTextColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.status_completed)
                )
            }
            "Отменен" -> {
                holder.statusTextView.setTextColor(
                    ContextCompat.getColor(holder.itemView.context, R.color.status_cancelled)
                )
            }
        }
        holder.titleTextView.text = order.title
        holder.categoryTextView.text = order.professionName
        holder.priceTextView.text = "${order.price.toInt()} ₽"
        holder.dateTextView.text = convertIsoToCustomFormat(order.createdAt)

        holder.imageView.setOnClickListener {
            onOrderClick(order)
        }


    }

    override fun getItemCount(): Int = orders.size

    fun setList(newOrders: List<OrderDto>) {
        orders.clear()
        orders.addAll(newOrders)
    }

    fun convertIsoToCustomFormat(time: String): String {
        return try {
            val inputFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
            val outputFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")
            val parsedDate = OffsetDateTime.parse(time, inputFormatter)
            outputFormatter.format(parsedDate)
        } catch (e: Exception) {
            time
        }
    }

}
