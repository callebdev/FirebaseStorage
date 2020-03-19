package com.calleb.firebasestorage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.calleb.firebasestorage.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initComponents()
    }

    private fun initComponents() {

        // ViewModel
        val repository = MainRepository()
        val factory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        //ClickListeners
        binding.buttonDocx.setOnClickListener { uploadDocx() }
        binding.buttonImage.setOnClickListener { uploadImage() }
        binding.buttonMusic.setOnClickListener { uploadAudio() }
        binding.buttonPdf.setOnClickListener { uploadPdf() }
        binding.buttonVideo.setOnClickListener { uploadVideo() }

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
            txtUri.text = data.data.toString()
            viewModel.upload(data.data!!)
        }
    }

    private fun uploadPdf() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            startActivityForResult(Intent.createChooser(this, "Select a PDF to upload"), PDF)
        }
    }

    private fun uploadImage() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            startActivityForResult(Intent.createChooser(this, "Select an image to upload"), IMAGE)
        }
    }

    private fun uploadVideo() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "video/*"
            startActivityForResult(Intent.createChooser(this, "Select a video to upload"), VIDEO)
        }
    }

    private fun uploadAudio() {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
            startActivityForResult(Intent.createChooser(this, "Select an audio to upload"), AUDIO)
        }
    }

    private fun uploadDocx() {
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