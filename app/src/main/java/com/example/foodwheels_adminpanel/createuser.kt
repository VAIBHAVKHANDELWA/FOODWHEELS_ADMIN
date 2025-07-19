package com.example.foodwheels_adminpanel

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodwheels_adminpanel.databinding.ActivityCreateuserBinding

class createuser : AppCompatActivity() {
    private val binding: ActivityCreateuserBinding by lazy {
        ActivityCreateuserBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.back.setOnClickListener{
            finish()
        }

    }
}