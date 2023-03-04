package com.example.foottricks.activity

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.MessageAdapter
import com.example.foottricks.databinding.ActivityDisscusionBinding
import com.example.foottricks.databinding.ActivityJoinTeamBinding
import com.example.foottricks.model.Messages
import com.example.foottricks.model.Team
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class JoinTeamActivity : AppCompatActivity() {


    private lateinit var binding: ActivityJoinTeamBinding
    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        binding.finishbtn.setOnClickListener {

            if (binding.teamCode.text.isEmpty()) {
                binding.progressBar.visibility = View.GONE
                binding.teamCode.error = "Entre a team code";
            } else {
                var currentUser = auth.currentUser
                val databaseref = FirebaseDatabase.getInstance().reference.child("teams");


                databaseref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (dataSnapshot in snapshot.children) {

                            val team = dataSnapshot.getValue(Team::class.java)
                            if (team!!.uuid == binding.teamCode.text.toString()) {
                                val databaseref =
                                    FirebaseDatabase.getInstance().reference.child("users")
                                        .child(auth.currentUser!!.uid)
                                val newEntry = HashMap<String, String>()
                                newEntry["teamId"] = binding.teamCode.text.toString()

                                databaseref.updateChildren(newEntry as Map<String, String>)
                                    .addOnSuccessListener {
                                        val newEntry2 = HashMap<String, String>()
                                        newEntry2["team_name"] = team.team_name.toString()
                                        databaseref.updateChildren(newEntry2 as Map<String, String>)
                                            .addOnSuccessListener {
                                                var intent =
                                                    Intent(this@JoinTeamActivity, MainActivity::class.java)
                                                startActivity(intent)
                                                finish()
                                            }
                                            .addOnFailureListener {
                                                Toast.makeText(
                                                    this@JoinTeamActivity, "Something went wrong",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            this@JoinTeamActivity, "Something went wrong",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }

                            }


                    }
                    binding.progressBar.visibility = View.GONE
                    binding.teamCode.error = "Entre a valid team code";


                }

                        override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })


        }

    }
}
}