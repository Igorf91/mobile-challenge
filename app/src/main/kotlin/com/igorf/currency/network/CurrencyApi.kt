package com.igorf.currency.network

import retrofit2.Call
import retrofit2.http.GET
import vo.CurrenciesQuotesVo
import vo.CurrencyListVo

interface CurrencyApi {

    @GET("list")
    fun getCurrenciesList(): Call<CurrencyListVo>

    @GET("live")
    fun getQuotes(): Call<CurrenciesQuotesVo>
}