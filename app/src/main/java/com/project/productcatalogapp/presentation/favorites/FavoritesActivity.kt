package com.project.productcatalogapp.presentation.favorites

import android.annotation.SuppressLint
import android.content.Intent
import com.project.productcatalogapp.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.project.productcatalogapp.data.local.FavoriteManager
import com.project.productcatalogapp.presentation.productdetail.ProductDetailActivity
import com.project.productcatalogapp.presentation.productlist.ProductAdapter

class FavoritesActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteManager: FavoriteManager

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorites)

        recyclerView = findViewById(R.id.favoriteRecyclerView)

        favoriteManager = FavoriteManager(this)

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val favorites = favoriteManager.getFavorites()

    //    recyclerView.adapter = ProductAdapter(favorites) {}
        recyclerView.adapter = ProductAdapter(
            favorites.toMutableList(),
            favoriteManager
        ) { product ->

            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", Gson().toJson(product))
            startActivity(intent)
        }
    }
}