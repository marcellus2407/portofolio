package com.dicoding.picodiploma.storyapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.dicoding.picodiploma.storyapp.databinding.ActivityAddStoriesBinding
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class AddStoriesActivity : AppCompatActivity() {

    private lateinit var addStoriesViewModel: AllViewModel
    private lateinit var binding: ActivityAddStoriesBinding
    private var getFile: File? = null
    companion object {
        const val TAG = "AddStoriesActivity"
        const val CAMERA_X_RESULT = 200

        private val REQUEST_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE = 10
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_stories)

        binding = ActivityAddStoriesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUEST_PERMISSIONS,
                REQUEST_CODE
            )
        }
        binding.btnCam.setOnClickListener {
            openCamera()
        }

        binding.btnGall.setOnClickListener {
            openGallery()
        }

        binding.btnAdd.setOnClickListener {
            addImage()
        }
    }
    private fun setupViewModel() {
        addStoriesViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[AllViewModel::class.java]
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val addStory = menu.findItem(R.id.addstory)
        addStory.isVisible = false

        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_language -> {
                val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(intent)
                return true
            }

            R.id.menu_logout -> {
                addStoriesViewModel.logout()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return true
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            if (!allPermissionsGranted()) {
                Toast.makeText(this, getString(R.string.notallowed), Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUEST_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }
    private fun openCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherCameraXIntent.launch(intent)
    }
    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose Your Picture")
        launcherGalleryIntent.launch(chooser)
    }
    private fun addImage() {
        if (getFile != null) {
            val file = reduceFileImage(getFile as File)

            val description =
                binding.etDescription.text.toString().toRequestBody("text/plain".toMediaType())
            val requestImageFile = file.asRequestBody("image/jpeg".toMediaTypeOrNull())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "photo",
                file.name,
                requestImageFile)
            addStoriesViewModel.getUser().observe(this) {
                if (it != null) {
                    val client = ApiConfig.getApiService()
                        .ImageUpload("Bearer " + it.token, imageMultipart, description)
                    client.enqueue(object : Callback<UploadFileResponse> {
                        override fun onResponse(
                            call: Call<UploadFileResponse>,
                            response: Response<UploadFileResponse>
                        ) {
                            val responseBody = response.body()
                            Log.d(TAG, "onResponse: $responseBody")
                            if (response.isSuccessful && responseBody?.message == "Story created successfully") {
                                Toast.makeText(
                                    this@AddStoriesActivity,
                                    getString(R.string.successup),
                                    Toast.LENGTH_SHORT
                                ).show()
                                val intent =
                                    Intent(this@AddStoriesActivity, MainActivity::class.java)
                                startActivity(intent)
                            } else {
                                Log.e(TAG, "onFailure1: ${response.message()}")
                                Toast.makeText(
                                    this@AddStoriesActivity,
                                    getString(R.string.failedup),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                        override fun onFailure(call: Call<UploadFileResponse>, t: Throwable) {
                            Log.e(TAG, "onFailure2: ${t.message}")
                            Toast.makeText(
                                this@AddStoriesActivity,
                                getString(R.string.failedup),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    })

                }
            }
        }

    }
    private val launcherCameraXIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val theFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
            getFile = theFile

            val result = rotateBitmap(
                BitmapFactory.decodeFile(theFile.path),
                isBackCamera
            )

            binding.imgAdd.setImageBitmap(result)
        }
    }
    private val launcherGalleryIntent = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val theFile = uriToFile(selectedImg, this@AddStoriesActivity)
            getFile = theFile
            binding.imgAdd.setImageURI(selectedImg)
        }
    }
}