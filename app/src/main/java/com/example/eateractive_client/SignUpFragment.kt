package com.example.eateractive_client

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.eateractive_client.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        with(binding) {
            signUpButton.setOnClickListener {
                if (usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString(
                                getString(R.string.username_key),
                                usernameEditText.text.toString()
                            )
                            putString(
                                getString(R.string.password_key),
                                passwordEditText.text.toString()
                            )
                            putString(
                                getString(R.string.address_key),
                                addressEditText.text.toString()
                            )
                            apply()
                        }

                        findNavController().navigate(R.id.action_signUpFragment_to_restaurantsFragment)
                    }
                }
            }
        }

        return binding.root
    }
}