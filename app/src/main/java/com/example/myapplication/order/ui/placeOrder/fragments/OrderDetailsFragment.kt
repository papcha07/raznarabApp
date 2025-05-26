package com.example.myapplication.order.ui.placeOrder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.CONST
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentOrderDetailsBinding
import com.example.myapplication.order.data.dto.order.OrderDto
import com.example.myapplication.order.domain.TimeFormatter
import com.example.myapplication.order.ui.placeOrder.OrderViewModel
import com.example.myapplication.order.ui.placeOrder.adapters.CandidatesAdapter
import com.example.myapplication.order.ui.placeOrder.state.CandidatesState
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class OrderDetailsFragment : Fragment() {

    private lateinit var binding: FragmentOrderDetailsBinding
    private val orderViewModel: OrderViewModel by viewModel()
    private lateinit var orderForView: OrderDto
    private lateinit var recyclerView: RecyclerView
    private lateinit var candidatesAdapter: CandidatesAdapter
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
        orderViewModel.getAllCandidates(orderForView.id)
        observeCancelState()
        setAdapter()
        observerCandidates()
        cancelOrder(orderForView.id)
    }

    private fun cancelOrder(orderId: String){
        binding.buttonCancelOrder.setOnClickListener {
            orderViewModel.cancelOrder(orderId)
        }
    }

    private fun setAdapter(){
        candidatesAdapter = CandidatesAdapter(
            context = requireContext(),
            candidatesList = mutableListOf(),
        ){
            candidate ->
            val cand = candidate
            val action = OrderDetailsFragmentDirections.actionOrderDetailsFragment2ToExecutorDetailFragment(candidate.id, orderForView.id)
            findNavController().navigate(action)
        }
        recyclerView = binding.recyclerResponses
        recyclerView.adapter = candidatesAdapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun fillScreen(){
        binding.textOrderTitle.text = orderForView.title
        binding.textOrderPrice.text = "${orderForView.price.toInt()}₽"
        binding.textOrderDate.text = TimeFormatter.formatResponseTime(orderForView.createdAt)

        val avatarUrl = "${CONST.BASE_URL}image/show/${orderForView.mainImagePath}"
        Glide.with(requireContext())
            .load(avatarUrl)
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageOrderPhoto)
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

    private fun observerCandidates(){
        orderViewModel.getCandidateState().observe(viewLifecycleOwner){
            state ->
            when(state){

                is CandidatesState.Content -> {
                    candidatesAdapter.setList(state.data)
                }

                is CandidatesState.Failed -> {
                    Toast.makeText(
                        requireContext(),
                        "Не удалось загрузить кандидатов..",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is CandidatesState.NoInternet -> {
                    Toast.makeText(
                        requireContext(),
                        "Проблемы с интернетом..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }



}