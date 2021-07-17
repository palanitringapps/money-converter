package com.moneyconverter.viewmodel

import android.app.Application
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainActivityViewModelTest : TestCase() {

    private lateinit var viewModel: MainActivityViewModel

    @Mock
    private lateinit var app: Application


    @Before
    fun setup() {
        viewModel = MainActivityViewModel(app)
    }


    @Test
    fun validate_Query_Value_Cases() {
        assertFalse(viewModel.validateQueryString(""))
        assertTrue(viewModel.validateQueryString("USD"))
    }

    @Test
    fun validate_amount_Value_Cases() {
        assertFalse(viewModel.validateConvertAmount(""))
        assertTrue(viewModel.validateConvertAmount("5"))
    }
}