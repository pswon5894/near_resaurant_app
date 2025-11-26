package com.cc.near_restaurant_app

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cc.near_restaurant_app.databinding.ItemRestaurantBinding
import com.bumptech.glide.Glide
import com.cc.near_restaurant_app.data.Restaurant
import com.cc.near_restaurant_app.retrofit.RetrofitClient
import java.util.Locale

class RestaurantAdapter(
    private val restaurants: List<Restaurant>
) : RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private var selectedPosition: Int = -1
    private var itemClickListener: ((Int) -> Unit)? = null

    inner class RestaurantViewHolder(private val binding: ItemRestaurantBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(restaurant: Restaurant, position: Int) {
            binding.tvRestaurantName.text = restaurant.name
            binding.tvRestaurantAddress.text = restaurant.address
            binding.tvRating.text = restaurant.rating?.let { "평점 %.1f".format(it) } ?: "평점 없음"
            binding.tvFoodType.text = restaurant.types?.let { formatTypes(it) } ?: "타입 없음"

            val photoUrl = restaurant.photoReference?.let { RetrofitClient.getPhotoUrl(it, 400, 400) }
            if (photoUrl != null) {
                Glide.with(binding.root.context).load(photoUrl).placeholder(android.R.drawable.ic_menu_rotate)
                    .error(android.R.drawable.ic_menu_report_image).into(binding.ivRestaurantPhoto)
            } else binding.ivRestaurantPhoto.setImageResource(android.R.drawable.ic_menu_camera)

            binding.root.setBackgroundResource(
                if (position == selectedPosition) android.R.color.holo_blue_light
                else android.R.color.transparent
            )

            binding.root.setOnClickListener { itemClickListener?.invoke(position) }
        }

        private fun formatTypes(types: List<String>): String {
            val typeMap = mapOf(
                "bakery" to "빵집", "cafe" to "카페", "bar" to "술집/바",
                "korean_restaurant" to "한식", "japanese_restaurant" to "일식",
                "chinese_restaurant" to "중식", "thai_restaurant" to "태국 요리",
                "indian_restaurant" to "인도 요리", "italian_restaurant" to "이탈리아 요리",
                "american_restaurant" to "양식", "mexican_restaurant" to "멕시코 요리",
                "seafood_restaurant" to "해산물", "pizza_restaurant" to "피자",
                "steak_house" to "스테이크", "burger_joint" to "햄버거",
                "dessert_shop" to "디저트", "fast_food_restaurant" to "패스트푸드",
                "sushi_restaurant" to "초밥", "ramen_restaurant" to "라멘"
            )

            val irrelevantTypes = listOf(
                "point_of_interest", "establishment", "food", "store",
                "meal_takeaway", "meal_delivery", "restaurant"
            )

            return types.filter { !irrelevantTypes.contains(it) }
                .map { typeMap[it] ?: it.replace("_", " ").replaceFirstChar { c -> c.titlecase(Locale.getDefault()) } }
                .distinct().take(3).joinToString(" / ")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val binding = ItemRestaurantBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RestaurantViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bind(restaurants[position], position)
    }

    override fun getItemCount(): Int = restaurants.size

    fun setSelectedPosition(position: Int) {
        val previous = selectedPosition
        selectedPosition = position
        if (previous != -1) notifyItemChanged(previous)
        notifyItemChanged(selectedPosition)
    }

    fun setOnItemClickListener(listener: (Int) -> Unit) {
        itemClickListener = listener
    }
}
