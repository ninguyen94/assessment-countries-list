package com.example.countriesinformationapp.data

import com.example.countriesinformationapp.model.Country

class CountryRepository {
    // Fetch countries from the API
    suspend fun fetchCountries(): Result<List<Country>> {
        return try {
            val countries = ApiClient.service.getCountries()
            if (countries.isEmpty()) {
                Result.failure(Exception("No countries found"))
            } else {
                Result.success(countries)
            }
        } catch (e: Exception) {
            Result.failure(e) // Handle network errors
        }
    }
}