package com.baiganov.weatherapp.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.viewpager2.widget.ViewPager2
import com.baiganov.weatherapp.R
import com.baiganov.weatherapp.adapters.ContentAdapter
import com.baiganov.weatherapp.adapters.ItemClickListener
import com.baiganov.weatherapp.adapters.PagerAdapter
import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.databinding.ActivityMainBinding
import com.baiganov.weatherapp.util.NetworkResult
import com.baiganov.weatherapp.viewmodels.MainViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private var searchView: SearchView? = null

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: ContentAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        title = EMPTY_STRING
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        getLocation()
        setupRecyclerView()

        binding.contentViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                mAdapter.setSelectedIndex(position)
                binding.headerRecyclerView.smoothScrollToPosition(position)
                super.onPageSelected(position)
            }
        })

        mainViewModel.weatherForecasts.observe(this, { response ->
            handleResponse(response)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.weather_menu, menu)
        val search = menu?.findItem(R.id.menu_search)
        searchView = search?.actionView as? SearchView
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_geo -> getLocation()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        try {
            val geocoder = Geocoder(this, Locale.getDefault())
            val address: List<Address> = geocoder.getFromLocationName(query, 5)
            if (address.isNotEmpty()) {
                val location = address[0]
                mainViewModel.getData(location.latitude, location.longitude, address[0].locality)
            } else {
                showToast(getString(R.string.not_found, query.toString()))
            }
        } catch (e: Exception) {
            showToast(NO_NETWORK_CONNECTION)
        }
        searchView?.let {
            it.setQuery(EMPTY_STRING, false)
            it.clearFocus()
            it.onActionViewCollapsed()
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    private fun handleResponse(response: NetworkResult<List<DailyForecastEntity>>) {
        when (response) {
            is NetworkResult.Success -> {
                binding.progressBar.isVisible = false
                binding.gradient.isVisible = true

                response.data?.let {
                    mAdapter.setData(it)
                    title = it[0].cityName

                    val pager = PagerAdapter(it, this)
                    binding.contentViewPager.adapter = pager

                    if (it.isEmpty()) {
                        binding.errorTextView.isVisible = true
                        binding.gradient.isVisible = false
                    }
                }
            }
            is NetworkResult.Error -> {
                binding.progressBar.isVisible = false
                loadDataFromCache()

                showToast(response.message.toString())
            }
            is NetworkResult.Loading -> {
                binding.progressBar.isVisible = true
                binding.gradient.isVisible = false
            }
        }
    }

    private fun loadDataFromCache() {
        mainViewModel.loadDataFromDatabase()
    }

    private fun setupRecyclerView() {
        val clickListener = ItemClickListener { position ->
            binding.contentViewPager.setCurrentItem(position, true)
        }
        mAdapter = ContentAdapter(clickListener)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.apply {
            headerRecyclerView.layoutManager = layoutManager
            headerRecyclerView.adapter = mAdapter
            (headerRecyclerView.itemAnimator as SimpleItemAnimator).supportsChangeAnimations =
                false
        }
    }

    private fun getLocation() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) ==
            PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {

                    try {
                        val geocoder = Geocoder(this, Locale.getDefault())

                        val address: List<Address> =
                            geocoder.getFromLocation(location.latitude, location.longitude, 1)
                        mainViewModel.getData(
                            location.latitude,
                            location.longitude,
                            address[0].locality
                        )


                    } catch (e: Exception) {
                        showToast(e.message.toString())
                        loadDataFromCache()
                    }
                } else {
                    binding.progressBar.isVisible = false
                    binding.errorTextView.isVisible = true
                }
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                44
            )
            getLocation()
        }
    }

    private fun showToast(text: String) {
        Toast.makeText(
            this,
            text,
            Toast.LENGTH_LONG
        ).show()
    }

    companion object {
        private const val EMPTY_STRING = ""
        private const val NO_NETWORK_CONNECTION = "No network connection"
    }
}