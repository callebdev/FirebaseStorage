package com.calleb.firebasestorage.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.calleb.firebasestorage.*
import com.calleb.firebasestorage.data.MainRepository
import com.calleb.firebasestorage.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )

        initComponents()

        getUploadResult()
    }

    private fun getUploadResult() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                UPLOADED -> {
                    Toast.makeText(this, getString(R.string.success_message), Toast.LENGTH_SHORT)
                        .show()
                    viewModel.onStateSet()
                }
                FAILED -> {
                    Toast.makeText(
                        this, getString(R.string.error_message), Toast.LENGTH_SHORT
                    ).show()
                    viewModel.onStateSet()
                }
            }
        })
    }

    private fun initComponents() {

        // ViewModel
        val repository = MainRepository()
        val factory =
            MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        // ClickListeners
        binding.buttonDocx.setOnClickListener { uploadDocx() }
        binding.buttonImage.setOnClickListener { uploadImage() }
        binding.buttonMusic.setOnClickListener { uploadAudio() }
        binding.buttonPdf.setOnClickListener { uploadPdf() }
        binding.buttonVideo.setOnClickListener { uploadVideo() }

        // This is used so that databinding can observe the LiveData
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PDF -> uploadData(data)
                DOCX -> uploadData(data)
                AUDIO -> uploadData(data)
                VIDEO -> uploadData(data)
                IMAGE -> uploadData(data)
            }
        }
    }

    private fun uploadData(data: Intent?) {
        data?.let {
            viewModel.upload(data.data!!)
        }
    }

    private fun uploadPdf() {
        viewModel.clearText()
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            startActivityForResult(Intent.createChooser(this, "Select a PDF to upload"),
                PDF
            )
        }
    }

    private fun uploadImage() {
        viewModel.clearText()
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            startActivityForResult(Intent.createChooser(this, "Select an image to upload"),
                IMAGE
            )
        }
    }

    private fun uploadVideo() {
        viewModel.clearText()
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "video/*"
            startActivityForResult(Intent.createChooser(this, "Select a video to upload"),
                VIDEO
            )
        }
    }

    private fun uploadAudio() {
        viewModel.clearText()
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
            startActivityForResult(Intent.createChooser(this, "Select an audio to upload"),
                AUDIO
            )
        }
    }

    private fun uploadDocx() {
        viewModel.clearText()
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "docx/*"
            startActivityForResult(
                Intent.createChooser(
                    this,
                    "Select a document (.docx) to upload"
                ), DOCX
            )
        }
    }

}