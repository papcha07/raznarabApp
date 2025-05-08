package com.example.myapplication.authorization.ui.registration

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.authorization.ui.model.Category
import com.example.myapplication.databinding.FragmentRegistrationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegistrationFragment : Fragment() {
    private lateinit var binding: FragmentRegistrationBinding
    private lateinit var spinner: Spinner
    private lateinit var adapter: CategoryAdapter
    private val registrationViewModel: RegistrationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter()
        chooseRoleVariant()
        registrationViewModel.getScreenState().observe(viewLifecycleOwner) { state ->
            when (state) {
                Category.Company -> {
                    binding.secondNameEditTextId.setHint("Название компании")
                }

                else -> {
                    binding.secondNameEditTextId.setHint("Инициалы")
                }
            }
        }
        registration()
        registrationObserver()
        goToLoginScreen()

    }

    private fun setAdapter() {
        spinner = binding.spinnerId
        val items = listOf(
            RoleCategory(Category.Nothing, "Выберите категорию"),
            RoleCategory(Category.Customer, "Заказчик"),
            RoleCategory(Category.Executor, "Частный исполнитель"),
            RoleCategory(Category.Company, "Компания")
        )
        val adapter = CategoryAdapter(requireContext(), items)
        spinner.adapter = adapter
    }

    private fun chooseRoleVariant() {
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val selectedItem = spinner.selectedItem
                if (selectedItem is RoleCategory) {
                    registrationViewModel.chooseRoleState(selectedItem.variant)

                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun registration() {
        binding.enterButtonId.setOnClickListener {
            when{
                !allFieldsFilled() -> {
                    showToast("Заполните все поля")
                }

                registrationViewModel.getScreenState().value == Category.Nothing -> {
                    showToast("Выберите катеорию исполняемой работы")
                }

                allFieldsFilled() && registrationViewModel.getScreenState().value != Category.Nothing -> {
                    registrationViewModel.registration(
                        binding.emailEditTextId.text.toString(),
                        binding.passwordEditText.text.toString(),
                        binding.firstNameEditTextId.text.toString(),
                        binding.secondNameEditTextId.text.toString(),
                        binding.patronymicEditTextId.text.toString(),
                        binding.phoneEditTextId.text.toString(),
                        registrationViewModel.getScreenState().value!!
                    )
                }
            }
        }
    }

    private fun registrationObserver(){
        registrationViewModel.getRegistrationScreenState().observe(viewLifecycleOwner){
            state ->
            when(state) {
                is RegistrationScreenState.Success -> {
                    showToast("Регистрация прошла успешно")
                }

                is RegistrationScreenState.AlreadyExist -> {
                    showToast("Такой аккаунт уже зарегистрирован")
                }

                is RegistrationScreenState.Error -> {
                    showToast("Ошибка регистрации")
                }
            }
        }
    }

    private fun allFieldsFilled() : Boolean{
        return listOf(
            binding.emailEditTextId.text,
            binding.phoneEditTextId.text,
            binding.passwordEditText.text,
            binding.secondNameEditTextId.text,
            binding.firstNameEditTextId.text,
            binding.patronymicEditTextId.text
        ).all {
            it?.isNotBlank() == true
        }
    }

    private fun showToast(message : String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun goToLoginScreen(){
        binding.noAccountButtonId.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }
}