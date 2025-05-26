package com.example.myapplication.order.ui.placeOrder.fragments

import android.os.Bundle
import android.util.TypedValue
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.myapplication.CONST
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentExecutorDetailBinding
import com.example.myapplication.order.ui.placeOrder.OrderViewModel
import com.example.myapplication.profile.ui.ProfileInfoStateScreen
import com.example.myapplication.profile.ui.ProfileViewModel
import com.example.myapplication.profile.ui.UserSettingsModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.card.MaterialCardView
import org.koin.androidx.viewmodel.ext.android.viewModel


class ExecutorDetailFragment : Fragment() {
    private lateinit var binding: FragmentExecutorDetailBinding
    private var id = "-1"
    private var orderId = "-1"
    private val profileViewModel : ProfileViewModel by viewModel()
    private val orderViewModel : OrderViewModel by viewModel()
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<MaterialCardView>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = ExecutorDetailFragmentArgs.fromBundle(requireArguments())
        id = args.executorId
        orderId = args.orderId

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentExecutorDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileViewModel.loadInfoByExecutor(id)
        observerInfoByExecutor()
        setAssignButton()
        observeExecutorSetter()
        initBottomMenu()
    }

    private fun initBottomMenu(){
        val bottomSheetContainer = binding.orderBottomRating.bottomContainerId
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetContainer).apply {
            state = BottomSheetBehavior.STATE_COLLAPSED
            isHideable = false
            peekHeight = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                50f,
                resources.displayMetrics
            ).toInt()
        }
    }

    private fun observerInfoByExecutor(){
        profileViewModel.getExecutorInfoState().observe(viewLifecycleOwner){
            state ->
            when(state){
                is ProfileInfoStateScreen.Content -> {
                    val data = state.data
                    fillScreen(data)
                }

                is ProfileInfoStateScreen.Error -> {
                    Toast.makeText(
                        requireContext(),
                        state.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is ProfileInfoStateScreen.ConnectionFailed -> {
                    Toast.makeText(
                        requireContext(),
                        "Проблемы с интернетом..",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun fillScreen(userModel : UserSettingsModel) {
        binding.textRating.text = "⭐ ${userModel.rating ?: "5.00"} • 128 заказов"
        binding.textName.text = "${userModel.firstName} ${userModel.secondName}"
        binding.textDescription.text = userModel.description
        val avatarUrl = "${CONST.BASE_URL}image/show/${userModel.avatarPath}"
        Glide.with(requireContext())
            .load(avatarUrl)
            .placeholder(R.drawable.ic_account)
            .error(R.drawable.ic_account)
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.imageWorker)
    }

    private fun setExecutor(orderId : String, executorId : String){
        orderViewModel.setExecutor(orderId, executorId)
    }

    private fun observeExecutorSetter(){
        orderViewModel.getOrderStatus().observe(viewLifecycleOwner){
            state ->
            when(state){
                true -> {
                    showMessage("Вы назначили исполнителя")
                }

                false -> {
                    showMessage("Не удалось назначить исполнителя")
                }
            }
        }
    }

    private fun setAssignButton(){
        binding.buttonAssign.setOnClickListener {
            setExecutor(orderId, id)
        }
    }

    private fun showMessage(message: String){
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }


}