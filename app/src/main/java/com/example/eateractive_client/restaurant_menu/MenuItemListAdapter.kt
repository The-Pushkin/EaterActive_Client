package com.example.eateractive_client.restaurant_menu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eateractive_client.R
import com.example.eateractive_client.cart.entity.MenuItemEntity
import com.example.eateractive_client.databinding.DividerRowBinding
import com.example.eateractive_client.databinding.MenuItemRowBinding

class MenuItemListAdapter(private val onClickCallback: (MenuItemEntity) -> Unit) :
    ListAdapter<MenuItemModel, MenuItemListAdapterViewHolder>(MenuItemModelCallback) {
    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is MenuItemModel.MenuItem -> MenuItemListAdapterViewHolder.MENU_ITEM
        is MenuItemModel.Divider -> MenuItemListAdapterViewHolder.DIVIDER_ITEM
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MenuItemListAdapterViewHolder = when (viewType) {
        MenuItemListAdapterViewHolder.MENU_ITEM -> {
            val binding = MenuItemRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MenuItemListAdapterViewHolder.MenuItemViewHolder(binding, onClickCallback)
        }
        MenuItemListAdapterViewHolder.DIVIDER_ITEM -> {
            val binding = DividerRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
            MenuItemListAdapterViewHolder.DividerViewHolder(binding)
        }
        else -> {
            error("ViewType $viewType is not supported.")
        }
    }

    override fun onBindViewHolder(holder: MenuItemListAdapterViewHolder, position: Int) =
        holder.bind(currentList[position])
}

sealed class MenuItemListAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: MenuItemModel)

    class MenuItemViewHolder(
        private val binding: MenuItemRowBinding,
        private val onClickCallback: (MenuItemEntity) -> Unit
    ) : MenuItemListAdapterViewHolder(binding.root) {
        override fun bind(item: MenuItemModel) {
            val menuItem = item as? MenuItemModel.MenuItem ?: return
            with(binding) {
                menuItemName.text = menuItem.menuItemEntity.name
                menuItemPrice.text =
                    itemView.context.getString(R.string.price_string, menuItem.menuItemEntity.price)
                root.setOnClickListener {
                    onClickCallback(menuItem.menuItemEntity)
                }
            }
        }
    }

    class DividerViewHolder(
        binding: DividerRowBinding
    ) : MenuItemListAdapterViewHolder(binding.root) {
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
                        newItem.menuItemEntity.name == oldItem.menuItemEntity.name &&
                        newItem.menuItemEntity.price == oldItem.menuItemEntity.price
            }
            is MenuItemModel.Divider -> {
                newItem is MenuItemModel.Divider
            }
        }

    override fun areContentsTheSame(oldItem: MenuItemModel, newItem: MenuItemModel): Boolean =
        oldItem == newItem
}