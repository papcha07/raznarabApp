package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentImageBinding
import com.example.myapplication.databinding.FragmentWatchOrderBinding


class WatchOrderFragment : Fragment() {

    private lateinit var binding: FragmentWatchOrderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWatchOrderBinding.inflate(inflater, container, false)
        return binding.root
    }

}