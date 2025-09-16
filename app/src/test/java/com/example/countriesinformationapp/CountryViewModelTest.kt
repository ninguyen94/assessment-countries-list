package com.example.countriesinformationapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.countriesinformationapp.data.CountryRepository
import com.example.countriesinformationapp.model.Country
import com.example.countriesinformationapp.ui.CountryViewModel
import com.example.countriesinformationapp.util.UiState
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CountryViewModelTest {

    // Set the main coroutines dispatcher for unit testing
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var repository: CountryRepository
    private lateinit var viewModel: CountryViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when fetchCountries success then emit Success`() = runTest {
        val countries = listOf(Country("US", "NA", "USA", "Washington, D.C."))
        coEvery { repository.fetchCountries() } returns Result.success(countries)

        viewModel = CountryViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Success)
    }

    @Test
    fun `when fetchCountries empty then emit Empty`() = runTest {
        coEvery { repository.fetchCountries() } returns Result.success(emptyList())

        viewModel = CountryViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Empty)
    }

    @Test
    fun `when fetchCountries fails then emit Error`() = runTest {
        coEvery { repository.fetchCountries() } returns Result.failure(Exception("Network error"))

        viewModel = CountryViewModel(repository)
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is UiState.Error)
    }
}