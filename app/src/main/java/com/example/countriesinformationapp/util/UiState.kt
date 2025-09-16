package com.example.countriesinformationapp.util

// Sealed class representing UI states
sealed class UiState<out T> {
    // Loading state
    object Loading : UiState<Nothing>()

    // Success state with data
    data class Success<T>(val data: T) : UiState<T>()

    // Error state with message
    data class Error(val message: String, val retry: (() -> Unit)? = null) : UiState<Nothing>()

    // Empty state
    object Empty : UiState<Nothing>()

}