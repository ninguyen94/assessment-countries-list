package com.example.countriesinformationapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countriesinformationapp.data.CountryRepository
import com.example.countriesinformationapp.model.Country
import com.example.countriesinformationapp.util.UiState
import kotlinx.coroutines.launch


class CountryViewModel(
    private val repository: CountryRepository = CountryRepository()
) : ViewModel() {

    private val _uiState = MutableLiveData<UiState<List<Country>>>()
    val uiState: LiveData<UiState<List<Country>>> = _uiState

    init {
        loadCountries()
    }

    fun loadCountries() {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            val result = repository.fetchCountries()
            result.onSuccess {
                _uiState.value = if (it.isEmpty()) UiState.Empty else UiState.Success(it)
            }.onFailure { e ->
                _uiState.value = UiState.Error(
                    e.localizedMessage ?: "Unknown error",
                    retry = { loadCountries() }
                )
            }
        }
    }
}