package com.example.eateractive_client.restaurant_menu

import android.content.Context
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
import com.example.eateractive_client.databinding.FragmentCheckoutBinding
import com.example.eateractive_client.delivery.DeliveryFragment
import com.example.eateractive_client.server.ServerApi
import com.example.eateractive_client.server.ServerViewModel
import com.example.eateractive_client.server.models.OrderListModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CheckoutFragment : Fragment() {
    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CartViewModel by viewModels {
        CartViewModelFactory(cartDatabase(requireActivity().applicationContext))
    }

    private lateinit var adapter: MenuItemListAdapter
    private lateinit var serverApi: ServerApi

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

        val restaurantId = requireArguments().getInt(KEY_ARG_RESTAURANT_ID)
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val deliveryAddress = sharedPref.getString(getString(R.string.address_key), "nicaieri")

        adapter = MenuItemListAdapter { menuItemEntity ->
            viewModel.removeFromCart(menuItemEntity)
        }
        binding.menuItemList.adapter = adapter
        binding.menuItemList.layoutManager = LinearLayoutManager(context)

        serverApi = ServerViewModel.getInstance().create(ServerApi::class.java)

        binding.checkoutButton.setOnClickListener {
            val items = adapter.currentList.map {
                when (it) {
                    is MenuItemModel.MenuItem -> it.menuItemEntity.name
                    else -> "divider"
                }
            }.joinToString(separator = "\n")
            lifecycleScope.launch(Dispatchers.Main) {
                val orderId =
                    serverApi.postItems(
                        OrderListModel(
                            items,
                            restaurantId,
                            deliveryAddress ?: "nicaieri"
                        )
                    ).body()?.orderId ?: -1

                viewModel.emptyCart()

                findNavController().navigate(
                    R.id.action_checkoutFragment_to_deliveryFragment,
                    bundleOf(DeliveryFragment.KEY_ARG_ORDER_ID to orderId)
                )
            }
        }

        viewModel.menuItems.observe(viewLifecycleOwner) { menuItems ->
            val newList = menuItems.map {
                MenuItemModel.MenuItem(it)
            }
            adapter.submitList(newList)
        }
    }

    companion object {
        const val KEY_ARG_RESTAURANT_ID = "restaurantId"
    }
}