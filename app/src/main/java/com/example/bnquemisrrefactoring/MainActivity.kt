package com.example.bnquemisrrefactoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), AsyncResponce {

    private lateinit var button: Button
    private val model : MainViewModel by viewModels()
    private val url = "https://github.com/MoAmrYehia/Tiva/archive/refs/heads/main.zip"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.button = findViewById(R.id.button)


        //connect view model with the activity
        val newProgress = Observer<Int>{    newValue->
            textView2.text = newValue.toString()
        }
        model.Progress.observe(this, newProgress)


        this.button.setOnClickListener {


            //fetal mistake-----------
            var download = Reposetory(this@MainActivity)
            download.execute(url, "demo.jpeg")
            download.delegate = this


        }
    }

    override fun processFinish(output: String?) {
        if(output == "1"){

        }
    }

}