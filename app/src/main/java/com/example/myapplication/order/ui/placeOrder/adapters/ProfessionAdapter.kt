package com.example.myapplication.order.ui.placeOrder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.myapplication.R
import com.example.myapplication.order.domain.models.Profession

class ProfessionAdapter(
    context: Context,
    private val professions: MutableList<Profession>,
    private val onItemClick: (Profession) -> Unit
) : ArrayAdapter<Profession>(context, R.layout.dropdown_item, professions)
{
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.dropdown_item, parent, false)
        val profession = getItem(position)
        (view as TextView).text = profession?.name ?: ""

        view.setOnClickListener {
            profession?.let { onItemClick(it) }
        }

        return view
    }


    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }

    fun setList(profList: MutableList<Profession>){
        professions.clear()
        professions.addAll(profList)
    }
}