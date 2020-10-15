package com.igorf.currency.chooser

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class CurrencyChooserViewModel(private val repository: CurrencyRepository) : ViewModel() {

    fun fetchData() {
        repository.fetchCurrencyList()
    }

    fun getCurrencies() = repository.currencies

    fun listenerSearch() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Empty
        }
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Empty
        }
        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isNotEmpty()) {
                repository.filter(s.toString())
            }
        }
    }
}

class CurrencyChooserViewModelFactory(private val repository: CurrencyRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CurrencyChooserViewModel(repository) as T
    }
}