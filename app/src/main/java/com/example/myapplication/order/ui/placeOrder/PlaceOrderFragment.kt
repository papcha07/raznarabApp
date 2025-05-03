package com.example.myapplication.order.ui.placeOrder

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.databinding.FragmentPlaceOrderBinding
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Profession
import com.example.myapplication.order.domain.models.Order
import org.koin.androidx.viewmodel.ext.android.activityViewModel


class PlaceOrderFragment : Fragment() {
    private lateinit var binding: FragmentPlaceOrderBinding
    private val orderViewModel: OrderViewModel by activityViewModel()
    private lateinit var imageAdapter: ImageAdapter

    private lateinit var placeAdapter: PlaceAdapter
    private lateinit var professionAdapter: ProfessionAdapter
    private var professionId = ""
    private var address: Place? = null

    val picker = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia()
    ) { uris ->
        if (uris != null) {
            orderViewModel.setPhotoState(uris.toMutableList())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaceOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pickImage()
        setUpImageAdapter()
        observeUris()
        setUpPlaceAdapter()
        placeTextWatcherObserever()
        observeAddressList()
        setUpProfessionAdapter()
        observerProfessionList()
    }

    private fun pickImage() {
        binding.addPhotoButtonId.setOnClickListener {
            picker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    private fun setUpImageAdapter(){
        imageAdapter = ImageAdapter { uri ->
            openImage(uri)
        }
        binding.rvId.adapter = imageAdapter
        binding.rvId.layoutManager = GridLayoutManager(requireContext(), 3)
    }

    private fun openImage(uri: Uri) {
        val action = PlaceOrderFragmentDirections.actionPlaceOrderFragmentToImageFragment(uri)
        findNavController().navigate(action)
    }


    private fun observeUris() {
        orderViewModel.getPhotoState().observe(viewLifecycleOwner) { uris ->
            imageAdapter.setItems(uris)
        }
    }

    private fun setUpPlaceAdapter(){
        placeAdapter = PlaceAdapter(
            context = requireContext(),
            places = mutableListOf()
        ){
            place->
            binding.addressEditTextId.setText(place.address)
            binding.addressEditTextId.clearFocus()
            hideKeyboard()
            address = place
        }

        binding.addressEditTextId.setAdapter(placeAdapter)
    }

    private fun placeTextWatcherObserever(){
        binding.addressEditTextId.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                orderViewModel.loadAddressList(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun observeAddressList(){
        orderViewModel.getAddressState().observe(viewLifecycleOwner){
            state ->
            when(state){
                is AddressState.Content -> {
                    updatePlaceAdapter(state.data)
                }
                else ->{ }
            }
        }
    }

    private fun updatePlaceAdapter(addressList: List<Place>){
        placeAdapter.clear()
        placeAdapter.addAll(addressList)
        placeAdapter.notifyDataSetChanged()
        binding.addressEditTextId.showDropDown()
    }

    private fun hideKeyboard(){
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.addressEditTextId.windowToken, 0)
    }

    private fun setUpProfessionAdapter(){
        professionAdapter = ProfessionAdapter(
            context = requireContext(),
            professions = mutableListOf()
        ){
            click ->
            professionId = click.id

        }

        binding.autoCompleteTextViewId.setAdapter(professionAdapter)
    }

    private fun observerProfessionList(){
        orderViewModel.getProfState().observe(viewLifecycleOwner){
            state ->
            when(state){
                is ProfessionState.Content -> {
                    professionAdapter.setList(state.content)
                }
                else -> {}
            }
        }
    }

    






}