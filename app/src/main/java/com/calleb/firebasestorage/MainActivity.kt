package com.calleb.firebasestorage

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val PDF = 0
    val DOCX = 1
    val AUDIO = 2
    val VIDEO = 3
    val IMAGE = 4

    private lateinit var uri: Uri
    private lateinit var mStorageRef: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mStorageRef = FirebaseStorage.getInstance().getReference("Uploaded_files")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK) {
            when(requestCode){
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

    private fun upload (){
        val mReference = mStorageRef.child(uri.lastPathSegment!!)
        try {
            mReference.putFile(uri).addOnSuccessListener { taskSnapshot: UploadTask.TaskSnapshot? -> val url = taskSnapshot!!.uploadSessionUri
                txtDwn.text = url.toString()
                Toast.makeText(this, "Your file was successful uploaded", Toast.LENGTH_SHORT).show()
            }
        }catch (ex: Exception){
            Toast.makeText(this, "Error! Your file wasn't uploaded. Try again.\nERROR: ${ex.toString()}", Toast.LENGTH_LONG).show()
        }
    }

    fun uploadPdf(view: View) {
        val intent = Intent()
        intent.type = "application/pdf"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select a PDF to upload"), PDF)
    }

    fun uploadImage(view: View) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select an image to upload"), IMAGE)

    }

    fun uploadVideo(view: View) {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select a video to upload"), VIDEO)
    }

    fun uploadAudio(view: View) {
        val intent = Intent()
        intent.type = "audio/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select an audio to upload"), AUDIO)
    }

    fun uploadDocx(view: View) {
        val intent = Intent()
        intent.type = "docx/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent,"Select a document (.docx) to upload"), DOCX)
    }


}