package com.example.myapplication.profile.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.example.myapplication.authorization.ui.registration.RegistrationViewModel
import com.example.myapplication.databinding.FragmentChangeProfileBinding
import com.example.myapplication.profile.domain.model.UserSettingsModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChangeProfileFragment : Fragment() {
    private lateinit var binding: FragmentChangeProfileBinding
    private val viewModel: ProfileViewModel by viewModel()


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
        changeInfo()
        pickImage()
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
    }

    private fun changeInfo() {
        binding.saveButtonId.setOnClickListener {
            val email: String? = null
            val phoneNumber: String? = binding.phoneEditTextId.text.toString()
            val firstName: String? = binding.nameEditTextId.text.toString()
            val secondName: String? = binding.lastNameEditTextId.text.toString()
            val patronymic: String? = null
            val description: String? = binding.abouEditTextId.text.toString()
            viewModel.updateInfo(UserSettingsModel(
                email,
                phoneNumber,
                firstName,
                secondName,
                patronymic,
                description
            ))
        }
    }

    fun pickImage(){
        val picker = registerForActivityResult(
            ActivityResultContracts.PickVisualMedia()
        ){
            uris ->
            if(uris != null){
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