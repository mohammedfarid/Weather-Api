package com.farid.weatherlogger.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.farid.weatherlogger.R
import com.farid.weatherlogger.bases.BaseFragment
import com.farid.weatherlogger.model.response.WeatherResponse
import com.farid.weatherlogger.viewModel.WeatherViewModel
import kotlinx.android.synthetic.main.fragment_show_result.*
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class ShowResultFragment : BaseFragment() {
    val weatherViewModel: WeatherViewModel by viewModel()

    var weatherResponse: WeatherResponse? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_show_result, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoadingDialog()
        weatherViewModel.selectWeatherDB().observe(viewLifecycleOwner, Observer {
            hideLoadingDialog()
            if (it.isEmpty()) {
                emptyShow()
            } else {
                showData(it)
            }
        })
    }

    private fun showData(weatherResponse: List<WeatherResponse>?) {
        tv_empty.visibility = View.GONE
        rv_show.visibility = View.VISIBLE
        rv_show.adapter = weatherResponse?.let { it -> ShowResultRVAdapter(it) }
    }

    private fun emptyShow() {
        tv_empty.visibility = View.VISIBLE
        rv_show.visibility = View.GONE
    }


}
