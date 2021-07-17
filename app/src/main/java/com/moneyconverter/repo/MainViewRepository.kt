package com.moneyconverter.repo

import com.google.gson.JsonObject
import com.moneyconverter.MainViewModelListener
import com.moneyconverter.api.ApiServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewRepository(
    private val listener: MainViewModelListener)
{
    fun getCurrencyValue(query: String, apiKey: String) {
        val callBack = ApiServices.service.getConvertValue(query, "ultra", apiKey)
        callBack.enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                if (response.isSuccessful) {
                    listener.getConverterResponse(response.body(), "")
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                listener.getConverterResponse(null, t.localizedMessage ?: "Internal server error")
            }

        })
    }

}