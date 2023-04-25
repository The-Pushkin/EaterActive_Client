package com.example.eateractive_client.restaurant_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eateractive_client.R
import com.example.eateractive_client.cart.CartViewModel
import com.example.eateractive_client.cart.CartViewModelFactory
import com.example.eateractive_client.cart.cartDatabase
import com.example.eateractive_client.databinding.FragmentRestaurantMenuBinding

class RestaurantMenuFragment : Fragment() {
    private var _binding: FragmentRestaurantMenuBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartDatabase(requireActivity().applicationContext))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantMenuBinding.inflate(inflater, container, false)

        val items: MutableList<MenuItemModel> = mutableListOf(
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Pastrama de oaie pe ceapa", 42.5),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("McCrispy Chicken", 21.0),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Shaorma Hatz", 24.5),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Dublu Booster Nepicant", 18.3),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Cheesy Bacon Fries Burrito", 40.6),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Meniu Dublu Steakhouse", 49.8),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Doner Kebab curcan", 37.0),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Caramel Latte", 15.7),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Triple mini burger menu", 50.0),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Neumarkt Grenada Cinstita", 0.5),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Chicken-Flavoured Oil", 63.2),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Chocolate-Glazed Donut", 11.4),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Beef Wellington", 1024.2),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Piept de pui umplut cu cascaval, verdeata si usturoi", 26.7),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Frigarui de creveti gatiti in unt", 37.8),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Sarmale cu mamaliguta", 6969.6969),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Alegatura de porc la garnita cu ou", 32.14),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Choice Grade Ribeye Steak", 112.0),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Pranzul Haiducului", 43.7),
            MenuItemModel.Divider,
            MenuItemModel.MenuItem("Mici Cu Bere", 20.0),
            MenuItemModel.Divider,
        )

        binding.title.text = requireArguments().getString(KEY_ARG_RESTAURANT_NAME)

        val adapter = MenuItemListAdapter { menuItemEntity ->
            viewModel.addToCart(menuItemEntity)
        }
        adapter.submitList(items)
        binding.menuItemList.adapter = adapter
        binding.menuItemList.layoutManager = LinearLayoutManager(context)

        binding.checkoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_restaurantMenuFragment_to_checkoutFragment)
        }

        return binding.root
    }

    companion object {
        const val KEY_ARG_RESTAURANT_NAME = "restaurantName"
    }
}