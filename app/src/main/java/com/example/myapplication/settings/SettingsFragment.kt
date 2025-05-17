package com.example.myapplication.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.App
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentSettingsBinding
import com.example.myapplication.profile.domain.model.UserSettingsModel
import com.example.myapplication.profile.ui.ProfileInfoStateScreen
import com.example.myapplication.profile.ui.ProfileViewModel
import com.example.myapplication.settings.domain.api.ThemeInteractor
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private val viewModel: ProfileViewModel by viewModel()
    private lateinit var switchMaterial: SwitchMaterial
    private val themeInteractor: ThemeInteractor by inject()

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
        switchTheme()
        showLogOutDialog()
        sendMessageToAdmin()
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

                is ProfileInfoStateScreen.ConnectionFailed -> {
                    networkErrorStub()
                    Toast.makeText(
                        requireContext(),
                        "Проблемы с подключением к интернету",
                        Toast.LENGTH_LONG
                    ).show()
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

    private fun navigateToChangeScreen() {
        binding.editButton.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment2_to_changeProfileFragment2)
        }

        binding.accountContainerId.setOnClickListener {
            findNavController().navigate(R.id.action_settingsFragment2_to_changeProfileFragment2)
        }
    }

    private fun switchTheme() {
        switchMaterial = binding.themeSwitcherId
        val currentTheme = themeInteractor.getTheme()
        switchMaterial.setChecked(currentTheme)

        switchMaterial.setOnCheckedChangeListener { compoundButton, checked ->
            (requireContext().applicationContext as App).switchTheme(checked)
        }
    }

    private fun logOut() {
//        findNavController().navigate(R.id.action_settingsFragment_to_loginFragment)
    }

    private fun showLogOutDialog() {
        binding.logoutContainerId.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(requireContext())
                .setTitle("Выйти?")
                .setMessage("Нужно будет заново авторизоваться в приложение")
                .setNegativeButton("Отмена") { dialog, which ->
                    dialog.dismiss()
                }
                .setPositiveButton("Да") { dialog, which ->
                    logOut()
                }
            dialog.show()
        }
    }

    private fun sendMessageToAdmin(){
        binding.aboutContainerId.setOnClickListener {
            viewModel.messageToAdmins()
        }
    }




}