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
import com.example.eateractive_client.databinding.FragmentLoginBinding
import com.example.eateractive_client.server.ServerApi
import com.example.eateractive_client.server.ServerViewModel
import com.example.eateractive_client.server.models.LoginModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var serverApi: ServerApi

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        serverApi = ServerViewModel.getInstance().create(ServerApi::class.java)

        with(binding) {
            loginButton.setOnClickListener {
                if (usernameEditText.text.isNotEmpty() && passwordEditText.text.isNotEmpty()) {
                    lifecycleScope.launch(Dispatchers.IO) {
                        val loginModel = serverApi.login(
                            LoginModel(
                                usernameEditText.text.toString(),
                                passwordEditText.text.toString()
                            )
                        ).body()

                        val jwt = loginModel?.token
                        val address = loginModel?.address

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
                                putString(
                                    getString(R.string.address_key),
                                    address
                                )
                                apply()
                            }
                        }
                    }

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
                            apply()
                        }

                        findNavController().navigate(R.id.action_loginFragment_to_restaurantsFragment)
                    }
                }
            }
        }

        return binding.root
    }
}