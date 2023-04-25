package com.example.eateractive_client.restaurant_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eateractive_client.R
import com.example.eateractive_client.databinding.FragmentRestaurantsBinding

class RestaurantsFragment : Fragment() {
    private var _binding: FragmentRestaurantsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantsBinding.inflate(inflater, container, false)

        val items: MutableList<RestaurantModel> = mutableListOf(
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Cocosu' Rosu"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Taco Bell"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Burger King"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Una Mica La Georgica"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Starbucks"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("McDonald's"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("KFC"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Mesopotamia"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Shaormeria Baneasa"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("BO$$ BURGERS"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Popeye's Chicken"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Dunkin' Donuts"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Hell Kitchen"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("Garden Pub"),
            RestaurantModel.Divider,
            RestaurantModel.Restaurant("The Place"),
            RestaurantModel.Divider,
        )

        val adapter = RestaurantsAdapter { bundle ->
            findNavController().navigate(
                R.id.action_restaurantsFragment_to_restaurantMenuFragment,
                bundle
            )
        }
        adapter.submitList(items)
        binding.restaurantList.adapter = adapter
        binding.restaurantList.layoutManager = LinearLayoutManager(context)

        return binding.root
    }
}