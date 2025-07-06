package com.example.myapplication.raznarab.ui

import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.myapplication.CONST
import com.example.myapplication.R

class PhotoPagerAdapter(
    private val photos: MutableList<String>
) : RecyclerView.Adapter<PhotoPagerAdapter.PhotoViewHolder>() {

    inner class PhotoViewHolder(val imageView: ImageView) : RecyclerView.ViewHolder(imageView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        val imageView = ImageView(parent.context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            scaleType = ImageView.ScaleType.CENTER_CROP
            background = ContextCompat.getDrawable(context, R.drawable.bg_rounded_corners)
            clipToOutline = true


        }
        return PhotoViewHolder(imageView)
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val url = photos[position]
        val radiusInPx = 20 // радиус скругления в пикселях
        Glide.with(holder.imageView.context)
            .load("${CONST.BASE_URL}image/show/$url")
            .apply(RequestOptions().transform(RoundedCorners(radiusInPx)))
            .placeholder(R.drawable.ic_add_photo)
            .error(R.drawable.ic_add_photo)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = photos.size

    fun updateData(newPhotos: List<String>) {
        photos.clear()
        photos.addAll(newPhotos)
        notifyDataSetChanged()
    }
}
