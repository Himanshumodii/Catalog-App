package com.project.productcatalogapp.presentation.productlist

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.productcatalogapp.data.remote.RetrofitClient
import com.project.productcatalogapp.domain.model.Product
import com.project.productcatalogapp.domain.repository.ProductRepository
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {

  //  private val repository = ProductRepository()

    private val repository = ProductRepository(RetrofitClient.api)

    val productList = MutableLiveData<List<Product>>()
    val categoryList = MutableLiveData<List<String>>() // NEW

    private val currentList = mutableListOf<Product>()

    val loadingState = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()

    private var skip = 0
    private val limit = 10
    var isLoading = false

    // Pagination API
    fun fetchProducts() {

        if (isLoading) return

        viewModelScope.launch {

            try {

                loadingState.value = true
                isLoading = true

                val products = repository.getProducts(limit, skip)

                currentList.addAll(products)
                productList.value = currentList

                skip += limit

            } catch (e: Exception) {

                errorMessage.value = e.message ?: "Something went wrong"

            } finally {

                loadingState.value = false
                isLoading = false
            }
        }
    }

   /* fun fetchProducts() {
        viewModelScope.launch {
            try {
              //  val products = repository.getProducts()
                productList.value = repository.getProducts()
                Log.d("API_DATA", "Products size: ${repository.getProducts().size}")
              //  productList.postValue(products)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("API_ERROR", e.message.toString())
            }
        }
    }*/

    fun searchProducts(query: String) {

        viewModelScope.launch {

            try {

                loadingState.value = true

                val products = repository.searchProducts(query)

                currentList.clear()
                currentList.addAll(products)

                productList.value = currentList

            } catch (e: Exception) {

                errorMessage.value = e.message ?: "Search failed"

            } finally {

                loadingState.value = false
            }
        }
    }

    // NEW
    fun fetchCategories() {

        viewModelScope.launch {

            try {

                loadingState.value = true

                val categories = repository.getCategories().toMutableList()
                categories.add(0, "All")

                categoryList.value = categories

            } catch (e: Exception) {

                errorMessage.value = "Failed to load categories"

            } finally {

                loadingState.value = false
            }
        }
    }

    // NEW
    fun getProductsByCategory(category: String) {

        viewModelScope.launch {

            try {

                loadingState.value = true

                if (category == "All") {

                    resetPagination()
                    fetchProducts()

                } else {

                    val products = repository.getProductsByCategory(category)

                    currentList.clear()
                    currentList.addAll(products)

                    productList.value = currentList
                }

            } catch (e: Exception) {

                errorMessage.value = "Failed to load category products"

            } finally {

                loadingState.value = false
            }
        }
    }

    public fun resetPagination() {

        skip = 0
        currentList.clear()
    }



}