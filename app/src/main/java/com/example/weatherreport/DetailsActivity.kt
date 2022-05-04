package com.example.weatherreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.weatherreport.adapter.CityAdapter
import com.example.weatherreport.data.CityWeatherResult
import com.example.weatherreport.databinding.ActivityDetailsBinding
import com.example.weatherreport.network.CityWeatherAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.Math.round
import kotlin.math.roundToInt

class DetailsActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onResume() {
        super.onResume()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherService = retrofit.create(CityWeatherAPI::class.java)

        val call = weatherService.getCityWeather(
            intent.getStringExtra(CityAdapter.CITY_NAME).toString(),
            "metric",
            "22913aa8bedf2cb89b957109eeb763bc"
        )
        call.enqueue(object : Callback<CityWeatherResult> {
            override fun onResponse(
                call: Call<CityWeatherResult>,
                response: Response<CityWeatherResult>
            ) {
                val body = response.body()
                binding.tvName.text = body!!.name
                binding.tvTemp.text = body.main?.temp?.toFloat()!!.roundToInt().toString()
                binding.tvDesc.text = body.weather?.get(0)?.main.toString()
                binding.tvFeel.text = body.main.feels_like?.toFloat()!!.roundToInt().toString()
                binding.tvMin.text = body.main.temp_min?.toFloat()!!.roundToInt().toString()
                binding.tvMax.text = body.main.temp_max?.toFloat()!!.roundToInt().toString()
                binding.tvHumid.text = body.main.humidity?.toFloat()!!.roundToInt().toString()

                Glide.with(this@DetailsActivity)
                    .load(
                        ("https://openweathermap.org/img/w/" +
                                body.weather?.get(0)?.icon
                                + ".png")
                    )
                    .into(binding.iconWeather)
            }

            override fun onFailure(call: Call<CityWeatherResult>, t: Throwable) {
                binding.tvName.text = "Error: ${t.message}"
            }
        })
    }
}