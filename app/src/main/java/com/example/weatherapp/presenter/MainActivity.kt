package com.example.weatherapp.presenter

import android.os.Bundle
import android.view.View
import android.widget.SearchView.OnQueryTextListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
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

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val viewModel: WeatherViewModel by viewModels()
    lateinit var weatherAdapter: WeatherAdapter

    var isConnectAvailable : Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        checkNetworkConnection()
        initAdapter()
        searchViewListener()
        observerWeatherList()

    }

    private fun observerWeatherList() {
        viewModel.weatherList.observe(this, Observer {
            if(it == null){
                showError(Constant.RESULT_NOT_FOUND)
                return@Observer
            }
            it?.let {
                binding.rv.visible()
                binding.errorMessage.gone()
               weatherAdapter.setData(it as ArrayList<Weather>)
               weatherAdapter.notifyDataSetChanged()
           }
        })
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
            viewModel.getCity(it)
        }
    }

    private fun checkNetworkConnection() {
        lifecycleScope.launchWhenStarted {
            checkConnection().collect {
                isConnectAvailable = when (it) {
                    true -> {
                        viewModel.getWeatherList()
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

    private fun showError(showNetworkError: String) {
        binding.rv.gone()
        binding.errorMessage.visible()
        when(showNetworkError){
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
}