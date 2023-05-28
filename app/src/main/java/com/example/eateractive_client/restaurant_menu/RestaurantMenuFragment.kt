package com.example.eateractive_client.restaurant_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eateractive_client.R
import com.example.eateractive_client.cart.CartViewModel
import com.example.eateractive_client.cart.CartViewModelFactory
import com.example.eateractive_client.cart.cartDatabase
import com.example.eateractive_client.databinding.FragmentRestaurantMenuBinding
import com.example.eateractive_client.server.ServerApi
import com.example.eateractive_client.server.ServerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RestaurantMenuFragment : Fragment() {
    private var _binding: FragmentRestaurantMenuBinding? = null
    private val binding get() = _binding!!
    private lateinit var serverApi: ServerApi
    private lateinit var adapter: MenuItemListAdapter

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartDatabase(requireActivity().applicationContext))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRestaurantMenuBinding.inflate(inflater, container, false)

        val restaurantName = requireArguments().getString(KEY_ARG_RESTAURANT_NAME)
        val restaurantId = requireArguments().getInt(KEY_ARG_RESTAURANT_ID)

        binding.title.text = restaurantName

        adapter = MenuItemListAdapter { menuItemEntity ->
            viewModel.addToCart(menuItemEntity)
        }
        adapter.submitList(emptyList())
        binding.menuItemList.adapter = adapter
        binding.menuItemList.layoutManager = LinearLayoutManager(context)

        serverApi = ServerViewModel.getInstance().create(ServerApi::class.java)
        lifecycleScope.launch(Dispatchers.IO) {
            val items =
                serverApi.getMenu(restaurantId).body()
                    ?.map { MenuItemModel.MenuItem(it.id, it.name, it.price) }

            adapter.submitList(items)
        }

        binding.checkoutButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_restaurantMenuFragment_to_checkoutFragment,
                bundleOf(CheckoutFragment.KEY_ARG_RESTAURANT_ID to restaurantId)
            )
        }

        return binding.root
    }

    companion object {
        const val KEY_ARG_RESTAURANT_NAME = "restaurantName"
        const val KEY_ARG_RESTAURANT_ID = "restaurantId"
    }
}