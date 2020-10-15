package com.igorf.currency.chooser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.igorf.currency.network.CurrencyApi
import com.igorf.currency.util.RetrofitFactory
import com.igorf.currency.util.callback

class CurrencyRepository {

    private val api: CurrencyApi by lazy {
        RetrofitFactory().getRetrofit().create(CurrencyApi::class.java)
    }

    private val _currencies = MutableLiveData<Map<String, String>>()
    val currencies: LiveData<Map<String, String>> = _currencies
    private val currenciesCache = mutableMapOf<String, String>()

    fun fetchCurrencyList() {
        api.getCurrenciesList().enqueue(
            callback { response, throwable ->
                response?.let {
                    response.body()?.let {
                        if (it.success) {
                            currenciesCache.clear()
                            currenciesCache.putAll(it.currencies)
                            _currencies.postValue(it.currencies)
                        }
                        else
                            Log.e("currencyList", "success == false")
                    }
                }

                throwable?.let {
                    Log.e("currencyList", it.message ?: "EMPTY RESPONSE")
                }
            }
        )
    }

    fun filter(value: String) {
        _currencies.postValue(
            currenciesCache.filter {
                it.key.contains(value, true) ||
                        it.value.contains(value, true)
            }
        )
    }
}