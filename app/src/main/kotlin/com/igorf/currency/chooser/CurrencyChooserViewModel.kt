package com.igorf.currency.chooser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyChooserViewModel(private val repository: CurrencyRepository) : ViewModel() {

    fun fetchData() {
        repository.fetchCurrencyList()
    }

    fun getCurrencies() = repository.currencies
}

class CurrencyChooserViewModelFactory(private val repository: CurrencyRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyChooserViewModel(repository) as T
    }
}