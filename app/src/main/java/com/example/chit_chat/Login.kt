package com.example.chit_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var Loginbutton: Button
    private lateinit var Signupbutton: Button
    private lateinit var mauth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mauth= FirebaseAuth.getInstance()
        Email=findViewById(R.id.Email)
        Password=findViewById(R.id.Password)
        Loginbutton=findViewById(R.id.Login)
        Signupbutton=findViewById(R.id.Signup)

        Signupbutton.setOnClickListener {
            val intent= Intent(this,Signup::class.java)
            startActivity(intent)
        }
        Loginbutton.setOnClickListener {
            val email=Email.text.toString()
            val password=Password.text.toString()
            login(email,password)
        }
    }

    private fun login(email: String, password: String) {
        mauth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent=Intent(this@Login,MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this,"user not found", Toast.LENGTH_SHORT).show()
                }
            }
    }
}