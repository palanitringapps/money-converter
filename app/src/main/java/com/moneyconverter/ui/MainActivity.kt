package com.moneyconverter.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.moneyconverter.R
import com.moneyconverter.databinding.ActivityConvertBinding
import com.moneyconverter.viewmodel.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var bind: ActivityConvertBinding
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var currencyList: Array<String>
    private var fromCurrency: String? = null
    private var toCurrency: String? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = DataBindingUtil.setContentView(this, R.layout.activity_convert)

        showCountryFromLocal()
        initViewModel()

        viewModel.currencyValue.observe(this, {
            bind.tvConvertedValue.visibility = View.VISIBLE
            bind.tvConvertedValue.text =
                bind.etValue.text.toString().plus("  ").plus(fromCurrency).plus(" = ").plus(it)
                    .plus("  ").plus(toCurrency)
        })


        viewModel.isLoading.observe(this, {
            bind.progressBar.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.errorMessage.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })


        bind.btConvert.setOnClickListener {
            callCurrencyValueAPI()
        }

        bind.acFromId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Nothing selected
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                fromCurrency = parent?.getItemAtPosition(position).toString()
            }

        }

        bind.acToId.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //Nothing selected
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                toCurrency = parent?.getItemAtPosition(position).toString()
            }

        }

        bind.main.setOnTouchListener { _, _ ->
            hideKeyboard()
        }

    }

    private fun hideKeyboard(): Boolean {
        val imm: InputMethodManager =
            getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        return true
    }

    private fun callCurrencyValueAPI() {
        bind.tvConvertedValue.visibility = View.GONE
        val fromAndTo = fromCurrency.plus("_").plus(toCurrency)
        viewModel.getCurrentValue(
            fromAndTo,
            bind.etValue.text.toString(),
            getString(R.string.api_key_value)
        )
    }

    private fun initViewModel() {
        val inviteModelFactory = MainActivityViewModel.MainViewModelFactory(application)
        viewModel =
            ViewModelProvider(this, inviteModelFactory).get(MainActivityViewModel::class.java)
    }

    private fun initSpinnerAdapter(currencyCodeList: List<String>) {
        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencyCodeList)
        bind.acFromId.adapter = adapter
        bind.acToId.adapter = adapter
    }

    private fun showCountryFromLocal() {
        currencyList = resources.getStringArray(R.array.country)
        fromCurrency = currencyList[0]
        toCurrency = currencyList[0]
        initSpinnerAdapter(currencyList.toList())
    }
}