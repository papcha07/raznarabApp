package com.example.myapplication.order.ui.placeOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderList2Binding
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderListFragment : Fragment() {


    private lateinit var binding: FragmentOrderList2Binding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView
    private val ordersViewModel : OrderViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderList2Binding.inflate(layoutInflater, container , false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerVIew()
        observeAllOrders()
//        setUpTouchHelperCallBack()
    }

    private fun setUpRecyclerVIew(){
        orderAdapter = OrderAdapter(
            mutableListOf()
        )
        recyclerView = binding.recyclerViewId
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = orderAdapter
    }

    private fun observeAllOrders(){
        ordersViewModel.getOrdersState().observe(viewLifecycleOwner){
            state ->
            when(state){
                is OrdersListState.Orders -> {
                    orderAdapter.setList(state.data)
                }
                is OrdersListState.EmptyList -> {

                }
            }
        }
    }


//    private fun setUpTouchHelperCallBack(){
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean = false
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.adapterPosition
//                orderAdapter.removeItem(position)
//                val currentOrder = orderAdapter.getItem(position)
//                ordersViewModel.deleteOrder(currentOrder.id)
//            }
//        }
//        ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView)
//    }




}