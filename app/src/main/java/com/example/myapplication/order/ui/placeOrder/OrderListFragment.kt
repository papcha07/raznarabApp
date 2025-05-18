package com.example.myapplication.order.ui.placeOrder

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderList2Binding
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.domain.models.OrderForView
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderListFragment : Fragment() {


    private lateinit var binding: FragmentOrderList2Binding
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var recyclerView: RecyclerView
    private val gson: Gson by inject()
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
    }

    private fun setUpRecyclerVIew(){
        orderAdapter = OrderAdapter(requireContext(),
            mutableListOf()
        ){
            order ->
            if(!order.isCancelled){
                navigateToOrderDetailScree(order)
                ordersViewModel.getAllCandidates(order.id)
            }
        }
        recyclerView = binding.recyclerViewId
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = orderAdapter
    }

    private fun observeAllOrders(){
        ordersViewModel.getOrdersState().observe(viewLifecycleOwner){
            state ->
            when(state){
                is OrdersListState.Orders -> {
                    showList(state.data)
                }
                is OrdersListState.EmptyList -> {
                    showEmptyContainer()
                }

                is OrdersListState.Loading -> {
                    showProgressBar()
                }

                is OrdersListState.Failed -> {

                }
            }
        }
    }


    private fun showList(list: List<OrderDto>){
        orderAdapter.setList(list)
        binding.progressBarId.visibility = View.GONE
        binding.recyclerViewId.visibility = View.VISIBLE
        binding.emptyContainerId.visibility = View.GONE
    }

    private fun showEmptyContainer(){
        binding.progressBarId.visibility = View.GONE
        binding.recyclerViewId.visibility = View.GONE
        binding.emptyContainerId.visibility = View.VISIBLE
    }

    private fun showProgressBar(){
        binding.progressBarId.visibility = View.VISIBLE
        binding.recyclerViewId.visibility = View.GONE
        binding.emptyContainerId.visibility = View.GONE
    }

    private fun navigateToOrderDetailScree(order: OrderDto){
        val gsonOrder = gson.toJson(order)
        val action = OrderListFragmentDirections.actionOrderListFragmentToOrderDetailsFragment2(gsonOrder)
        findNavController().navigate(action)
    }




}