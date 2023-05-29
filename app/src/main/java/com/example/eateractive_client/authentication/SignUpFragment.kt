package com.example.eateractive_client.authentication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.eateractive_client.R
import com.example.eateractive_client.databinding.FragmentSignUpBinding
import com.example.eateractive_client.server.ServerApi
import com.example.eateractive_client.server.ServerViewModel
import com.example.eateractive_client.server.models.SignupModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignUpFragment : Fragment() {
    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var serverApi: ServerApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)

        serverApi = ServerViewModel.getInstance().create(ServerApi::class.java)

        with(binding) {
            signUpButton.setOnClickListener {
                lifecycleScope.launch(Dispatchers.Main) {
                    val jwt = serverApi.signup(
                        SignupModel(
                            usernameEditText.text.toString(),
                            passwordEditText.text.toString(),
                            addressEditText.text.toString()
                        )
                    ).body()?.token

                    if (jwt == null) {
                        findNavController().popBackStack()
                    }

                    val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
                    if (sharedPref != null) {
                        with(sharedPref.edit()) {
                            putString(
                                getString(R.string.jwt_key),
                                jwt
                            )
                            apply()
                        }
                    }
                }

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