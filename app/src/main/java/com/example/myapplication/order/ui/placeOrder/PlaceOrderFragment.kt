package com.example.myapplication.order.ui.placeOrder

import android.content.Context
import android.content.DialogInterface
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
import androidx.appcompat.content.res.AppCompatResources
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentPlaceOrderBinding
import com.example.myapplication.order.data.network.RetrofitClient
import com.example.myapplication.order.domain.models.Place
import com.example.myapplication.order.domain.models.Profession
import com.example.myapplication.order.domain.models.Order
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
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
        observerOrderPlaceState()
        binding.placeOrderButtonId.setOnClickListener {
            when(checkAllFields()){
                true -> {
                    val order = Order(
                        title = binding.titleEditTextId.text.toString(),
                        description = binding.descriptionEditTextId.text.toString(),
                        lat = address!!.lat.toDouble(),
                        lon = address!!.lon.toDouble(),
                        address = address!!.address,
                        price = binding.priceEdiTextId.text.toString().toDouble(),
                        imagesFiles = imageAdapter.getUriList(),
                        professionId = professionId
                    )
                    orderViewModel.placeOrder(order)
                }

                false -> {
                    Toast.makeText(
                        requireContext(),
                        "Заполните все поля и прикрепите фотографии работ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

        }
    }

    private fun pickImage() {
        binding.addPhotoButtonId.setOnClickListener {
            picker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

    private fun setUpImageAdapter() {
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

    private fun setUpPlaceAdapter() {
        placeAdapter = PlaceAdapter(
            context = requireContext(),
            places = mutableListOf()
        ) { place ->
            binding.addressEditTextId.setText(place.address)
            binding.addressEditTextId.clearFocus()
            hideKeyboard()
            address = place
        }

        binding.addressEditTextId.setAdapter(placeAdapter)
    }

    private fun placeTextWatcherObserever() {
        binding.addressEditTextId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                orderViewModel.loadAddressList(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun observeAddressList() {
        orderViewModel.getAddressState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is AddressState.Content -> {
                    updatePlaceAdapter(state.data)
                }

                else -> {}
            }
        }
    }

    private fun updatePlaceAdapter(addressList: List<Place>) {
        placeAdapter.clear()
        placeAdapter.addAll(addressList)
        placeAdapter.notifyDataSetChanged()
        binding.addressEditTextId.showDropDown()
    }

    private fun hideKeyboard() {
        val imm =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.addressEditTextId.windowToken, 0)
    }

    private fun setUpProfessionAdapter() {
        professionAdapter = ProfessionAdapter(
            context = requireContext(),
            professions = mutableListOf()
        ) { click ->
            professionId = click.id
            binding.autoCompleteTextViewId.setText(click.name)
        }

        binding.autoCompleteTextViewId.setAdapter(professionAdapter)
    }

    private fun observerProfessionList() {
        orderViewModel.getProfState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfessionState.Content -> {
                    professionAdapter.setList(state.content)
                }

                else -> {}
            }
        }
    }

    private fun observerOrderPlaceState() {
        orderViewModel.getPlaceOrderState().observe(viewLifecycleOwner) { state ->
            when (state) {
                true -> {
                    placedMessage(getString(R.string.successOrderMessage))
                    clearDetails()
                    orderViewModel.clearObserve()
                }

                false -> {
                    placedMessage(getString(R.string.failedOrderMessage))
                    orderViewModel.clearObserve()
                }

                null -> {
                    return@observe
                }
            }
        }
    }

    private fun placedMessage(title: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(title)
            .setNeutralButton("ОК", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                }
            })
            .show()
    }

    private fun checkAllFields(): Boolean {
        val fields = listOf(
            binding.titleEditTextId.text.toString(),
            binding.priceEdiTextId.text.toString(),
            binding.descriptionEditTextId.text.toString()
        ).all {
            it.isNotEmpty()
        }
        if (fields && professionId.isNotEmpty() && address != null && orderViewModel.getPhotoState().value?.size != 0) {
            return true
        } else {
            return false
        }
    }

    private fun clearDetails(){
        binding.titleEditTextId.text?.clear()
        binding.priceEdiTextId.text?.clear()
        binding.addressEditTextId.text?.clear()
        binding.descriptionEditTextId.text?.clear()
        binding.autoCompleteTextViewId.text?.clear()
        imageAdapter.setItems(emptyList())
        address = null
        professionId = ""
    }


}