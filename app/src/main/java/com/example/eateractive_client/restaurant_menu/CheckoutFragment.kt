package com.example.eateractive_client.restaurant_menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eateractive_client.R
import com.example.eateractive_client.cart.CartViewModel
import com.example.eateractive_client.cart.CartViewModelFactory
import com.example.eateractive_client.cart.cartDatabase
import com.example.eateractive_client.databinding.FragmentCheckoutBinding

class CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartDatabase(requireActivity().applicationContext))
    }

    private lateinit var adapter: MenuItemListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.title.text = getString(R.string.cart)

        adapter = MenuItemListAdapter { menuItemEntity ->
            viewModel.removeFromCart(menuItemEntity)
        }
        binding.menuItemList.adapter = adapter
        binding.menuItemList.layoutManager = LinearLayoutManager(context)

        viewModel.menuItems.observe(viewLifecycleOwner) { menuItems ->
            val newList = menuItems.map {
                MenuItemModel.MenuItem(it)
            }
            adapter.submitList(newList)
        }
    }
}