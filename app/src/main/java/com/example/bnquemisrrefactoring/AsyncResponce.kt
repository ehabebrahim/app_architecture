package com.example.bnquemisrrefactoring

interface AsyncResponce {
    fun onStart()
    fun onFinish()
    fun onProgress(progress: Int)
}
