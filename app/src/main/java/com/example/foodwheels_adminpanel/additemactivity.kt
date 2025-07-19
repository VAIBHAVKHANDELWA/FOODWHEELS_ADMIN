package com.example.foodwheels_adminpanel

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodwheels_adminpanel.databinding.ActivityAdditemactivityBinding
import com.example.foodwheels_adminpanel.model.additemclass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import java.io.IOException

class additemactivity : AppCompatActivity() {
    private lateinit var fooname: String
    private lateinit var foodprice: String
    private var foodimage: Uri? = null
    private lateinit var foodndescrip: String
    private lateinit var ingridients: String

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    private val binding: ActivityAdditemactivityBinding by lazy {
        ActivityAdditemactivityBinding.inflate(layoutInflater)
    }

    private val pickImage = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        if (uri != null) {
            foodimage = uri
            binding.imgselect.setImageURI(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.create.setOnClickListener {
            fooname = binding.foodname.text.toString()
            foodprice = binding.foodprice.text.toString()
            foodndescrip = binding.fooddescription.text.toString()
            ingridients = binding.ingridients.text.toString()

            if (fooname.isEmpty() || foodprice.isEmpty() || foodndescrip.isEmpty() || ingridients.isEmpty()) {
                if (fooname.isEmpty()) binding.foodname.error = "Enter Food Name"
                if (foodprice.isEmpty()) binding.foodprice.error = "Enter Food Price"
                if (foodndescrip.isEmpty()) binding.fooddescription.error = "Enter Food Description"
                if (ingridients.isEmpty()) binding.ingridients.error = "Enter Food Ingredients"
            } else {
                if (foodimage != null) {
                    uploadImageToCloudinary(foodimage!!)
                } else {
                    Toast.makeText(this, "Please select an image", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.back.setOnClickListener { finish() }

        binding.selectimage.setOnClickListener {
            pickImage.launch("image/*")
        }
    }

    private fun uploadImageToCloudinary(imageUri: Uri) {
        val cloudName = "ddlzz2mjc" // ⬅️ Replace with your actual Cloudinary cloud name (no spaces)
        val uploadPreset = "itemname" // ⬅️ Replace with your unsigned upload preset name

        val inputStream = contentResolver.openInputStream(imageUri)
        val tempFile = File.createTempFile("upload", ".jpg", cacheDir)
        inputStream?.use { input ->
            tempFile.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("file", tempFile.name, tempFile.asRequestBody("image/*".toMediaTypeOrNull()))
            .addFormDataPart("upload_preset", uploadPreset)
            .build()

        val request = Request.Builder()
            .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
            .post(requestBody)
            .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@additemactivity, "Image upload failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                val json = JSONObject(responseBody ?: "")
                val imageUrl = json.getString("secure_url")
                runOnUiThread {
                    uploaddata(imageUrl)
                    Toast.makeText(this@additemactivity, "Item Added Successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        })
    }


    private fun uploaddata(imageUrl: String) {
        val menuref = database.getReference("menu")
        val menuitemKey = menuref.push().key ?: return

        val item = additemclass(
            foodname = fooname,
            foodprice = foodprice,
            foodimage = imageUrl,
            foodndescrip = foodndescrip,
            ingridients = ingridients
        )

        menuref.child(menuitemKey).setValue(item)
    }

}
