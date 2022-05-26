package com.example.chit_chat

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {
    private lateinit var userrecyclerview: RecyclerView
    private lateinit var userlist:ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mauth: FirebaseAuth
    private lateinit var mdatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mauth = FirebaseAuth.getInstance()
        mdatabase = FirebaseDatabase.getInstance().reference
        //userlist
        userlist = ArrayList()
        //adapter
        adapter = UserAdapter(this, userlist)
        //reclyclerview
        userrecyclerview = findViewById(R.id.userrecyclerview)
        userrecyclerview.layoutManager = LinearLayoutManager(this)
        userrecyclerview.adapter = adapter
        DividerItemDecoration(
            this, // context
            (userrecyclerview.layoutManager as LinearLayoutManager).orientation
        ).apply {
            // add divider item decoration to recycler view
            // this will show divider line between items
            userrecyclerview.addItemDecoration(this)
        }

        mdatabase.child("user").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot){
                userlist.clear()
                for (e in snapshot.children){
                    val currentuser=e.getValue(User::class.java)
                    if (currentuser != null) {
                        if (mauth.currentUser?.uid!=currentuser.Uid)
                            userlist.add(currentuser)
                    }
                }
                adapter.notifyDataSetChanged()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId== R.id.logout){
            mauth.signOut()
            val intent= Intent(this, Login::class.java)
            finish()
            startActivity(intent)
            return true
        }
        return true
    }
    //for closing app
    override fun onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true)
    }

}