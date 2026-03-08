package com.project.productcatalogapp.presentation.productlist

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.gson.Gson
import com.project.productcatalogapp.R
import com.project.productcatalogapp.data.local.FavoriteManager
import com.project.productcatalogapp.presentation.favorites.FavoritesActivity
import com.project.productcatalogapp.presentation.productdetail.ProductDetailActivity

class ProductListActivity : AppCompatActivity(){

    private lateinit var viewModel: ProductViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var categoryRecyclerView: RecyclerView
    private lateinit var btnFavorite: Button
    private lateinit var progressBar: ProgressBar
    private lateinit var swipeRefresh: SwipeRefreshLayout


    private lateinit var adapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        recyclerView = findViewById(R.id.recyclerView)
        searchView = findViewById(R.id.searchView)
        categoryRecyclerView = findViewById(R.id.categoryRecyclerView)
        btnFavorite = findViewById(R.id.btnFavorite)
        progressBar = findViewById(R.id.progressBar)
        swipeRefresh = findViewById(R.id.swipeRefresh)

        swipeRefresh.setOnRefreshListener {

            viewModel.resetPagination()
            viewModel.fetchProducts()

        }

        btnFavorite.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }

        recyclerView.layoutManager = GridLayoutManager(this, 2)

        val favoriteManager = FavoriteManager(this)

        adapter = ProductAdapter(mutableListOf(), favoriteManager) { product ->

            val intent = Intent(this, ProductDetailActivity::class.java)
            intent.putExtra("product", Gson().toJson(product))
            startActivity(intent)
        }

      //  adapter.favoriteManager = favoriteManager

        recyclerView.adapter = adapter

        viewModel = ViewModelProvider(this)[ProductViewModel::class.java]

        observeProducts()

        setupSearch()

        setupCategories()

        setupPagination()

        progressBar.visibility = View.VISIBLE
        viewModel.fetchProducts()
        viewModel.fetchCategories()




        /*viewModel.productList.observe(this) { products ->
         //   recyclerView.adapter = ProductAdapter(products)
            recyclerView.adapter = ProductAdapter(products) { product ->

                val intent = Intent(this, ProductDetailActivity::class.java)
                intent.putExtra("product", Gson().toJson(product))
                startActivity(intent)

            }
        }
        viewModel.fetchProducts()

        setupSearch()

        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.categoryList.observe(this) { categories ->

            categoryRecyclerView.adapter =
                CategoryAdapter(categories) { category ->

                    viewModel.getProductsByCategory(category)

                }
        }

        viewModel.fetchCategories()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!viewModel.isLoading && lastVisibleItem >= totalItemCount - 2) {

                    viewModel.fetchProducts()

                }
            }
        })*/

    }
    private fun observeProducts() {

        viewModel.productList.observe(this) { products ->

            progressBar.visibility = View.GONE
            adapter.updateProducts(products)
            swipeRefresh.isRefreshing = false

        }
        viewModel.errorMessage.observe(this) {

            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()

        }
    }

    private fun setupCategories() {

        categoryRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        viewModel.categoryList.observe(this) { categories ->

            categoryRecyclerView.adapter =
                CategoryAdapter(categories) { category ->

                    viewModel.getProductsByCategory(category)

                }
        }
    }

    private fun setupPagination() {

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as GridLayoutManager

                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!viewModel.isLoading && lastVisibleItem >= totalItemCount - 2) {

                    viewModel.fetchProducts()

                }
            }
        })
    }

    private fun setupSearch() {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {

                query?.let {
                    viewModel.searchProducts(it)
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText.isNullOrEmpty()) {
                    viewModel.resetPagination()
                    viewModel.fetchProducts()
                }

                return true
            }
        })
    }

}