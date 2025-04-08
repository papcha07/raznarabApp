package com.example.myapplication.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.profile.domain.model.UserSettingsModel
import com.example.myapplication.profile.ui.ProfileInfoStateScreen
import com.example.myapplication.profile.ui.ProfileViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeScreen()
        navigateToChangeScreen()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadInfo()
    }


    private fun observeScreen() {
        viewModel.getInfoState().observe(viewLifecycleOwner) { state ->

            when (state) {
                is ProfileInfoStateScreen.Content -> {
                    fillScreen(state.data)
                }

                is ProfileInfoStateScreen.Error -> {
                    networkErrorStub()
                }
            }
        }
    }

    private fun fillScreen(userModel: UserSettingsModel) {
        val fullname = "${userModel.firstName} ${userModel.secondName}"
        binding.workerNameId.text = fullname
        binding.phoneNumberId.text = userModel.phoneNumber
    }

    private fun networkErrorStub() {
        binding.workerNameId.text = "Имя Фамилия"
        binding.phoneNumberId.text = "+77777777777"
    }

    private fun navigateToChangeScreen(){
        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment2_to_changeProfileFragment)
        }
    }




}