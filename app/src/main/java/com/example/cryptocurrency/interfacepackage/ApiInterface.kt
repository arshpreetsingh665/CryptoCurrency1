package com.example.cryptocurrency.interfacepackage

import com.example.cryptocurrency.model.CoinModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    //Api=https://api.coinmarketcap.com/data-api/v3/cryptocurrency/listing?start=1&limit=500
    @GET("data-api/v3/cryptocurrency/listing?start=1&limit=500")
    suspend fun getTopCurrencyList():Response<CoinModel>

}