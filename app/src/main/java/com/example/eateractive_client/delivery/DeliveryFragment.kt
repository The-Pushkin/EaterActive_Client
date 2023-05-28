package com.example.eateractive_client.delivery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.eateractive_client.databinding.FragmentDeliveryBinding
import com.example.eateractive_client.server.ServerApi
import com.example.eateractive_client.server.ServerViewModel

class DeliveryFragment : Fragment() {
    private var _binding: FragmentDeliveryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: DeliveryViewModel by viewModels {
        DeliveryViewModelFactory(
            ServerViewModel.getInstance().create(ServerApi::class.java),
            requireArguments().getInt(KEY_ARG_ORDER_ID)
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.confirmButton.setOnClickListener {
            viewModel.confirmDelivery()

            val navController = findNavController()
            navController.popBackStack()
            navController.popBackStack()
            navController.popBackStack()
        }

        viewModel.orderStatus.observe(viewLifecycleOwner) { status ->
            binding.orderStatus.text = status
        }
    }

    companion object {
        const val KEY_ARG_ORDER_ID = "orderId"
    }
}