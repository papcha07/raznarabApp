package com.example.myapplication.order.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderListBinding


class OrderListFragment : Fragment() {
    private lateinit var binding: FragmentOrderListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderList = mutableListOf<Order>(
            Order(
                id = 1,
                address  = "Забобонова 16",
                category  = "Электромонтаж",
                price = "2500",
                lon = 22.0,
                lat = 21.2,
                created = "22.12.2004",
                title = "Сделать счетчик"
            ),

            Order(
                id = 2,
                address  = "Крупская 10б",
                category  = "Электромонтаж",
                price = "2600",
                lon = 22.0,
                lat = 21.2,
                created = "22.12.2004",
                title = "Сделать линию передач"
            ),

        )
        val adapter = OrderAdapter(orderList)
        binding.trackList.adapter = adapter



    }

}