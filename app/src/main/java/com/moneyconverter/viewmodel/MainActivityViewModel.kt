package com.moneyconverter.viewmodel

import android.app.Application
import androidx.core.text.isDigitsOnly
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.JsonObject
import com.moneyconverter.MainViewModelListener
import com.moneyconverter.R
import com.moneyconverter.repo.MainViewRepository
import com.moneyconverter.utils.NetworkConnectionUtil

class MainActivityViewModel(private var app: Application) : AndroidViewModel(app), MainViewModelListener {

    var currencyValue = MutableLiveData<String>()
    var isLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<String>()
    private var repo: MainViewRepository = MainViewRepository(this)
    private var conversionValue = 0
    private var parserValue = ""


    fun getCurrentValue(query: String, convertAmount: String, apiKey: String) {
        if (NetworkConnectionUtil.isNetworkConnected(app)) {
            if (validateQueryString(query) && validateConvertAmount(convertAmount)) {
                isLoading.postValue(true)
                parserValue = query
                conversionValue = convertAmount.toInt()
                repo.getCurrencyValue(query, apiKey)
            } else {
                errorMessage.postValue(app.getString(R.string.validation_error))
            }
        } else errorMessage.postValue(app.getString(R.string.network_error))
    }

    fun validateQueryString(query: String): Boolean {
        return query.isNotBlank()
    }

    fun validateConvertAmount(amount: String): Boolean {
        return amount.isNotBlank()
    }

    override fun getConverterResponse(data: JsonObject?, error: String) {
        isLoading.postValue(false)
        if (data == null) {
            errorMessage.postValue(error)
        } else {
            val convertValue = data.get(parserValue)
            val convertedAmount = convertValue.asDouble * conversionValue
            currencyValue.postValue(String.format("%.3f", convertedAmount))
        }
    }


    class MainViewModelFactory(
        private val application: Application
    ) : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return modelClass.getConstructor(
                Application::class.java
            )
                .newInstance(application)
        }
    }
}