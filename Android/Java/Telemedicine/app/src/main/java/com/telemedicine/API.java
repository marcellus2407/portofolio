package com.telemedicine;

import com.telemedicine.model.AddToCartResponse;
import com.telemedicine.model.Cart;
import com.telemedicine.model.ChatResponse;
import com.telemedicine.model.CheckoutResponse;
import com.telemedicine.model.Dokter;
import com.telemedicine.model.LoginResponse;
import com.telemedicine.model.Message;
import com.telemedicine.model.Obat;
import com.telemedicine.model.Order;
import com.telemedicine.model.OrderDetail;
import com.telemedicine.model.RegisterResponse;
import com.telemedicine.model.RemoveFromCartResponse;
import com.telemedicine.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("name") String nama,
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") String role
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> register(
            @Field("name") String nama,
            @Field("email") String email,
            @Field("password") String password,
            @Field("role") String role,
            @Field("specialist") String spesialis
    );

    @GET("user.php")
    Call<User> getUser(
            @Query("id") int id
    );

    @GET("dokter.php")
    Call<List<Dokter>> getDokters();

    @GET("dokter.php")
    Call<List<Dokter>> searchDokters(
            @Query("keyword") String keyword
    );

    @GET("admin.php")
    Call<List<User>> getAdmins();

    @FormUrlEncoded
    @POST("send-chat.php")
    Call<ChatResponse> sendMessage(
            @Field("sender") int sender,
            @Field("receiver") int receiver,
            @Field("message") String message
    );

    @GET("retrieve-chat.php")
    Call<List<Message>> retrieveMessage(
            @Query("sender") int sender,
            @Query("receiver") int receiver
    );

    @GET("user-chat.php")
    Call<List<User>> getUserChat(
            @Query("id") int id
    );
    @GET("obat.php")
    Call<List<Obat>> getObats();

    @GET("obat.php")
    Call<List<Obat>> searchObats(
            @Query("keyword") String keyword
    );

    @FormUrlEncoded
    @POST("add-to-cart.php")
    Call<AddToCartResponse> addToCart(
            @Field("user") int user,
            @Field("obat") int obat,
            @Field("quantity") int quantity
    );

    @GET("cart.php")
    Call<List<Cart>> getCart(
            @Query("user") int user
    );

    @FormUrlEncoded
    @POST("remove-from-cart.php")
    Call<RemoveFromCartResponse> removeFromCart(
            @Field("user") int user,
            @Field("obat") int obat
    );

    @FormUrlEncoded
    @POST("checkout.php")
    Call<CheckoutResponse> checkout(
            @Field("user") int user,
            @Field("alamat") String alamat
    );

    @GET("order.php")
    Call<List<Order>> getOrders(
            @Query("user") int user
    );
    @GET("order-details.php")
    Call<List<OrderDetail>> getOrderDetails(
            @Query("id") int id
    );
}
