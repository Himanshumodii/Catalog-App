package com.project.productcatalogapp.presentation.productdetail

import android.os.Bundle
import com.project.productcatalogapp.R
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.gson.Gson
import com.project.productcatalogapp.domain.model.Product

class ProductDetailActivity : AppCompatActivity(){

    private lateinit var product: Product

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        val productJson = intent.getStringExtra("product")
        product = Gson().fromJson(productJson, Product::class.java)

        setupUI()
    }

    private fun setupUI() {

        findViewById<TextView>(R.id.title).text = product.title
        findViewById<TextView>(R.id.price).text = "$${product.price}"
        findViewById<TextView>(R.id.rating).text = "⭐ ${product.rating}"
        findViewById<TextView>(R.id.brand).text = product.brand
        findViewById<TextView>(R.id.category).text = product.category
        findViewById<TextView>(R.id.discount).text = "${product.discountPercentage}% OFF"
        findViewById<TextView>(R.id.stock).text = "Stock: ${product.stock}"
        findViewById<TextView>(R.id.description).text = product.description

        val slider = findViewById<ViewPager2>(R.id.imageSlider)
        slider.adapter = ImageSliderAdapter(product.images)

    }

}