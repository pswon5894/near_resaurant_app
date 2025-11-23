package com.cc.near_restaurant_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cc.near_restaurant_app.databinding.ItemRestaurantBinding // 실제 바인딩 클래스로 변경
import com.google.android.gms.maps.model.LatLng

class RestaurantAdapter(private val restaurants: List<Restaurant>) :
RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>(){

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(restaurant: Restaurant) {
            binding.tvRestaurantsName.text = restaurant.name
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        // R.layout.item_restaurant 대신 바인딩 클래스 사용
        val binding = ItemRestaurantBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position])
    }

    override fun getItemCount(): Int = restaurants.size
}