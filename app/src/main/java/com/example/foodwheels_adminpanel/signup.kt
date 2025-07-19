package com.example.foodwheels_adminpanel

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.foodwheels_adminpanel.databinding.ActivitySignupBinding
import com.example.foodwheels_adminpanel.model.usermodel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.ktx.auth

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class signup : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email:String
    private lateinit var password:String
    private lateinit var username:String
    private lateinit var resname:String
    private lateinit var data:DatabaseReference
    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        auth= Firebase.auth
        data= Firebase.database.reference
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val locationlist = arrayOf("Jaipur", "Dehradun", "Mumbai", "Delhi", "Kolkata", "Varanasi", "Bengaluru", "Assam", "Goa", "Kashmir", "Lucknow", "Punjab")
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, locationlist)
        val autoCompleteTextView = binding.autoview
        autoCompleteTextView.setAdapter(adapter)
        binding.signup.setOnClickListener{
            email=binding.email.text.toString().trim()
            password=binding.pass.text.toString().trim()
            username=binding.name.text.toString().trim()
            resname=binding.resname.text.toString().trim()
            if(email.isBlank() || password.isBlank() || username.isBlank() || resname.isBlank()) {
                if (email.isBlank()) {
                    binding.email.error = "Email cannot be empty"
                }
                if (password.isBlank()) {
                    binding.pass.error = "Password cannot be empty"
                }
                if (username.isBlank()) {
                    binding.name.error = "Username cannot be empty"
                }
                if (resname.isBlank()) {
                    binding.resname.error = "Restaurant name cannot be empty"
                }
                return@setOnClickListener // Stop further execution
            }
            else
            {
                createaccount(email,password,username,resname)
            }



        }
        binding.signin.setOnClickListener{
            val intent= Intent(this,login::class.java)
            startActivity(intent)
        }
    }

    private fun createaccount(email: String, password: String, username: String, resname: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
            if(it.isSuccessful)
            {
                Toast.makeText(this,"Account created successfully",Toast.LENGTH_SHORT).show()
                saveuserdata()
                val intent= Intent(this,login::class.java)
                startActivity(intent)
                finish()
            }
            else{
                Toast.makeText(this,"Account creation failed",Toast.LENGTH_SHORT).show()
                Log.d("TAG","Create Account:Failure",it.exception)
            }

        }
    }

    private fun saveuserdata() {
        email=binding.email.text.toString().trim()
        password=binding.pass.text.toString().trim()
        username=binding.name.text.toString().trim()
        resname=binding.resname.text.toString().trim()
        val user= usermodel(username,email,password,resname)
        val userid:String=FirebaseAuth.getInstance().currentUser!!.uid
        data.child("Users").child(userid).setValue(user)
    }


}
