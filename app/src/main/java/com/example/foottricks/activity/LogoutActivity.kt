package com.example.foottricks.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foottricks.R
import com.example.foottricks.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class LogoutActivity: AppCompatActivity() {

        private lateinit var imageUrl: Uri
        private lateinit var storage: FirebaseStorage
        val auth = FirebaseAuth.getInstance();
        private lateinit var database: DatabaseReference

        @SuppressLint("ResourceAsColor")
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)

            storage = FirebaseStorage.getInstance();
            database =
                FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid);



                var dialog: Dialog = Dialog(this, R.style.Dialoge)
                dialog.setContentView(com.example.foottricks.R.layout.dialog_layout);

                var yesBtn = dialog.findViewById<TextView>(com.example.foottricks.R.id.yesBtn)
                var noBtn = dialog.findViewById<TextView>(com.example.foottricks.R.id.noBtn)

                yesBtn.setOnClickListener {
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this@LogoutActivity, LoginActivity::class.java))


                }
                noBtn.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show();



        }






}