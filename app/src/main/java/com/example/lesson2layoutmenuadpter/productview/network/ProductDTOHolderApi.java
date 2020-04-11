package com.example.lesson2layoutmenuadpter.productview.network;


import com.example.lesson2layoutmenuadpter.productview.dto.ProductDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductDTOHolderApi {
    @GET("products")
    public Call<List<ProductDTO>> getAllProducts();
}
