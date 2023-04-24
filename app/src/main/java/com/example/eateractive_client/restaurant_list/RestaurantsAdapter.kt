package com.example.eateractive_client.restaurant_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.eateractive_client.databinding.DividerRowBinding
import com.example.eateractive_client.databinding.RestaurantRowBinding

class RestaurantsAdapter(private val onClickCallback: () -> Unit) :
    ListAdapter<RestaurantModel, RestaurantsAdapterViewHolder>(RestaurantModelCallback) {
    override fun getItemViewType(position: Int): Int = when (getItem(position)) {
        is RestaurantModel.Restaurant -> RestaurantsAdapterViewHolder.RESTAURANT_ITEM
        is RestaurantModel.Divider -> RestaurantsAdapterViewHolder.DIVIDER_ITEM
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RestaurantsAdapterViewHolder =
        when (viewType) {
            RestaurantsAdapterViewHolder.RESTAURANT_ITEM -> {
                val binding = RestaurantRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RestaurantsAdapterViewHolder.RestaurantViewHolder(binding, onClickCallback)
            }
            RestaurantsAdapterViewHolder.DIVIDER_ITEM -> {
                val binding = DividerRowBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                RestaurantsAdapterViewHolder.DividerViewHolder(binding)
            }
            else -> {
                error("ViewType $viewType is not supported.")
            }
        }

    override fun onBindViewHolder(holder: RestaurantsAdapterViewHolder, position: Int) =
        holder.bind(currentList[position])
}

sealed class RestaurantsAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: RestaurantModel)
    class RestaurantViewHolder(
        private val binding: RestaurantRowBinding,
        private val onClickCallback: () -> Unit
    ) : RestaurantsAdapterViewHolder(binding.root) {
        override fun bind(item: RestaurantModel) {
            val restaurantItem = item as? RestaurantModel.Restaurant ?: return
            with(binding) {
                restaurantName.text = restaurantItem.name
                restaurantName.setOnClickListener { onClickCallback() }
            }
        }
    }

    class DividerViewHolder(
        binding: DividerRowBinding
    ) : RestaurantsAdapterViewHolder(binding.root) {
        override fun bind(item: RestaurantModel) = Unit
    }

    companion object {
        /** ViewType of [RestaurantModel.Restaurant]. */
        const val RESTAURANT_ITEM = 1

        /** ViewType of [RestaurantModel.Divider]. */
        const val DIVIDER_ITEM = 2
    }
}

object RestaurantModelCallback : DiffUtil.ItemCallback<RestaurantModel>() {
    override fun areItemsTheSame(oldItem: RestaurantModel, newItem: RestaurantModel): Boolean =
        when (oldItem) {
            is RestaurantModel.Restaurant -> {
                newItem is RestaurantModel.Restaurant &&
                        newItem.name == oldItem.name
            }
            is RestaurantModel.Divider -> {
                newItem is RestaurantModel.Divider
            }
        }

    override fun areContentsTheSame(oldItem: RestaurantModel, newItem: RestaurantModel): Boolean =
        oldItem == newItem
}