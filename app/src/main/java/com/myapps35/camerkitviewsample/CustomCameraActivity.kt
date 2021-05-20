package com.myapps35.camerkitviewsample

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap.CompressFormat
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.wonderkiln.camerakit.*
import java.io.ByteArrayOutputStream
import java.io.File
import kotlinx.android.synthetic.main.activity_custom_camera.*
import java.io.FileOutputStream
import java.util.*


class CustomCameraActivity : AppCompatActivity(), CameraKitEventListener, View.OnClickListener {
    @Suppress("PrivatePropertyName")
    private val JPEG_IMAGE_QUALITY_PERCENTAGE = 70
    val REQUEST_DOCUMENT_PREVIEW = 180


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_custom_camera)
        camera.addCameraKitListener(this)
        camera.setJpegQuality(JPEG_IMAGE_QUALITY_PERCENTAGE)
        cameraClick.setOnClickListener(this)
        camera.setOnClickListener(this)
    }

    override fun onStart() {
        super.onStart()
        camera.start()
    }

    override fun onResume() {
        super.onResume()
        camera.start()
    }

    override fun onPause() {
        camera.stop()
        super.onPause()
    }

    override fun onStop() {
        camera.stop()
        super.onStop()
    }

    override fun onImage(p0: CameraKitImage) {
        val imagePath = File(cacheDir, "images")

        if (!imagePath.exists()) {
            imagePath.mkdirs()
        }
        val fileName = "photo_" + Date().time + ".jpg"
        val savedPhoto = File(imagePath, fileName)
        try {
            val outputStream = FileOutputStream(savedPhoto.path)
            val bos = ByteArrayOutputStream()
            p0.bitmap.compress(CompressFormat.JPEG, JPEG_IMAGE_QUALITY_PERCENTAGE, bos)
            outputStream.write(bos.toByteArray())
            outputStream.close()
        } catch (e: java.io.IOException) {
            e.printStackTrace()
        }
        val photoURI = Uri.fromFile(savedPhoto)

        setResult(Activity.RESULT_OK, intent.putExtra("imageUri", photoURI.path))
        finish()

//        Intent(this, MainActivity::class.java)
//                .putExtra("imageUri", photoURI)
//                .also { startActivityForResult(it, REQUEST_DOCUMENT_PREVIEW) }
    }

    override fun onVideo(p0: CameraKitVideo?) {
        return
    }

    override fun onEvent(p0: CameraKitEvent?) {
        return
    }

    override fun onError(p0: CameraKitError?) {
        return
    }

    override fun onClick(p0: View) {
        if (p0.id == R.id.cameraClick  || p0.id == R.id.camera) {
            try {
                camera?.captureImage()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}