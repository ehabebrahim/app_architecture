package com.example.bnquemisrrefactoring

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MainViewModel(application: Application) : AndroidViewModel(application), AsyncResponce {

    val progressLiveData = MutableLiveData<Int>()
    // TODO BASSAM: "Create another live data for Button state"


    fun start() {

        // here we started the repository to download, and give it the implementation of `AsyncResponce` to get download result
        Reposetory(getApplication(), this).execute()
    }

    override fun onStart() {
        // TODO BASSAM: "update live data for button to be disabled"
    }

    override fun onFinish() {
        // TODO BASSAM: "update live data for button to be enabled again"
    }

    override fun onProgress(progress: Int) {
        // TODO BASSAM: "Update live data progress"
    }


}