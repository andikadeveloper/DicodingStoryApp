package com.example.dicodingstoryapp.presentation.story.add

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.widget.addTextChangedListener
import coil.load
import com.example.dicodingstoryapp.R
import com.example.dicodingstoryapp.core.createCustomTempFile
import com.example.dicodingstoryapp.core.reduceFileImage
import com.example.dicodingstoryapp.core.uriToFile
import com.example.dicodingstoryapp.databinding.ActivityAddStoryBinding
import java.io.File

class AddStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddStoryBinding

    private lateinit var currentPhotoPath: String
    private var photoFile: File? = null

    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val file = File(currentPhotoPath)

            renderPreviewImage(file)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val selectedImg = it.data?.data as Uri
            val file = uriToFile(selectedImg, this@AddStoryActivity)

            renderPreviewImage(file)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        with (binding) {
            initBinding()
        }
    }

    private fun ActivityAddStoryBinding.initBinding() {
        initListeners()
    }

    private fun ActivityAddStoryBinding.initListeners() {
        ibCamera.setOnClickListener { startTakePhoto() }

        ibPhoto.setOnClickListener { startGallery() }

        buttonAdd.setOnClickListener { uploadImage() }

        edAddDescription.addTextChangedListener { setActiveInActiveButtonAdd() }
    }

    private fun renderPreviewImage(file: File) {
        photoFile = file

        binding.apply {
            ivPhotoPreview.load(photoFile)
            setActiveInActiveButtonAdd()
        }
    }

    private fun ActivityAddStoryBinding.uploadImage() {
        if (photoFile == null) return

        val file = reduceFileImage(photoFile as File)
    }

    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                this@AddStoryActivity,
                "com.example.dicodingstoryapp",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherIntentCamera.launch(intent)
        }

        launcherIntentCamera.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent().apply {
            action = ACTION_GET_CONTENT
            type = "image/*"
        }
        val chooser = Intent.createChooser(intent, getString(R.string.choose_picture))
        launcherIntentGallery.launch(chooser)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                alertPermissionCamera()
            }
        }
    }

    private fun ActivityAddStoryBinding.setActiveInActiveButtonAdd() {
        buttonAdd.isEnabled = false

        val description = edAddDescription.text.toString().trim()

        val rules = listOf(
            photoFile != null,
            description.isNotEmpty()
        )

        for (isValid in rules) {
            if (!isValid) return
        }

        buttonAdd.isEnabled = true
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun alertPermissionCamera() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.title_camera_permission_alert))
        builder.setMessage(getString(R.string.desc_camera_permission_alert))

        builder.setPositiveButton(getString(R.string.settings)) { _, _ ->
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            val uri = Uri.fromParts("package", packageName, null)
            intent.data = uri
            startActivity(intent)
        }

        builder.show()
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}