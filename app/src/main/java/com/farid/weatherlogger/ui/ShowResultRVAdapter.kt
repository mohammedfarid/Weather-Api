package com.farid.weatherlogger.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.farid.weatherlogger.R
import com.farid.weatherlogger.model.response.WeatherResponse
import com.farid.weatherlogger.utils.DateAndTimeUtils

class ShowResultRVAdapter(var weatherResponse: List<WeatherResponse>) :
    RecyclerView.Adapter<ShowResultRVAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvWeatherName:AppCompatTextView = itemView.findViewById(R.id.tvWeatherName)
        private val tvWeatherDate:AppCompatTextView = itemView.findViewById(R.id.tvWeatherDate)
        private val tvWeatherDegree:AppCompatTextView = itemView.findViewById(R.id.tvWeatherDegree)
        fun bind(weatherResponse: WeatherResponse) {
            tvWeatherDegree.text = weatherResponse.let {
                it.main!!.temp + "\u2103"
            } ?: "0\u2103"

            tvWeatherName.text = weatherResponse.let {
                it.name
            } ?: ""

            tvWeatherDate.text = weatherResponse.let {
                DateAndTimeUtils.getDate(it.dt!!.toLong(), DateAndTimeUtils.TIME_FORMATE)
            } ?: ""
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return weatherResponse.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(weatherResponse[position])
    }
}