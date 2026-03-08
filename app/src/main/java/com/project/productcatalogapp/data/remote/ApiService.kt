package com.project.productcatalogapp.data.remote

import com.project.productcatalogapp.domain.model.Category
import com.project.productcatalogapp.domain.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.Locale

interface ApiService {

    @GET("products")
    suspend fun getProducts(): ProductResponse

    @GET("products/search")
    suspend fun searchProducts(
        @Query("q") query: String
    ): ProductResponse

    // NEW
    @GET("products/categories")
    suspend fun getCategories(): List<Category>

    // NEW
    @GET("products/category/{category}")
    suspend fun getProductsByCategory(
        @Path("category") category: String
    ): ProductResponse

    @GET("products")
    suspend fun getProducts(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int
    ): ProductResponse

}