package com.calleb.firebasestorage.ui.main

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.calleb.firebasestorage.FAILED
import com.calleb.firebasestorage.UPLOADED
import com.calleb.firebasestorage.UPLOADING
import com.calleb.firebasestorage.ERROR
import com.calleb.firebasestorage.data.MainRepository

class MainViewModel(private val repository: MainRepository) : ViewModel() {
    var state = MutableLiveData<String>()

    private var _fileUrl = MutableLiveData<String>()
    val fileUrl: LiveData<String>
        get() = _fileUrl

    private var _fileUri = MutableLiveData<String>()
    val fileUri: LiveData<String>
        get() = _fileUri

    /**
     * Method to upload a file to Firebase Storage
     * @fileUri -> File to be uploaded to Firebase Storage.
     */
    fun upload(fileUri: Uri) {
        _fileUri.value = fileUri.toString()
        _fileUrl.value = UPLOADING
        repository.upload(fileUri).addOnSuccessListener { it ->
            it?.let {
                val url = it.uploadSessionUri
                state.value = UPLOADED
                Log.d("ViewModel", "Your file was successful uploaded $url")
                _fileUrl.value = url.toString()
            }
        }.addOnFailureListener {
            state.value = FAILED
            _fileUrl.value = ERROR
            Log.d("ViewModel", "Upload failed error: ${it.message}")
        }
    }

    /**
     * Returns the state to empty to avoid leaks
     */
    fun onStateSet() {
        state.value = ""
    }

    /**
     * Clears the previous text after clicking a button.
     */
    fun clearText(){
        _fileUrl.value = ""
        _fileUri.value = ""
    }
}

