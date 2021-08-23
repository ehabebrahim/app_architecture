package com.example.bnquemisrrefactoring

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {







    val Progress:MutableLiveData<Int> by lazy {
        MutableLiveData<Int>().also{

        }
    }





}