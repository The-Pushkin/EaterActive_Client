package com.example.eateractive_client.server

import com.example.eateractive_client.server.models.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ServerApi {
    @POST("client/signup")
    suspend fun signup(@Body signupModel: SignupModel): Response<JwtModel>

    @POST("client/login")
    suspend fun login(@Body loginModel: LoginModel): Response<JwtAddressModel>

    @GET("restaurants")
    suspend fun getRestaurants(): Response<List<RestaurantModel>>

    @GET("menu/{restaurant_id}")
    suspend fun getMenu(
        @Path("restaurant_id") restaurantId: Int
    ): Response<List<MenuItemModel>>

    @POST("orders/create")
    suspend fun postItems(@Body items: OrderListModel): Response<OrderIdModel>

    @GET("orders/{order_id}")
    suspend fun getOrderStatus(
        @Path("order_id") orderId: Int
    ): Response<OrderStatusModel>

    @POST("orders/confirm_delivery/{order_id}")
    suspend fun confirmDelivery(
        @Path("order_id") orderId: Int
    ): Response<Void>
}