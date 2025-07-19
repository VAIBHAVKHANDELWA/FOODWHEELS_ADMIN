package com.example.foodwheels_adminpanel

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class splash : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        Handler(Looper.getMainLooper()).postDelayed({
            val currentUser = auth.currentUser

            if (currentUser != null) {
                // Check if user still exists in the database
                val userId = currentUser.uid
                database.child("Users").child(userId)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                // User exists, continue to MainActivity
                                startActivity(Intent(this@splash, MainActivity::class.java))
                            } else {
                                // User was deleted from DB, sign out and go to login
                                auth.signOut()
                                startActivity(Intent(this@splash, login::class.java))
                            }
                            finish()
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // On DB error, go to login screen
                            auth.signOut()
                            startActivity(Intent(this@splash, login::class.java))
                            finish()
                        }
                    })
            } else {
                // No user logged in, go to login screen
                startActivity(Intent(this, login::class.java))
                finish()
            }
        }, 1000)
    }
}
