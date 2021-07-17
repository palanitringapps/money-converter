package com.moneyconverter

import com.google.gson.JsonObject

interface MainViewModelListener {
    fun getConverterResponse(data:JsonObject?,error:String)
}