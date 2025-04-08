package com.example.myapplication.authorization.ui.registration

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CategoryAdapter(context: Context, private val categories: List<RoleCategory>) :
    ArrayAdapter<RoleCategory>(context, android.R.layout.simple_spinner_item, categories) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent) as TextView
        view.text = categories[position].name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent) as TextView
        view.text = categories[position].name
        return view
    }
}