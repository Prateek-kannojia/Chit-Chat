package com.example.chit_chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Signup : AppCompatActivity() {
    private lateinit var Email: EditText
    private lateinit var Password: EditText
    private lateinit var Name: EditText
    private lateinit var Signupbutton: Button
    private lateinit var mauth: FirebaseAuth
    private lateinit var mdatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mauth= FirebaseAuth.getInstance()
        Email=findViewById(R.id.Email)
        Password=findViewById(R.id.Password)
        Name=findViewById(R.id.Name)
        Signupbutton=findViewById(R.id.Signup)

        Signupbutton.setOnClickListener {
            val name=Name.text.toString()
            val email=Email.text.toString()
            val password=Password.text.toString()
            signup(name,email,password)
        }
        supportActionBar!!.setHomeButtonEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    private fun signup(name:String,email: String, password: String) {
        mauth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addusertofirebase(name,email, mauth.currentUser?.uid!!)
                    val intent=Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this,"Something went worng",Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun addusertofirebase(name: String, email: String, uid: String) {
        mdatabase= FirebaseDatabase.getInstance().getReference()
        mdatabase.child("user").child(uid).setValue(User(name,email,uid))

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else-> super.onOptionsItemSelected(item)
        }
    }
}