package com.project.productcatalogapp.presentation.productlist

import android.content.Context
import android.util.Log
import com.project.productcatalogapp.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.project.productcatalogapp.data.local.FavoriteManager
import com.project.productcatalogapp.domain.model.Product

class ProductAdapter(
    private val products: MutableList<Product>,
    private val favoriteManager: FavoriteManager,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

 //  private var favoriteManager: FavoriteManager? = null

    fun addProducts(newProducts: List<Product>) {
        val start = products.size
        products.addAll(newProducts)
        notifyItemRangeInserted(start, newProducts.size)
    }

    class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val image: ImageView = view.findViewById(R.id.productImage)
        val title: TextView = view.findViewById(R.id.productTitle)
        val price: TextView = view.findViewById(R.id.productPrice)
        val rating: TextView = view.findViewById(R.id.productRating)
        val favoriteIcon: ImageView = view.findViewById(R.id.imgFavorite)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_product, parent, false)

        return ProductViewHolder(view)
    }


    override fun getItemCount(): Int = products.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        val product = products[position]

        val isFav = favoriteManager.isFavorite(product.id)

        Log.d("ProductAdapter", "Binding product: $product")

        holder.favoriteIcon.setImageResource(
            if (isFav) R.drawable.ic_favorite else R.drawable.ic_favorite_border
        )

        holder.favoriteIcon.setOnClickListener {

            if (favoriteManager.isFavorite(product.id)) {

                favoriteManager.removeFavorite(product)

                holder.favoriteIcon.setImageResource(R.drawable.ic_favorite_border)

            } else {

                favoriteManager.addFavorite(product)

                holder.favoriteIcon.setImageResource(R.drawable.ic_favorite)
            }
        }


        holder.title.text = product.title
        holder.price.text = "$${product.price}"
        holder.rating.text = "⭐ ${product.rating}"

        Glide.with(holder.itemView.context)
            .load(product.thumbnail)
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }

    }
    fun updateProducts(newProducts: List<Product>) {

        products.clear()
        products.addAll(newProducts)
        notifyDataSetChanged()

    }


}