package com.example.foottricks.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request.Method
import com.android.volley.toolbox.JsonObjectRequest
import com.example.foottricks.adapter.MessageAdapter
import com.example.foottricks.databinding.ActivityDisscusionBinding
import com.example.foottricks.model.Messages
import com.example.foottricks.ui.chat.ChatFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Array.newInstance
import java.util.Date

class DisscusionActivity : AppCompatActivity() {

    private lateinit var receiverImg: String

    private lateinit var receiverName: String

    private lateinit var receiverUid: String
    private lateinit var senderUid: String

    private lateinit var binding: ActivityDisscusionBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    companion object {
        var sImage: String? = null
        var rImage: String? = null
    }


    private lateinit var senderRoom: String
    private lateinit var receiverRoom: String
    private lateinit var messagesArrayList: ArrayList<Messages>


    private lateinit var messageAdapter: RecyclerView
    private lateinit var adapter: MessageAdapter


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisscusionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        receiverName = intent.getStringExtra("name")!!
        receiverImg = intent.getStringExtra("receiverImg")!!
        receiverUid = intent.getStringExtra("uid")!!
        messagesArrayList = ArrayList<Messages>();


        senderUid = auth.uid!!;
        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        Picasso.get().load(receiverImg).into(binding.disscusionImage)
        binding.receiverName.text = "" + receiverName

        messageAdapter = binding.messageAdapter;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        messageAdapter.layoutManager = linearLayoutManager;
        adapter = MessageAdapter(messagesArrayList, this);
        messageAdapter.adapter = adapter

        var reference = database.getReference().child("users").child(auth.uid!!);
        var chatReference =
            database.getReference().child("chats").child(senderRoom).child("messages");

        chatReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messagesArrayList.clear();
                for (dataSnapshot in snapshot.children) {

                    val messages = dataSnapshot.getValue(Messages::class.java)
                    messagesArrayList.add(messages!!)
                }
                adapter.notifyDataSetChanged();

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                sImage = snapshot.child("imageUri").value.toString()
                rImage = receiverImg;
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.sendBtn.setOnClickListener {

            var message = binding.message.text.toString();
            if (message.isEmpty()) {
                Toast.makeText(this, "Please enter a valid message", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.message.setText("");
            var date = Date();
            var msg = Messages(message, senderUid, date.time);
            database = FirebaseDatabase.getInstance()
            database.reference.child("chats").child(senderRoom).child("messages").push()
                .setValue(msg).addOnCompleteListener {

                    database.reference.child("chats").child(receiverRoom).child("messages")
                        .push()
                        .setValue(msg).addOnCompleteListener {


                        }


                }
        }
        binding.imgToolbarBtnBack.setOnClickListener {
            it.setBackgroundColor(Color.rgb(44, 244, 224))
            finish()

        }

    }




}