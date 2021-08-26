package com.example.bnquemisrrefactoring

import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.myapplication.R

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    //private lateinit var text: Button
    private lateinit var text: TextView
    private val model: MainViewModel by viewModels()
    private val url = "https://github.com/MoAmrYehia/Tiva/archive/refs/heads/main.zip"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.button = findViewById(R.id.button)
        this.text = findViewById(R.id.textView2)


        //connect view model with the activity
        val newProgress = Observer<Int> { newValue ->
            this.text.setText("completed..."+newValue.toString()+"%")
            //this.text.text = newValue.toString()
        }
        model.progressLiveData.observe(this, newProgress)

        // TODO BASSAM: "Create another observer for another LiveData for Button state"
        
        val newButtonState = Observer<String>{    newValue->
            button.setText(newValue)
        }
        model.buttonState.observe(this, newButtonState)

        this.button.setOnClickListener {

            //fetal mistake-----------
            // TODO BASSAM: "This is bad practice to have repository inside View, only view model"
//            var download = Reposetory(this@MainActivity)
//            download.execute(url, "demo.jpeg")
//            download.delegate = this
            model.start()
        }
    }
}
