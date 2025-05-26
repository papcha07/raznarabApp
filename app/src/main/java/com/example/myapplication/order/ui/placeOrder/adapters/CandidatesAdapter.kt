package com.example.myapplication.order.ui.placeOrder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.CONST
import com.example.myapplication.R
import com.example.myapplication.order.domain.TimeFormatter
import com.example.myapplication.order.domain.models.Candidate

class CandidatesAdapter(
    private val context: Context,
    private val candidatesList: MutableList<Candidate>,
    private val onCandidateClick: (Candidate) -> Unit
) : RecyclerView.Adapter<CandidatesAdapter.CandidatesViewHolder>() {

    class CandidatesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val phoneTextView: TextView = itemView.findViewById(R.id.textPhone)
        val textRatingTextView: TextView = itemView.findViewById(R.id.textRating)
        val typeTextView: TextView = itemView.findViewById(R.id.typeId)
        val descriptionTextView: TextView = itemView.findViewById(R.id.textDescription)
        val imageView: ImageView = itemView.findViewById(R.id.imageAvatar)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CandidatesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.respondent_item_view, parent, false)
        return CandidatesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return candidatesList.size
    }

    override fun onBindViewHolder(holder: CandidatesViewHolder, position: Int) {
        val candidate = candidatesList[position]
        val type = candidate.type
        when{
            type == "Company" -> {
                holder.typeTextView.text = "Компания"
                if(candidate.companyName != null){
                    holder.descriptionTextView.text = "${candidate.companyName}"
                }
            }
            type == "User" -> {
                holder.typeTextView.text = "Частное лицо"
                if(candidate.firstName != null && candidate.secondName != null){
                    holder.descriptionTextView.text = "${candidate.firstName} ${candidate.secondName}"
                }
            }
        }
        if(candidate.rating == null){
            holder.textRatingTextView.text = "5.00"
        } else{
            holder.textRatingTextView.text = candidate.rating.toString()
        }
        holder.phoneTextView.text = candidate.phoneNumber
        holder.itemView.setOnClickListener {
            onCandidateClick(candidate)
        }

        Glide.with(context)
            .load("${CONST.BASE_URL}image/show/${candidate.imagePath}")
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.imageView)
    }

    fun setList(newCandidates: List<Candidate>){
        candidatesList.clear()
        candidatesList.addAll(newCandidates)
        notifyDataSetChanged()
    }


}