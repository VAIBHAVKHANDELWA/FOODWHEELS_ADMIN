package com.example.foodwheels_adminpanel

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.foodwheels.adapter.adapter1
import com.example.foodwheels_adminpanel.databinding.ActivityAllcartBinding
import com.example.foodwheels_adminpanel.model.additemclass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class allcart : AppCompatActivity() {
    private lateinit var databaseReference:DatabaseReference
    private lateinit var database:FirebaseDatabase
    private  var menuitem:ArrayList<additemclass> = ArrayList()
     private val binding:ActivityAllcartBinding by lazy {
         ActivityAllcartBinding.inflate(layoutInflater)
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        databaseReference=FirebaseDatabase.getInstance().reference
        retrivemenuitem()




        binding.back.setOnClickListener {
            finish()
        }


    }

    private fun retrivemenuitem() {
        database = FirebaseDatabase.getInstance()
        val ref: DatabaseReference = database.reference.child("menu")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                menuitem.clear()
                for (itemSnapshot in snapshot.children) {
                    val item = itemSnapshot.getValue(additemclass::class.java)
                    item?.let {
                        menuitem.add(it)
                    }
                }
                setadapter()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("Database error","error,${error.message}")
            }

        })


        }

    private fun setadapter() {
        val adapter = adapter1(this@allcart, menuitem,databaseReference)

        binding.rcyl.layoutManager= LinearLayoutManager(this)
        binding.rcyl.adapter=adapter
    }


    private fun showEmptyCartMessage() {
        binding.rcyl.visibility = View.GONE
        binding.emptyCartMessage.visibility = View.VISIBLE
        binding.emptyCartImage.visibility = View.VISIBLE
    }
}