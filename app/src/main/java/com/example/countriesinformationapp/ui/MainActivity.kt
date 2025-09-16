package com.example.countriesinformationapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.countriesinformationapp.R

import com.example.countriesinformationapp.databinding.ActivityMainBinding
import com.example.countriesinformationapp.util.UiState

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: CountryViewModel by viewModels()
    private val adapter = CountryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = getString(R.string.app_name)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        // Observe UI state
        viewModel.uiState.observe(this) { state ->
            when (state) {
                is UiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.GONE
                    binding.btnRetry.visibility = View.GONE
                }
                is UiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.VISIBLE
                    adapter.submitList(state.data)
                }
                is UiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = state.message
                    binding.btnRetry.visibility = View.VISIBLE
                    binding.btnRetry.setOnClickListener { state.retry?.invoke() }
                }
                is UiState.Empty -> {
                    binding.progressBar.visibility = View.GONE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvError.visibility = View.VISIBLE
                    binding.tvError.text = "No countries available"
                    binding.btnRetry.visibility = View.VISIBLE
                    binding.btnRetry.setOnClickListener { viewModel.loadCountries() }
                }
            }
        }
    }
}