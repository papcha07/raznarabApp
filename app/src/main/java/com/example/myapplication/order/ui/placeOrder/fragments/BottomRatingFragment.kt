package com.example.myapplication.order.ui.placeOrder.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.lifecycle.ViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentBottomRatingBinding
import com.example.myapplication.order.ui.placeOrder.OrderViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class BottomRatingFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomRatingBinding
    private lateinit var ratingBar: RatingBar
    private val mapViewModel: OrderViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBottomRatingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ratingBar = binding.ratingBar
        ratingBarListener()
    }

    private fun ratingBarListener(){
        ratingBar.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            mapViewModel.currentRating.postValue(rating.toInt())
        }
    }






}