package com.example.chit_chat

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(val context: Context, val userlist:ArrayList<User>): RecyclerView.Adapter<UserAdapter.viewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.all_users,parent,false)
        return viewholder(view)
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentuser=userlist[position]
        holder.name.text=currentuser.Name
        holder.itemView.setOnClickListener{
            val intent= Intent(context, Chat::class.java)
            intent.putExtra("name",currentuser.Name)
            intent.putExtra("uid",currentuser.Uid)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return userlist.size
    }
    class viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name=itemView.findViewById<TextView>(R.id.username)
    }

}