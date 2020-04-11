package com.example.lesson2layoutmenuadpter.productview.network;


import com.example.lesson2layoutmenuadpter.productview.dto.ProductCreateDTO;
import com.example.lesson2layoutmenuadpter.productview.dto.ProductCreateSuccessDTO;
import com.example.lesson2layoutmenuadpter.productview.dto.ProductDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProductDTOHolderApi {
    @GET("products")
    public Call<List<ProductDTO>> getAllProducts();

    @POST("products/create")
    public Call<ProductCreateSuccessDTO> CreateRequest(@Body ProductCreateDTO product);

}
