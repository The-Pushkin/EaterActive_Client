package com.example.eateractive_client

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eateractive_client.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        if (sharedPref != null) {
            if (!sharedPref.contains(getString(R.string.username_key)) ||
                !sharedPref.contains(getString(R.string.password_key)) ||
                !sharedPref.contains(getString(R.string.address_key))
            ) {
                findNavController().navigate(R.id.action_mainFragment_to_welcomeFragment)
            } else {
                findNavController().navigate(R.id.action_mainFragment_to_restaurantsFragment)
            }
        }
    }
}