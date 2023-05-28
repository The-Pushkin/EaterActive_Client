package com.example.eateractive_client.restaurant_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eateractive_client.R
import com.example.eateractive_client.databinding.FragmentRestaurantsBinding
import com.example.eateractive_client.server.ServerApi
import com.example.eateractive_client.server.ServerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantsFragment : Fragment() {
    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!
    private lateinit var serverApi: ServerApi
    private lateinit var adapter: RestaurantsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)

        adapter = RestaurantsAdapter { bundle ->
            findNavController().navigate(
                R.id.action_restaurantsFragment_to_restaurantMenuFragment,
                bundle
            )
        }
        adapter.submitList(emptyList())
        binding.restaurantList.adapter = adapter
        binding.restaurantList.layoutManager = LinearLayoutManager(context)

        serverApi = ServerViewModel.getInstance().create(ServerApi::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            val items =
                serverApi.getRestaurants().body()
                    ?.map { RestaurantModel.Restaurant(it.id, it.name) }
            adapter.submitList(items)
        }

        return binding.root
    }
}