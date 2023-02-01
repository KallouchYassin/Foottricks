package com.example.foottricks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.foottricks.databinding.ActivityLogin2Binding

import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogin2Binding
    val auth= FirebaseAuth.getInstance();
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null){
            var intent = Intent(this@LoginActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogin2Binding.inflate(layoutInflater);
        setContentView(binding.root);

        binding.others.setOnClickListener{
            var intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginbtn.setOnClickListener{
            binding.progressBar.visibility=View.VISIBLE
            val email=binding.loginEmail.text
            val password=binding.password.text
            if(TextUtils.isEmpty(email))
            {
                Toast.makeText(this,"Enter email", Toast.LENGTH_SHORT).show();
                binding.progressBar.visibility=View.GONE
                return@setOnClickListener;
            }
            if(TextUtils.isEmpty(password))
            {
                Toast.makeText(this,"Enter password", Toast.LENGTH_SHORT).show();
                binding.progressBar.visibility=View.GONE
                return@setOnClickListener;
            }
            auth.signInWithEmailAndPassword(email.toString(), password.toString())
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        binding.progressBar.visibility=View.GONE
                        Toast.makeText(this, "Authentication successfull.",
                            Toast.LENGTH_SHORT).show()
                        var intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()

                    } else {
                        // If sign in fails, display a message to the user.
                        binding.progressBar.visibility=View.GONE
                        Toast.makeText(this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()

                    }
                }
        }

    }
}