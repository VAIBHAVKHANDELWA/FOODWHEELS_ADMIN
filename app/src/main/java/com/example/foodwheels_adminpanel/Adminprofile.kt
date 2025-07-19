package com.example.foodwheels_adminpanel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodwheels_adminpanel.databinding.ActivityAdminprofileBinding

class Adminprofile : AppCompatActivity() {
    private val binding: ActivityAdminprofileBinding by lazy {
        ActivityAdminprofileBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

binding.back.setOnClickListener{
    finish()
}
        disableAllViews()


        binding.txtxx.setOnClickListener {
            enableAllViews()
        }
    }

    private fun disableAllViews() {
        // Disable EditTexts
        binding.nametext.isEnabled = false
        binding.emailtext.isEnabled = false
        binding.addresstext.isEnabled = false
        binding.numbertext.isEnabled = false
        binding.passwordtext.isEnabled = false

        binding.name.isEnabled = false
        binding.email.isEnabled = false
        binding.address.isEnabled = false
        binding.number.isEnabled = false
        binding.password.isEnabled = false


        binding.save.isEnabled = false

        binding.txtxx.isEnabled = true
    }

    private fun enableAllViews() {
        // Enable EditTexts
        binding.nametext.isEnabled = true
        binding.emailtext.isEnabled = true
        binding.addresstext.isEnabled = true
        binding.numbertext.isEnabled = true
        binding.passwordtext.isEnabled = true

        binding.name.isEnabled = true
        binding.email.isEnabled = true
        binding.address.isEnabled = true
        binding.number.isEnabled = true
        binding.password.isEnabled = true


        binding.save.isEnabled = true
    }
}
