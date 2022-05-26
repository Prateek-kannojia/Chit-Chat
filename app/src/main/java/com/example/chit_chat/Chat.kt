package com.example.chit_chat

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Chat : AppCompatActivity() {
    private lateinit var chatrecyclerview: RecyclerView
    private lateinit var messagebox: EditText
    private lateinit var sendbutton: ImageView
    private lateinit var adapter: MessageAdapter
    private lateinit var messagelist:ArrayList<Message>
    private lateinit var mdatabase: DatabaseReference

    var receiverroom:String?=null
    var senderroom:String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name=intent.getStringExtra("name")
        val receiveruid=intent.getStringExtra("uid")

        val senderuid= FirebaseAuth.getInstance().currentUser?.uid

        mdatabase= FirebaseDatabase.getInstance().reference

        senderroom=receiveruid+senderuid
        receiverroom=senderuid+receiveruid

        supportActionBar?.title =name

        chatrecyclerview=findViewById(R.id.chatrecyclerview)
        sendbutton=findViewById(R.id.sendbutton)
        messagebox=findViewById(R.id.messagebox)
        messagelist= ArrayList()
        adapter= MessageAdapter(this,messagelist)

        chatrecyclerview.layoutManager= LinearLayoutManager(this)

        chatrecyclerview.adapter=adapter

        //adding data to recyclerview
        mdatabase.child("chat").child(senderroom!!).child("message")
            .addValueEventListener(object : ValueEventListener {
                @SuppressLint("NotifyDataSetChanged")
                override fun onDataChange(snapshot: DataSnapshot) {
                    messagelist.clear()
                    for(e in snapshot.children){
                        val message=e.getValue(Message::class.java)
                        messagelist.add(message!!)
                    }
                    adapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })

        //adding data to database
        sendbutton.setOnClickListener {
            val message=messagebox.text.toString()
            val messageobject= Message(message,senderuid)
            mdatabase.child("chat").child(senderroom!!).child("message").push()
                .setValue(messageobject).addOnSuccessListener {
                    mdatabase.child("chat").child(receiverroom!!).child("message").push()
                        .setValue(messageobject)
                }
            messagebox.setText("")
        }
    }
}