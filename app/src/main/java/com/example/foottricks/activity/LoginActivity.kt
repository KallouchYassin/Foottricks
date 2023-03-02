package com.example.foottricks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.foottricks.databinding.ActivityLogin2Binding
import com.example.foottricks.model.Users

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding
    val auth = FirebaseAuth.getInstance();
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser

        val databaseref = FirebaseDatabase.getInstance().reference.child("users");
        var u: Users = Users();

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


                    if (u.teamId == null) {

                          var intent = Intent(this@LoginActivity, JoinTeamActivity::class.java)
                        startActivity(intent)

                        finish()
                    }else{
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.others.setOnClickListener {
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginbtn.setOnClickListener {
            binding.progressBar.visibility = View.VISIBLE
            val email = binding.loginEmail.text
            val password = binding.password.text
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter email", Toast.LENGTH_SHORT).show();
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
                binding.progressBar.visibility = View.GONE
                return@setOnClickListener;
            }
            auth.signInWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this, "Authentication successfull.",
                            Toast.LENGTH_SHORT
                        ).show()
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(
                            this, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                }
        }

    }
}