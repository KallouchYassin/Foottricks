package com.example.foottricks.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foottricks.R
import com.example.foottricks.ui.calendar.CalendarFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class NoteActivity: AppCompatActivity() {

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
            dialog.setContentView(com.example.foottricks.R.layout.dialog_note_us);

            var yesBtn = dialog.findViewById<TextView>(com.example.foottricks.R.id.rating_app)

            yesBtn.setOnClickListener {
                dialog.dismiss()
                val fragmentManager = supportFragmentManager
                val myFragment = CalendarFragment()
                val fragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.add(this@NoteActivity.taskId, myFragment)
                fragmentTransaction.commit()

            }

            dialog.show();



        }






}