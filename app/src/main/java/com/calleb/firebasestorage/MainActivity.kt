package com.calleb.firebasestorage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var uri: Uri
    private lateinit var mStorageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStorageRef = FirebaseStorage.getInstance().getReference(UPLOADED_FILES)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PDF -> {
                    uri = data!!.data!!
                    txtUri.text = uri.toString()
                    upload()
                }

                DOCX -> {
                    uri = data!!.data!!
                    txtUri.text = uri.toString()
                    upload()
                }

                AUDIO -> {
                    uri = data!!.data!!
                    txtUri.text = uri.toString()
                    upload()
                }

                VIDEO -> {
                    uri = data!!.data!!
                    txtUri.text = uri.toString()
                    upload()
                }

                IMAGE -> {
                    uri = data!!.data!!
                    txtUri.text = uri.toString()
                    upload()
                }
            }
        }
    }

    private fun upload() {
        val mReference = mStorageRef.child(uri.lastPathSegment!!)
        try {
            mReference.putFile(uri).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? ->
                val url = taskSnapshot!!.uploadSessionUri
                txtDwn.text = url.toString()
                Toast.makeText(this, "Your file was successful uploaded", Toast.LENGTH_SHORT).show()
            }
        } catch (ex: Exception) {
            Toast.makeText(this, "Error! Your file wasn't uploaded. Try again.", Toast.LENGTH_LONG)
                .show()
            Log.d("MainActivity", "Upload failed error: ${ex.message}")
        }
    }

    fun uploadPdf(view: View) {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "application/pdf"
            startActivityForResult(Intent.createChooser(this, "Select a PDF to upload"), PDF)
        }
    }

    fun uploadImage(view: View) {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "image/*"
            startActivityForResult(Intent.createChooser(this, "Select an image to upload"), IMAGE)
        }
    }

    fun uploadVideo(view: View) {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "video/*"
            startActivityForResult(Intent.createChooser(this, "Select a video to upload"), VIDEO)
        }
    }

    fun uploadAudio(view: View) {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "audio/*"
            startActivityForResult(Intent.createChooser(this, "Select an audio to upload"), AUDIO)
        }
    }

    fun uploadDocx(view: View) {
        Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "docx/*"
            startActivityForResult(Intent.createChooser(this, "Select a document (.docx) to upload"), DOCX)
        }
    }


}