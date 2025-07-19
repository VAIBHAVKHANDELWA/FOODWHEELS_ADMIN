package com.example.foodwheels_adminpanel

import android.os.Bundle
import android.widget.Adapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwheels_adminpanel.databinding.ActivityDeliveryBinding
import java.util.ArrayList

class delivery : AppCompatActivity() {
    private val binding:ActivityDeliveryBinding by lazy {
        ActivityDeliveryBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        val cname= arrayListOf("AMIT","SOHAN","VIJAY","AKSHAT","LALIT","MOHAN","SOHAN","ROHAN")
        val pname= arrayListOf("recieved","pending","recieved","nottrecieved","pending","nottrecieved","pending","nottrecieved")


        val adapter=adapter2(ArrayList(cname),ArrayList(pname))

        binding.rcylv.adapter=adapter
        binding.rcylv.layoutManager=LinearLayoutManager(this)

       binding.back.setOnClickListener {
           finish()
       }
    }
}