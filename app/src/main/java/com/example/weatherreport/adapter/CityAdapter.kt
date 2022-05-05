package com.example.weatherreport.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherreport.City
import com.example.weatherreport.DetailsActivity
import com.example.weatherreport.databinding.CityBinding
import com.example.weatherreport.touch.CityTouchHelperCallback

class CityAdapter(var context: Context) :
    ListAdapter<City, CityAdapter.ViewHolder>(DiffCallback()), CityTouchHelperCallback {

    companion object {
        const val CITY_NAME = "CITY_NAME"
    }

    var cities = arrayListOf<City>()

    inner class ViewHolder(private var binding: CityBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.tvCityName.text = city.name
            binding.btnRemove.setOnClickListener { deleteCity(this.adapterPosition) }
            binding.btnDetails.setOnClickListener {
                val intentDetails = Intent()
                intentDetails.setClass(context, DetailsActivity::class.java)
                intentDetails.putExtra(CITY_NAME, binding.tvCityName.text.toString())
                context.startActivity(intentDetails)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = CityBinding.inflate(
            LayoutInflater.from(context),
            parent, false
        )
        return ViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    fun addCity(city: City) {
        cities.add(city)
        notifyItemInserted(cities.indexOf(city))
    }

    fun deleteCity(idx: Int) {
        cities.removeAt(idx)
        notifyItemRemoved(idx)
    }

    override fun onDismissed(position: Int) {
        deleteCity(position)
    }

    override fun onItemMoved(fromPosition: Int, toPosition: Int) {
        notifyItemMoved(fromPosition, toPosition)
    }
}

class DiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem.name == newItem.name
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
        return oldItem == newItem
    }
}