package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foottricks.databinding.ActivityAttendanceBinding
import com.example.foottricks.databinding.ActivityPlayerStatsBinding
import com.example.foottricks.databinding.ActivityTeamResultBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TeamResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamResultBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamResultBinding.inflate(layoutInflater)

        setContentView(binding.root)    }
}