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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val api = retrofit.create(APIService::class.java)

        api.getMovieDetails().enqueue(object : Callback<ApiModel> {
            override fun onResponse(call: Call<ApiModel>, response: Response<ApiModel>) {
                showData(response.body()!!.results)
            }

            override fun onFailure(call: Call<ApiModel>, t: Throwable) {
                d("Example", "onFailure -> ${t.localizedMessage}")
            }
        })
    }

    private fun showData(results: List<MovieModel>) {
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = MovieAdapter(results)
        }
    }
}