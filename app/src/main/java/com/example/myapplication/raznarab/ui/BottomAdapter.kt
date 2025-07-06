package com.example.myapplication.raznarab.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import com.example.myapplication.raznarab.ui.domain.dto.MapOrderForView

class BottomAdapter(
    private val albumList: MutableList<MapOrder>,
    private val onOrderClick : (MapOrder) -> Unit
    ) : RecyclerView.Adapter<BottomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return BottomViewHolder(view)
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener {
            onOrderClick(albumList[position])
        }
    }

    fun setContent(list: List<MapOrder>) {
        albumList.clear()
        albumList.addAll(list)
        notifyDataSetChanged()
    }
}

class BottomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val addressTextView = itemView.findViewById<TextView>(R.id.addressTextView)
    private val descTextView = itemView.findViewById<TextView>(R.id.descriptionTextView)
    private val priceTextView = itemView.findViewById<TextView>(R.id.priceTextView)
    private val titleTextView = itemView.findViewById<TextView>(R.id.titleTextView)


    fun bind(order: MapOrder){
        addressTextView.text = "📍 Адрес: ${order.address}"
        priceTextView.text = "💰 Цена: ${order.price.toInt()} ₽"
        descTextView.text = order.shortDescription
        titleTextView.text = order.title
    }
}
