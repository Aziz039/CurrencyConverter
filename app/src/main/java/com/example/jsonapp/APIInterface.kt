package com.example.jsonapp

import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/eur.json")
    fun doGetListResources(): retrofit2.Call<CurrencyDetails?>?
}