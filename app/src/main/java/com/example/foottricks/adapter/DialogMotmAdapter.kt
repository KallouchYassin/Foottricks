package com.example.foottricks.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class DialogMotmAdapter (
private val userList: ArrayList<Users>,
private val context: Context,
private val uuid: String,
private val UserUuid: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    companion object {

        private lateinit var auth: FirebaseAuth
        private lateinit var database: FirebaseDatabase


    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view =
            LayoutInflater.from(context).inflate(com.example.foottricks.R.layout.item_user_summoning, parent, false);

        return Viewholder(view);
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var check = holder.itemView.findViewById<CheckBox>(com.example.foottricks.R.id.test)

        holder.itemView.findViewById<CheckBox>(com.example.foottricks.R.id.test).text =
            userList.get(position).firstname + " " + userList.get(position).lastname
        check.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked)
            {
                database = FirebaseDatabase.getInstance()
                database.reference.child("matches").child(uuid).child("motm").child(UserUuid)
                    .setValue(userList.get(position)).addOnCompleteListener {

                        Log.d("reussi", "oui")
                    }
            }
            else{
                database = FirebaseDatabase.getInstance()
                database.reference.child("matches").child(uuid).child("motm").child(UserUuid)
                    .removeValue().addOnCompleteListener {

                        Log.d("reussi", "oui")
                    }
            }

        }

    }

    override fun getItemCount(): Int {
        return userList.size;
    }

}