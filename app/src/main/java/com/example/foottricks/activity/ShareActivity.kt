package com.example.foottricks.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.adapter.PlayerStatAdapter
import com.example.foottricks.databinding.ActivityPlayerStatsBinding
import com.example.foottricks.databinding.ActivityShareBinding
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ShareActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShareBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var playerStatAdapter: RecyclerView
    private lateinit var adapter: PlayerStatAdapter
    private lateinit var userArrayList: ArrayList<Users>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share)
        val intent=Intent().apply {
            this.action=Intent.ACTION_SEND
            this.putExtra(Intent.EXTRA_TEXT,"Share our page")
            this.type="text/plain"
        }
        startActivity(intent)
    }
}