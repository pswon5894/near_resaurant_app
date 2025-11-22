package com.cc.near_resaurant_app.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

data class PlacesResponse(
    val results: List<PlaceResult>
)

data class PlaceResult(
    val name: String?,
    val geometry: Geometry?
)

data class Geometry(
    val location: LocationData
)

data class LocationData(
    val lat: Double,
    val lng: Double
)

interface PlacesApiService {

    @GET("maps/api/place/nearbysearch/json")
    fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int = 1000,
        @Query("type") type: String = "restaurant",
        @Query("key") apiKey: String
    ): Call<PlacesResponse>

}
