package com.example.foottricks.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.foottricks.R
import com.example.foottricks.databinding.ActivityRegister2Binding
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegister2Binding
    val auth = FirebaseAuth.getInstance();
    var items: Array<String> = arrayOf()
    val imageUri: String =
        "https://firebasestorage.googleapis.com/v0/b/foottricks-5a2f5.appspot.com/o/profile.png?alt=media&token=51f4ddbd-c439-4f10-b754-551d6b6b10ab"

    var emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private lateinit var database: FirebaseDatabase
    public override fun onStart() {
        super.onStart()
        var currentUser = auth.currentUser
        val databaseref = database.reference.child("users");
        var u:Users=Users();
        databaseref.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SuspiciousIndentation")
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {

                    val users = dataSnapshot.getValue(Users::class.java)
                    if (users!!.uuid != currentUser?.uid) {
                        u=users
                    }

                }
                if (currentUser != null) {

                    if(u.teamId == null){
                        Log.d("mechant", u.toString());

                     var intent = Intent(this@RegisterActivity, JoinTeamActivity::class.java)
                        startActivity(intent)

                        finish()
                    }else {
                        var intent = Intent(this@RegisterActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }


                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
            })


//        Log.d("testers", );

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater);
        setContentView(binding.root);
        database = FirebaseDatabase.getInstance();



        val database = FirebaseDatabase.getInstance()




        binding.signupbtn.setOnClickListener {
            binding.progressBa2r.visibility = View.VISIBLE
            val email = binding.email.text
            val password = binding.password.text
            val firstname = binding.firstname.text
            val repassword = binding.repassword.text
            val lastname = binding.lastname.text


            if (email.isEmpty() || password.isEmpty() || firstname.isEmpty() || repassword.isEmpty() || lastname.isEmpty()) {
                if (email.isEmpty()) {
                    binding.progressBa2r.visibility = View.GONE

                    binding.email.error = "Entre email";
                }
                if (firstname.isEmpty()) {
                    binding.progressBa2r.visibility = View.GONE
                    binding.firstname.error = "Entre phone";
                }
                if (lastname.isEmpty()) {
                    binding.progressBa2r.visibility = View.GONE
                    binding.lastname.error = "Entre name";
                }
                if (repassword.isEmpty()) {
                    binding.progressBa2r.visibility = View.GONE
                    binding.repassword.error = "Entre re-password";
                }
                if (password.isEmpty()) {
                    binding.progressBa2r.visibility = View.GONE
                    binding.password.error = "Entre passord";
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show();

            } else if (!email.matches(emailPattern.toRegex())) {

                binding.email.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show();
                binding.progressBa2r.visibility = View.GONE
            } else if (firstname.length <3 ) {
                binding.firstname.error = "Enter a valid name"
                binding.progressBa2r.visibility = View.GONE
                Toast.makeText(this, "Enter a valid name", Toast.LENGTH_SHORT).show();

            } else if (password.length < 6) {
                binding.password.error = "Password must contain at leats 6 chars"
                binding.progressBa2r.visibility = View.GONE
                Toast.makeText(this, "Password must contain at leats 6 chars", Toast.LENGTH_SHORT)
                    .show();

            } else if (password.toString() != repassword.toString()) {
                binding.repassword.error = "Password does not match"
                binding.progressBa2r.visibility = View.GONE
                Toast.makeText(this, "Password does not match", Toast.LENGTH_SHORT).show();

            } else {

                auth.createUserWithEmailAndPassword(email.toString(), password.toString())
                    .addOnCompleteListener(this) { task ->
                        findViewById<View>(R.id.progressBa2r).visibility = View.GONE

                        if (task.isSuccessful) {
                            val databaseref =
                                database.reference.child("users").child(auth.currentUser!!.uid)
                            val users: Users = Users(
                                lastname.toString(),firstname.toString(), email.toString(),null,
                                 auth.currentUser!!.uid, null,"player", imageUri
                            )
                            databaseref.setValue(users).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    var intent =
                                        Intent(this@RegisterActivity, JoinTeamActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                } else {
                                    Toast.makeText(
                                        this, "Something went wrong",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }


                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(
                                this, "Authentication failed.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
        }
        binding.others.setOnClickListener {
            var intent = Intent(this@RegisterActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}