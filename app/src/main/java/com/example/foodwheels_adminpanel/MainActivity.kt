package com.example.foodwheels_adminpanel

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodwheels_adminpanel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        binding.addim.setOnClickListener{
            val intent = Intent(this, additemactivity::class.java)
            startActivity(intent)
        }
        binding.vt.setOnClickListener{
            val intent = Intent(this, allcart::class.java)
            startActivity(intent)
        }
        binding.addimgg.setOnClickListener{
            val intent = Intent(this, delivery::class.java)
            startActivity(intent)
        }
        binding.profile.setOnClickListener{
            val intent = Intent(this, Adminprofile::class.java)
            startActivity(intent)
        }
        binding.add.setOnClickListener{
            val intent = Intent(this, createuser::class.java)
            startActivity(intent)
        }
       binding.pend.setOnClickListener{
           val intent = Intent(this, pending::class.java)
           startActivity(intent)
       }


    }
}