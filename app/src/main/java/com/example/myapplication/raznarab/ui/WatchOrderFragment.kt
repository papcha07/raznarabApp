package com.example.myapplication.raznarab.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentWatchOrderBinding
import com.example.myapplication.order.ui.placeOrder.OrderViewModel
import com.example.myapplication.raznarab.ui.domain.dto.MapOrder
import com.google.gson.Gson
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class xWatchOrderFragment : Fragment() {

    private lateinit var binding: FragmentWatchOrderBinding
    private val args: xWatchOrderFragmentArgs by navArgs()
    private val gson: Gson by inject()
    private lateinit var order: MapOrder
    private lateinit var viewPager: ViewPager2
    private lateinit var photoAdapter: PhotoPagerAdapter
    private val orderViewModel : MapViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val mapOrder = gson.fromJson(args.order, MapOrder::class.java)
        order = mapOrder
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        fillScreen(order)
        respondToOrder(order.id)
    }

    private fun initAdapter(){
        viewPager = binding.viewPagerPhotos
        photoAdapter = PhotoPagerAdapter(mutableListOf())
        viewPager.adapter = photoAdapter
    }

    private fun fillScreen(order: MapOrder){
        binding.tvTitle.text = order.title
        binding.tvPrice.text = "₽ ${order.price.toInt().toString()}"
        binding.tvAddress.text = order.address
        binding.tvDescription.text = order.shortDescription
        photoAdapter.updateData(order.images)
    }

    private fun respondToOrder(orderId: String){
        binding.button.setOnClickListener {
            orderViewModel.respondToOrder(orderId)
            findNavController().navigate(R.id.action_xWatchOrderFragment_to_mapFragment2)
        }
    }

    
}