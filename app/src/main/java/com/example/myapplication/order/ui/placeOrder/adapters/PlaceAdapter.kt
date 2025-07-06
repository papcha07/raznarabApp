package com.example.myapplication.order.ui.placeOrder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.TextView
import com.example.myapplication.order.domain.models.Place

class PlaceAdapter(
    context: Context,
    private val places: MutableList<Place>,
    private val onItemClick: (Place) -> Unit
) :
    ArrayAdapter<Place>(context, android.R.layout.simple_dropdown_item_1line, places) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(android.R.layout.simple_dropdown_item_1line, parent, false)

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = getItem(position)?.address ?: ""
        val place = getItem(position)

        view.setOnClickListener {
            place?.let { onItemClick(it) }
        }
        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                results.values = places
                results.count = places.size
                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                clear()
                addAll(results?.values as List<Place>)
                notifyDataSetChanged()
            }
        }
    }
}

