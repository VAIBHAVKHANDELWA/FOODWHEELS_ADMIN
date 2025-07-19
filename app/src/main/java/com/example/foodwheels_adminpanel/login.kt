package com.example.foodwheels_adminpanel

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.foodwheels_adminpanel.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.*

private lateinit var oneTapClient: SignInClient
private lateinit var signInRequest: BeginSignInRequest

class login : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var data: DatabaseReference
    private lateinit var GoogleSign: GoogleSignInClient
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        data = FirebaseDatabase.getInstance().reference

        // ✅ Initialize Google Sign-In client at the start
        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.web_client_id))
            .requestEmail()
            .build()

        GoogleSign = GoogleSignIn.getClient(this, googleSignInOptions)

        // ✅ Redirect if already signed in
        if (auth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        binding.loginbtn.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.pass.text.toString().trim()

            if (email.isBlank() || password.isBlank()) {
                if (email.isBlank()) binding.email.error = "Email cannot be empty"
                if (password.isBlank()) binding.pass.error = "Password cannot be empty"
            } else {
                loginUser(email, password)
            }
        }

        binding.txtsignup.setOnClickListener {
            startActivity(Intent(this, signup::class.java))
        }

        binding.googlelogin.setOnClickListener {
            val intent = GoogleSign.signInIntent
            launcher.launch(intent)
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid
                    if (uid != null) {
                        val userRef = data.child("Users").child(uid)
                        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                val name = snapshot.child("name").getValue(String::class.java)
                                Toast.makeText(this@login, "Welcome $name!", Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@login, MainActivity::class.java))
                                finish()
                            }

                            override fun onCancelled(error: DatabaseError) {
                                Toast.makeText(
                                    this@login,
                                    "Data fetch error: ${error.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                startActivity(Intent(this@login, MainActivity::class.java))
                                finish()
                            }
                        })
                    }
                } else {
                    Toast.makeText(
                        this@login,
                        "Login failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                if (task.isSuccessful) {
                    val googleAccount = task.result
                    val credentials = GoogleAuthProvider.getCredential(googleAccount.idToken, null)

                    auth.signInWithCredential(credentials).addOnCompleteListener { authTask ->
                        if (authTask.isSuccessful) {
                            val user = auth.currentUser
                            val uid = user?.uid
                            val name = googleAccount.displayName ?: "User"
                            val email = googleAccount.email ?: "N/A"

                            if (uid != null) {
                                val userRef = data.child("Users").child(uid)
                                val userMap = mapOf(
                                    "name" to name,
                                    "email" to email,
                                    "uid" to uid
                                )

                                userRef.setValue(userMap).addOnCompleteListener { dbTask ->
                                    if (dbTask.isSuccessful) {
                                        Toast.makeText(this, "Welcome $name!", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "Failed to save user data", Toast.LENGTH_SHORT).show()
                                    }
                                    startActivity(Intent(this, MainActivity::class.java))
                                    finish()
                                }
                            } else {
                                Toast.makeText(this, "User UID is null", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(
                                this,
                                "Google login failed: ${authTask.exception?.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Google Sign-In failed", Toast.LENGTH_LONG).show()
                }
            } else {
                Toast.makeText(this, "Google Sign-In cancelled", Toast.LENGTH_SHORT).show()
            }
        }
}
