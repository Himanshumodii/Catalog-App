package com.project.productcatalogapp.domain.repository

import com.project.productcatalogapp.data.remote.ApiService
import com.project.productcatalogapp.data.remote.RetrofitClient
import com.project.productcatalogapp.domain.model.Product
import com.project.productcatalogapp.domain.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

class ProductRepository(private val apiService: ApiService) {

    suspend fun getProducts(): List<Product> {
        return apiService.getProducts().products
    }

    suspend fun searchProducts(query: String): List<Product> {
        return apiService.searchProducts(query).products
    }

    // NEW
    suspend fun getCategories(): List<String> {
        val categories = apiService.getCategories()
        return categories.map { it.slug }
    }

    // NEW
    suspend fun getProductsByCategory(category: String): List<Product> {
        return apiService.getProductsByCategory(category).products
    }

    suspend fun getProducts(limit: Int, skip: Int): List<Product> {
        return apiService.getProducts(limit, skip).products
    }

}