package com.igorf.currency.quotes

import android.util.Log
import com.igorf.currency.network.CurrencyApi
import com.igorf.currency.util.RetrofitFactory
import com.igorf.currency.util.callback
import java.math.BigDecimal

class QuotesRepository {

    private val api: CurrencyApi by lazy {
        RetrofitFactory().getRetrofit().create(CurrencyApi::class.java)
    }

    private val _quotes = mutableMapOf<String, BigDecimal>()
    val quotes: Map<String, BigDecimal> = _quotes

    fun fetchQuotesList() {
        api.getQuotes().enqueue(
            callback { response, throwable ->
                response?.let {
                    response.body()?.let {
                        if (it.success) {
                            _quotes.clear()
                            _quotes.putAll(it.quotes)
                        } else
                            Log.e("quoteList", "success == false")
                    }
                }

                throwable?.let {
                    Log.e("quoteList", it.message ?: "EMPTY RESPONSE")
                }
            }
        )
    }
}