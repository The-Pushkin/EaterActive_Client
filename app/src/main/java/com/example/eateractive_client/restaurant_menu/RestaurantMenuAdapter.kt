package com.example.eateractive_client.restaurant_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eateractive_client.R
import com.example.eateractive_client.databinding.DividerRowBinding
import com.example.eateractive_client.databinding.MenuItemRowBinding

class RestaurantMenuAdapter(private val onClickCallback: () -> Unit) :
    ListAdapter<MenuItemModel, RestaurantMenuAdapterViewHolder>(MenuItemModelCallback) {
    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MenuItemModel.MenuItem -> RestaurantMenuAdapterViewHolder.MENU_ITEM
        is MenuItemModel.Divider -> RestaurantMenuAdapterViewHolder.DIVIDER_ITEM
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantMenuAdapterViewHolder = when (viewType) {
        RestaurantMenuAdapterViewHolder.MENU_ITEM -> {
            val binding = MenuItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            RestaurantMenuAdapterViewHolder.MenuItemViewHolder(binding, onClickCallback)
        }
        RestaurantMenuAdapterViewHolder.DIVIDER_ITEM -> {
            val binding = DividerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            RestaurantMenuAdapterViewHolder.DividerViewHolder(binding)
        }
        else -> {
            error("ViewType $viewType is not supported.")
        }
    }

    override fun onBindViewHolder(holder: RestaurantMenuAdapterViewHolder, position: Int) =
        holder.bind(currentList[position])
}

sealed class RestaurantMenuAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: MenuItemModel)

    class MenuItemViewHolder(
        private val binding: MenuItemRowBinding,
        private val onClickCallback: () -> Unit
    ) : RestaurantMenuAdapterViewHolder(binding.root) {
        override fun bind(item: MenuItemModel) {
            val menuItem = item as? MenuItemModel.MenuItem ?: return
            with(binding) {
                menuItemName.text = menuItem.name
                menuItemPrice.text =
                    itemView.context.getString(R.string.price_string, menuItem.price)
                this.root.setOnClickListener { onClickCallback() }
            }
        }
    }

    class DividerViewHolder(
        binding: DividerRowBinding
    ) : RestaurantMenuAdapterViewHolder(binding.root) {
        override fun bind(item: MenuItemModel) = Unit
    }

    companion object {
        /** ViewType of [MenuItemModel.MenuItem]. */
        const val MENU_ITEM = 1

        /** ViewType of [MenuItemModel.Divider]. */
        const val DIVIDER_ITEM = 2
    }
}

object MenuItemModelCallback : DiffUtil.ItemCallback<MenuItemModel>() {
    override fun areItemsTheSame(oldItem: MenuItemModel, newItem: MenuItemModel): Boolean =
        when (oldItem) {
            is MenuItemModel.MenuItem -> {
                newItem is MenuItemModel.MenuItem &&
                        newItem.name == oldItem.name &&
                        newItem.price == oldItem.price
            }
            is MenuItemModel.Divider -> {
                newItem is MenuItemModel.Divider
            }
        }

    override fun areContentsTheSame(oldItem: MenuItemModel, newItem: MenuItemModel): Boolean =
        oldItem == newItem
}