package com.example.bnquemisrrefactoring

import android.content.Context
import android.os.AsyncTask
import android.os.Build
import android.os.Environment
import java.io.*
import java.net.HttpURLConnection
import java.net.URL

class Reposetory : AsyncTask<String?, Int?, String?> {

    private var delegate: AsyncResponce
    private lateinit var folder: File
    private lateinit var filePath: String
    private lateinit var context: Context
//    TODO BASSAM: "Bad practice to access views in repository"
//    private lateinit var button: Button
//    private lateinit var textEdit : TextView

    constructor(context: Context, delegate: AsyncResponce) {
        this.context = context
        this.delegate = delegate
//        TODO BASSAM: "Bad practice to access views in repository"
//        button = Button(context)
//        textEdit = TextView(context)
    }

    override fun onPreExecute() {
        super.onPreExecute()
//        TODO BASSAM: "Bad practice to access views in repository"
//        button.setText("Download")
//        textEdit.setText("0")
        delegate.onStart()
    }

    override fun doInBackground(vararg params: String?): String? {
        var input: InputStream? = null
        var output: OutputStream? = null
        var connection: HttpURLConnection? = null


        try {
            var url = URL(params[0])
            connection = url.openConnection() as HttpURLConnection
            connection.connect()

            if (connection.responseCode !== HttpURLConnection.HTTP_OK) {
                return "Server returned HTTP" + connection.responseCode.toString() + "" + connection.responseMessage
            }

            val fileLength = connection.contentLength

            //check os version

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                var file = File(context.getExternalFilesDir(null).toString() + "/" + "DownloadFile")
                if (!file.exists()) {
                    file.mkdir()
                }
                folder = file

            } else {
                val externalStorageDirectory = Environment.getExternalStorageDirectory().toString()
                folder = File(externalStorageDirectory, "DownloadFile")
                folder.mkdir()
            }
            val imageFile = File(folder, params[1])
            filePath = imageFile.absolutePath

            try {
                imageFile.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            input = connection.inputStream
            output = FileOutputStream(imageFile.absolutePath)
            val data = ByteArray(4096)
            var total: Long = 0
            var count = 0
            while (input.read(data).also({ count = it }) != -1) {
//
                total += count.toLong()
                if (fileLength > 0)
                    publishProgress((total * 100 / fileLength).toInt())
                if (output != null) {
                    output.write(data, 0, count)
                }
            }

        } catch (e: Exception) {
            return e.toString()
        } finally {
            try {
                if (output != null)
                    output.close()
                if (input != null)
                    input.close()
            } catch (ignored: IOException) {

            }
            if (connection != null)
                connection.disconnect()

        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
//        TODO BASSAM: "Bad practice to access views in repository"
//        //send data to the live data
//        textEdit.setText(values[0]!!)
        values[0]?.let { 
            delegate.onProgress(it) 
        }
    }

    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

//        TODO BASSAM: "Bad practice to access views in repository"
//        if(result!=null){
//            delegate?.processFinish("0")
//            button.setText("Download")
//            Toast.makeText(context, "Download Failed", Toast.LENGTH_LONG).show()
//
//        }else{
//            delegate?.processFinish("1")
//            button.setText("Pause")
//            Toast.makeText(context, "Download Completed", Toast.LENGTH_LONG).show()
//        }

        delegate.onFinish()
    }
}
