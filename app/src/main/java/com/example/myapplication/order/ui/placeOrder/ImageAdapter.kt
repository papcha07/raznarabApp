package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R

class ImageAdapter(
    private val onItemClick: (Uri) -> Unit
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val items = mutableListOf<Uri>()

    fun setItems(newItems: List<Uri>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.order_image_item, parent, false)
        return ImageViewHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun getFirstItem() : Uri {
        return items.first()
    }

    fun getUriList() : List<Uri> = items

    class ImageViewHolder(itemView: View, private val onItemClick: (Uri) -> Unit) : RecyclerView.ViewHolder(itemView) {
        private val imageView : ImageView = itemView.findViewById(R.id.imageId)
        fun bind(uri: Uri) {
            imageView.setImageURI(uri)
            imageView.setOnClickListener {
                onItemClick(uri)
            }
        }
    }
}
