package com.igorf.currency

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.igorf.currency.quotes.QuotesRepository
import java.math.BigDecimal
import java.math.RoundingMode

class HomeViewModel(private val repository: QuotesRepository) : ViewModel() {

    private var initialValue = BigDecimal(0)
    private val _result = MutableLiveData("")
    val result: LiveData<String> = _result
    private var initialCurrency = Pair("", "")
    private var destinyCurrency = Pair("", "")

    fun listenerValue() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Empty
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            // Empty
        }

        override fun afterTextChanged(s: Editable?) {
            if (s.toString().isNotEmpty()) {
                initialValue = s.toString().toBigDecimal()
                updateValue()
            }
        }

    }

    fun setInitialCurrency(initialCurrency: Pair<String, String>) {
        this.initialCurrency = initialCurrency
        updateValue()
    }

    fun setDestinyCurrency(destinyCurrency: Pair<String, String>) {
        this.destinyCurrency = destinyCurrency
        updateValue()
    }

    private fun updateValue() {
        if (repository.quotes.isEmpty())
            repository.fetchQuotesList()

        if (initialValue > BigDecimal.ZERO &&
            !initialCurrency.first.isBlank() &&
            !destinyCurrency.first.isBlank()
        )
            convertValue()
    }

    private fun convertValue() {
        val dollarQuote =
            initialValue.divide(
                repository.quotes.getValue("USD" + initialCurrency.first),
                4,
                RoundingMode.HALF_UP
            )
        val result = dollarQuote.multiply(repository.quotes.getValue("USD" + destinyCurrency.first))
        _result.postValue(result.toString())
    }

}

class HomeViewModelFactory(private val repository: QuotesRepository) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeViewModel(repository) as T
    }
}