package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentImageBinding


class ImageFragment : Fragment() {

    private lateinit var binding : FragmentImageBinding
    private lateinit var uri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ImageFragmentArgs.fromBundle(requireArguments())
        uri = args.uri
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setImage()
        goBack()
    }

    private fun setImage(){
        binding.imageId.setImageURI(uri)
    }

    private fun goBack(){
        binding.closeButtonId.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}