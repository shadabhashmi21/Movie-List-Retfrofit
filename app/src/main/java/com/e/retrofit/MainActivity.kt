package com.e.retrofit

import android.os.Bundle
import android.util.Log.d
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.e.models.ApiModel
import com.e.models.MovieModel
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    var loadMore : Boolean = true
    lateinit var gridLayoutManager: GridLayoutManager
    lateinit var apiService : APIService
    var movieList : MutableList<MovieModel> = mutableListOf()
    var movieAdapter = MovieAdapter(movieList)
    var apiPageNo = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        gridLayoutManager = GridLayoutManager(this@MainActivity, 2)
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
            layoutManager = gridLayoutManager
            adapter = movieAdapter
        }

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        apiService = retrofit.create(APIService::class.java)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView,
                                    dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemCount = gridLayoutManager.childCount
                val totalItemCount = gridLayoutManager.itemCount
                val pastVisibleItems = gridLayoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                    apiPageNo++
                    callApi()
                }
            }
        })

        callApi()

    }

    private fun callApi() {
        apiService.getMovieDetails(apiPageNo).enqueue(object : Callback<ApiModel> {
            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
                populateData(response.body()!!.results)
                loadMore = true;
            }

            override fun onFailure(call: Call<ApiModel>, t: Throwable) {
                d("Example", "onFailure -> ${t.localizedMessage}")
                loadMore = true;
            }
        })
    }

    private fun populateData(results: List<MovieModel>) {
        movieList.addAll(results)
        movieAdapter.notifyDataSetChanged()
    }
}