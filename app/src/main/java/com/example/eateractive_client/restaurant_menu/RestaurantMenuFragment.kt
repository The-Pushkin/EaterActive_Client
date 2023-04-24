package com.example.eateractive_client.restaurant_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.eateractive_client.databinding.FragmentRestaurantMenuBinding

class RestaurantMenuFragment : Fragment() {
    private var _binding: FragmentRestaurantMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantMenuBinding.inflate(inflater, container, false)
        return binding.root
    }
}