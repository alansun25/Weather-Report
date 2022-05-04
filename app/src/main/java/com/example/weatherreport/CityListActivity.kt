package com.example.weatherreport

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import com.example.weatherreport.adapter.CityAdapter
import com.example.weatherreport.databinding.ActivityCityListBinding
import com.example.weatherreport.dialog.CityDialog
import com.example.weatherreport.network.CityWeatherAPI
import com.example.weatherreport.touch.CityRecyclerTouchCallback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CityListActivity : AppCompatActivity(), CityDialog.CityHandler {

    lateinit var binding: ActivityCityListBinding
    private lateinit var adapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title

        binding.fab.setOnClickListener { view ->
            CityDialog().show(supportFragmentManager, "City_DIALOG")
        }

        initRecyclerView()
    }

    private fun initRecyclerView() {
        adapter = CityAdapter(this)
        binding.recyclerItems.adapter = adapter

        val touchCallbackList = CityRecyclerTouchCallback(adapter)
        val itemTouchHelper = ItemTouchHelper(touchCallbackList)
        itemTouchHelper.attachToRecyclerView(binding.recyclerItems)
    }

    override fun cityAdded(city: City) {
        adapter.addCity(city)
        adapter.submitList(adapter.cities)
    }
}