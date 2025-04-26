package com.example.myapplication.order.ui.placeOrder

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPlaceOrderBinding
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Profession
import com.example.myapplication.order.ui.listOrder.Order
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.w3c.dom.Text


class PlaceOrderFragment : Fragment() {
    private lateinit var binding: FragmentPlaceOrderBinding
    private val orderViewModel: OrderViewModel by activityViewModel()

    private lateinit var adapter: PlaceAdapter
    private lateinit var addressList: MutableList<Place>
    private lateinit var autoCompleteTextView: AutoCompleteTextView
    private lateinit var imageAdapter: ImageAdapter

    private lateinit var profAdapter : ProfessionAdapter
    private lateinit var profAutoCompleteTextView: AutoCompleteTextView

    val picker = registerForActivityResult(
        ActivityResultContracts.PickMultipleVisualMedia()
    ){
        uris ->
        if(uris != null){
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
        autoCompleteTextView = binding.addressEditTextId
        addressList = mutableListOf()

        adapter = PlaceAdapter(requireContext(), addressList){
            place ->
            autoCompleteTextView.setText(place.toString())
            orderViewModel.chooseAddress()
        }
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                findAddress(p0.toString())
                orderViewModel.notChooseAddress()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
        orderViewModel.getAddressState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AddressState.Content -> {
                    updateAdapter(state.data)
                }

                else -> {

                }
            }
        }

        val professionList = mutableListOf<Profession>()
        val professionsAdapter = ProfessionAdapter(
            requireContext(),
            professionList
        )
        profAutoCompleteTextView = binding.autoCompleteTextViewId
        profAutoCompleteTextView.setAdapter(professionsAdapter)


        orderViewModel.getProfState().observe(viewLifecycleOwner){
            profList ->
            when(profList){
                is ProfessionState.Content -> {
                    professionsAdapter.setList(profList.content)
                }

                is ProfessionState.Error -> {
                    Toast.makeText(requireContext(), "не удалось загрузить список профессий", Toast.LENGTH_SHORT).show()
                }
            }
        }

        var current = ""
        profAutoCompleteTextView.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                current = p0.toString()
                Log.d("current", current.toString())

            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })






        pickImage()
        placeOrder()

        imageAdapter = ImageAdapter{
            uri ->
            openImage(uri)
        }

        val recyclerView = binding.rvId
        recyclerView.layoutManager = GridLayoutManager(requireContext(), 3)
        recyclerView.adapter = imageAdapter
        observeUris()

    }

    private fun findAddress(address: String) {
        orderViewModel.loadAddressList(address)
    }

    private fun updateAdapter(placesList: List<Place>) {
        adapter.clear()
        adapter.addAll(placesList)
        adapter.notifyDataSetChanged()
        autoCompleteTextView.setAdapter(adapter)
        autoCompleteTextView.post {
            binding.addressEditTextId.showDropDown()
        }
    }

    private fun placeOrder() {
        binding.placeOrderButtonId.setOnClickListener {
            when{
                !allFieldsFilled() -> {
                    showToast("Заполните все поля")
                }
                orderViewModel.getAddressClickState().value == false -> {
                    showToast("Выберите адрес из списка")
                }
                else ->{
                    val order = Order(
                        orderId = 0,
                        address = binding.addressEditTextId.text.toString(),
                        category = binding.autoCompleteTextViewId.text.toString(),
                        price = binding.priceEdiTextId.text.toString().toDouble(),
                        description = binding.descriptionEditTextId.text.toString(),
                        imageUri = imageAdapter.getFirstItem().toString()
                    )
                    createOrder(order)
                }
            }
        }
    }

    private fun allFieldsFilled(): Boolean {
        return listOf(
            binding.priceEdiTextId.text,
            binding.addressEditTextId.text,
            binding.autoCompleteTextViewId.text,
        ).all { it?.isNotBlank() == true }
    }


    private fun showToast(message : String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun pickImage(){
        binding.addPhotoButtonId.setOnClickListener {
            picker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )

        }
    }

    private fun openImage(uri: Uri){
        val action = PlaceOrderFragmentDirections.actionPlaceOrderFragmentToImageFragment(uri)
        findNavController().navigate(action)
    }

    private fun observeUris(){
        orderViewModel.getPhotoState().observe(viewLifecycleOwner){
            uris ->
            imageAdapter.setItems(uris)
        }
    }

    private fun createOrder(order: Order){
        orderViewModel.addOrder(order)
    }





}