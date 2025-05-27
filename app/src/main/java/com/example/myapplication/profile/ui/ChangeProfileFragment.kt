package com.example.myapplication.profile.ui

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.BuildConfig
import com.example.myapplication.CONST
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentChangeProfileBinding
import com.example.myapplication.profile.data.uriToFile
import com.example.myapplication.profile.domain.model.UserInfoRequest
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeProfileFragment : Fragment() {
    private lateinit var binding: FragmentChangeProfileBinding
    private val viewModel: ProfileViewModel by viewModel()
    private var currentUri: Uri ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        stateObserve()
        pickImage()
        changeInfo()

    }


    private fun stateObserve() {
        viewModel.getInfoState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is ProfileInfoStateScreen.Content -> {
                    fillProfile(state.data)
                }

                is ProfileInfoStateScreen.Error -> {
                    showMessage()
                }

                is ProfileInfoStateScreen.ConnectionFailed ->{
                    showMessage()
                }
            }
        }
    }

    private fun showMessage() {
        Toast.makeText(requireContext(), "Ошибка подключения к интернету", Toast.LENGTH_LONG).show()
    }



    private fun fillProfile(userModel: UserSettingsModel) {
        binding.nameEditTextId.setText(userModel.firstName)
        binding.lastNameEditTextId.setText(userModel.secondName)
        binding.phoneEditTextId.setText(userModel.phoneNumber)
        binding.abouEditTextId.setText(userModel.description)
        binding.ratingId.text = userModel.rating.toString()

        val avatarPath = userModel.avatarPath
        if(avatarPath != null){
            val avatarUrl = "${CONST.BASE_URL}image/show/$avatarPath"
            Glide.with(requireContext())
                .load(avatarUrl)
                .placeholder(R.drawable.ic_account)
                .error(R.drawable.ic_account)
                .dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.imageViewId)
        }

        else {
            binding.imageViewId.setImageResource(R.drawable.ic_account)
        }
    }

    private fun changeInfo() {
        binding.saveButtonId.setOnClickListener {
            val phoneNumber: String? = binding.phoneEditTextId.text.toString()
            val firstName: String? = binding.nameEditTextId.text.toString()
            val secondName: String? = binding.lastNameEditTextId.text.toString()
            val description: String? = binding.abouEditTextId.text.toString()
            viewModel.updateInfo(
                UserInfoRequest(
                    phoneNumber = phoneNumber,
                    firstName = firstName,
                    secondName = secondName,
                    description = description,
                    image = viewModel.uriState.value,
                )
            )
        }
    }

    fun pickImage(){
        val picker = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ){
            uris ->
            if(uris != null){
                viewModel.uriState.postValue(uris)
                binding.imageViewId.setImageURI(uris)
            }
        }

        binding.imageViewId.setOnClickListener {
            picker.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }

}