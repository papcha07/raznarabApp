package com.example.myapplication.authorization.ui.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        login()
        loginObservable()

    }


    private fun allFilled(): Boolean {
        return listOf(
            binding.emailEditTextId.text.toString(),
            binding.passwordEditText.text.toString()
        ).all {
            it.isNotBlank()
        }
    }

    private fun login() {
        binding.enterButtonId.setOnClickListener {

            when {
                allFilled() -> {
                    val email = binding.emailEditTextId.text.toString()
                    val password = binding.passwordEditText.text.toString()
                    loginViewModel.login(email, password)
                    findNavController().navigate(R.id.action_loginFragment_to_placeOrderFragment)
                }

                else -> {
                    showMessage("Заполните все поля")
                }
            }
        }
    }

    private fun loginObservable() {
        loginViewModel.getLoginState().observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginScreenState.Success -> {
                    showMessage(state.token)
                    Log.d("TOKEN_TOKEN", state.token )
                }

                is LoginScreenState.Error ->
                {
                    showMessage(state.message)
                }
            }
        }
    }

    private fun showMessage(message : String){
        binding.errorMessageTextViewId.setText(message)
        binding.errorMessageTextViewId.visibility = View.VISIBLE
    }


}