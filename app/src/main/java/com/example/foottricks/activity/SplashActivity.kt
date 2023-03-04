package com.example.foottricks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.foottricks.databinding.ActivityLogin2Binding
import com.example.foottricks.databinding.ActivitySplashBinding
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    val auth = FirebaseAuth.getInstance();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val currentUser = auth.currentUser

        val databaseref = FirebaseDatabase.getInstance().reference.child("users");
        var u: Users? =null;

        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {

                    val users = dataSnapshot.getValue(Users::class.java)
                    if (users!!.uuid == currentUser?.uid) {
                        u = users
                    }

                }
                if (currentUser != null) {

                    Log.d("mechant", currentUser.uid.toString());


                    if (u?.teamId == null) {

                        var intent = Intent(this@SplashActivity, JoinTeamActivity::class.java)
                        startActivity(intent)

                        finish()
                    }else{
                        var intent = Intent(this@SplashActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                }
                else{
                    var intent = Intent(this@SplashActivity, LoginActivity::class.java)
                    startActivity(intent)

                    finish()

                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        supportActionBar?.hide()
    }
}