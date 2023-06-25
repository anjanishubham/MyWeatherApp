package com.example.weatherapp.presenter

import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.common.Constant
import com.example.weatherapp.common.RecycleViewItemDecorator
import com.example.weatherapp.common.checkConnection
import com.example.weatherapp.common.gone
import com.example.weatherapp.common.visible
import com.example.weatherapp.databinding.ActivityMainBinding
import com.example.weatherapp.domin.Weather
import com.example.weatherapp.presenter.adapter.WeatherAdapter
import com.example.weatherapp.presenter.viewmodel.WeatherViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var weatherAdapter: WeatherAdapter

    var isConnectAvailable: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetworkConnection()
        initAdapter()
        searchViewListener()
        observeCityWeather()
    }

    private fun initAdapter() {
        weatherAdapter = WeatherAdapter()
        var lm = LinearLayoutManager(baseContext)
        lm.orientation = RecyclerView.VERTICAL
        binding.rv.layoutManager = lm
        binding.rv.adapter = weatherAdapter
        binding.rv.addItemDecoration(
            RecycleViewItemDecorator(
                baseContext,
                40
            )
        )
    }

    private fun searchViewListener() {
        binding.searchViewText.setOnQueryTextListener(object : OnQueryTextListener,
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getData(newText)
                return true
            }

        })
    }

    private fun getData(newText: String?) {
        newText?.let {
            viewModel.searchCity(it)
        }
    }

    private fun checkNetworkConnection() {
        lifecycleScope.launchWhenStarted {
            checkConnection().collect {
                isConnectAvailable = when (it) {
                    true -> {
                        true
                    }

                    false -> {
                        showError(Constant.NETWORK_ERROR)
                        false
                    }
                }
            }
        }
    }

    private fun observeCityWeather() {
        lifecycleScope.launch {
            viewModel.weatherListFlow.collect {
                if (it.isLoading) {
                    showLoader()
                } else if (it.error.isNullOrEmpty().not()) {
                    hideLoader()
                    showError("dlskfjdlkj")
                } else {
                    hideLoader()
                    updateUI(it.weatherList as List<Weather>?)
                }
            }
        }
    }

    private fun updateUI(weatherList: List<Weather>?) {
        binding.rv.visible()
        binding.errorMessage.gone()
        if (weatherList.isNullOrEmpty().not()) {
            weatherAdapter.setData(weatherList as ArrayList<Weather>)
            weatherAdapter.notifyDataSetChanged()
        } else {
            showError(Constant.RESULT_NOT_FOUND)
        }
    }

    private fun showError(showNetworkError: String) {
        binding.rv.gone()
        binding.errorMessage.visible()
        when (showNetworkError) {
            Constant.NETWORK_ERROR -> {
                binding.errorMessage.text = getString(R.string.network_error)
            }

            Constant.RESULT_NOT_FOUND -> {
                binding.errorMessage.text = getString(R.string.result_not_found)
            }

            else -> {
                binding.errorMessage.gone()
            }
        }

    }

    private fun showLoader() {
        binding.progressCircular.visible()
    }

    private fun hideLoader() {
        binding.progressCircular.gone()
    }
}


