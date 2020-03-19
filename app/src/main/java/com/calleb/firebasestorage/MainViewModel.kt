package com.calleb.firebasestorage

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    lateinit var uri: Uri

    /**
     * Method to upload a file to Firebase Storage
     * @fileUri -> File to be uploaded to Firebase Storage.
     */
     fun upload(fileUri: Uri) {
        repository.upload(fileUri).addOnSuccessListener { it ->
            it?.let {
                val url = it.uploadSessionUri
                //Toast.makeText(this, "Your file was successful uploaded", Toast.LENGTH_SHORT).show()
                Log.d("ViewModel", "Your file was successful uploaded $url")
            }
        }.addOnFailureListener {
            // Toast.makeText(this, "Error! Your file wasn't uploaded. Try again.", Toast.LENGTH_SHORT).show()
            Log.d("ViewModel", "Upload failed error: ${it.message}")
        }

    }
}

