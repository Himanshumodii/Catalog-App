package com.project.productcatalogapp.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.project.productcatalogapp.domain.model.Product

class FavoriteManager(context: Context) {

    private val prefs = context.getSharedPreferences("favorites", Context.MODE_PRIVATE)
    private val gson = Gson()

    private val KEY_FAVORITES = "favorite_products"

    fun getFavorites(): MutableList<Product> {

        val json = prefs.getString(KEY_FAVORITES, null) ?: return mutableListOf()

        val type = object : TypeToken<MutableList<Product>>() {}.type

        return gson.fromJson(json, type)
    }

    fun saveFavorites(products: List<Product>) {

        val json = gson.toJson(products)

        prefs.edit().putString(KEY_FAVORITES, json).apply()
    }

    fun addFavorite(product: Product) {

        val favorites = getFavorites()

        if (favorites.none { it.id == product.id }) {

            favorites.add(product)

            saveFavorites(favorites)
        }
    }

    fun removeFavorite(product: Product) {

        val favorites = getFavorites()

        favorites.removeAll { it.id == product.id }

        saveFavorites(favorites)
    }

    fun isFavorite(productId: Int): Boolean {

        return getFavorites().any { it.id == productId }
    }
}