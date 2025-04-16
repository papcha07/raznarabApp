package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ImageAdapter : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    private val items = mutableListOf<Uri>()

    fun setItems(newItems: List<Uri>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(300, 300)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        return ImageViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ImageViewHolder(private val imageView: ImageView) :
        RecyclerView.ViewHolder(imageView) {
        fun bind(uri: Uri) {
            imageView.setImageURI(uri)
        }
    }
}
