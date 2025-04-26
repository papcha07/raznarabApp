package com.example.myapplication.order.ui.listOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.myapplication.databinding.FragmentOrderListBinding
import com.example.myapplication.order.ui.placeOrder.OrderViewModel
import com.example.myapplication.order.ui.placeOrder.OrdersListState


class OrderListFragment : Fragment() {
    private lateinit var binding: FragmentOrderListBinding
    private val viewModel: OrderViewModel by activityViewModels()
    private lateinit var adapter : OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OrderAdapter(mutableListOf())
        binding.trackList.adapter = adapter
        orderListObserver()
    }


    private fun orderListObserver(){
        viewModel.getOrdersState().observe(viewLifecycleOwner){
            state ->
            when(state){
                is OrdersListState.Orders -> {
                    showList()
                    adapter.setOrders(state.data)
                }

                is OrdersListState.EmptyList -> {
                    showEmptyMessage()
                }
            }
        }
    }

    private fun showEmptyMessage() {
        binding.trackList.visibility = View.GONE
        binding.messageContainerId.visibility = View.VISIBLE
    }

    private fun showList(){
        binding.trackList.visibility = View.VISIBLE
        binding.messageContainerId.visibility = View.GONE
    }

}