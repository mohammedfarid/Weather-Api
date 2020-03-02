package com.farid.weatherlogger.ui

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.farid.weatherlogger.R
import com.farid.weatherlogger.bases.BaseActivity
import com.farid.weatherlogger.helper.LocationManager
import com.farid.weatherlogger.model.response.WeatherResponse
import com.farid.weatherlogger.utils.DateAndTimeUtils
import com.farid.weatherlogger.utils.FragmentUtils
import com.farid.weatherlogger.viewModel.WeatherViewModel
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity() {

    var locationManager: LocationManager? = null
    val weatherViewModel: WeatherViewModel by viewModel()

    var weatherResponse: WeatherResponse? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        locationManager = LocationManager(
            this@MainActivity,
            LocationManager.UPDATES
        )

        fetchCurrentLocationProcess()

        btnSave.setOnClickListener {
            showLoadingDialog()
            if (weatherResponse != null) {
                hideLoadingDialog()
                weatherViewModel.insertWeatherDB(weatherResponse!!)
            } else {
                hideLoadingDialog()
            }
        }
        btnList.setOnClickListener {

            FragmentUtils.replaceFragment(
                this@MainActivity, R.id.fragment_container,
                ShowResultFragment(),
                true
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == LocationManager.REQUEST_PERMISSIONS_REQUEST_CODE) {
            locationManager?.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LocationManager.REQUEST_CHECK_SETTINGS) {
            locationManager?.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun fetchCurrentLocationProcess() {
        locationManager?.location(object : LocationManager.OnLocationManagerListener {
            override fun onShowPermissionRationale() {
                //showRationale()
            }

            override fun onPermissionsDenied() {

            }

            override fun onLocationSettingsDenied() {

            }

            override fun onLocationDetected(location: Location?) {
                currentLocationProcess(location)
            }

            override fun onSomethingWentWrong() {
            }
        })
    }

    private fun currentLocationProcess(location: Location?) {
        if (location != null) {
            getWeather(location)
        }
    }

    private fun getWeather(location: Location) {
        showLoadingDialog()
        weatherViewModel.getCurrentWeatherByLatLong(
            location.latitude.toString(),
            location.longitude.toString()
        ).observe(this@MainActivity, Observer {
            hideLoadingDialog()
            weatherResponse = it
            showData(weatherResponse)
            //Toast.makeText(this@MainActivity, it.name, Toast.LENGTH_LONG).show()
        })
    }

    private fun showData(weatherResponse: WeatherResponse?) {
        tvWeatherDegree.text = weatherResponse?.let {
            it.main!!.temp + "\u2103"
        } ?: "0\u2103"

        tvWeatherName.text = weatherResponse?.let {
            it.name
        } ?: ""

        tvWeatherDate.text = weatherResponse?.let {
            DateAndTimeUtils.getDate(it.dt!!.toLong(), DateAndTimeUtils.TIME_FORMATE)
        } ?: ""
    }
}
