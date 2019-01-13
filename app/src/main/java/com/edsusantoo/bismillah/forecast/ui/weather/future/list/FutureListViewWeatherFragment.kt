package com.edsusantoo.bismillah.forecast.ui.weather.future.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.edsusantoo.bismillah.forecast.R

class FutureListViewWeatherFragment : Fragment() {

    companion object {
        fun newInstance() = FutureListViewWeatherFragment()
    }

    private lateinit var viewModel: FutureListViewWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.future_list_view_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(FutureListViewWeatherViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
