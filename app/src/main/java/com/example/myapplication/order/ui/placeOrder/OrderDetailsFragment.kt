package com.example.myapplication.order.ui.placeOrder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderDetailsBinding
import com.example.myapplication.databinding.FragmentPlaceOrderBinding
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.domain.models.OrderForView
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private val orderViewModel: OrderViewModel by viewModel()
    private lateinit var orderForView: OrderDto
    private val gson: Gson by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = OrderDetailsFragmentArgs.fromBundle(requireArguments()).orderInfo
        val order = gson.fromJson(args, OrderDto::class.java)
        orderForView = order
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOrderDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fillScreen()
        cancelOrder(orderForView.id)
        observeCancelState()
    }

    private fun cancelOrder(orderId: String){
        binding.buttonCancelOrder.setOnClickListener {
            orderViewModel.cancelOrder(orderId)
        }
    }

    private fun fillScreen(){
        binding.textOrderTitle.text = orderForView.title
        binding.textOrderDescription.text = orderForView.professionName
    }

    private fun observeCancelState(){
        orderViewModel.getCancelOrderState().observe(viewLifecycleOwner){
            state ->
            when(state){
                true -> {
                    findNavController().navigate(R.id.action_orderDetailsFragment2_to_orderListFragment)
                }
                false -> {
                    Toast.makeText(requireContext(), "Ошибка отмена заказа\nПопробуйте позже", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}