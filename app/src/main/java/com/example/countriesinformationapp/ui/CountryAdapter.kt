package com.example.countriesinformationapp.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.countriesinformationapp.databinding.ItemCountryBinding
import com.example.countriesinformationapp.model.Country

// Adapter using ListAdapter
class CountryAdapter :
    ListAdapter<Country, CountryAdapter.CountryViewHolder>(DiffCallback()) {

    inner class CountryViewHolder(private val binding: ItemCountryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Country) {
            binding.tvNameRegion.text = "${item.name}, ${item.region}"
            binding.tvCode.text = item.code
            binding.tvCapital.text = item.capital
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val binding = ItemCountryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    // DiffCallback for efficient updates
    class DiffCallback : DiffUtil.ItemCallback<Country>() {
        override fun areItemsTheSame(oldItem: Country, newItem: Country) = oldItem.code == newItem.code
        override fun areContentsTheSame(oldItem: Country, newItem: Country) = oldItem == newItem
    }
}