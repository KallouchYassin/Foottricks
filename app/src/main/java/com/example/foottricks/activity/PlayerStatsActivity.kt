package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.LeagueAdapter
import com.example.foottricks.adapter.PlayerStatAdapter
import com.example.foottricks.databinding.ActivityAttendanceBinding
import com.example.foottricks.databinding.ActivityLeagueBinding
import com.example.foottricks.databinding.ActivityPlayerStatsBinding
import com.example.foottricks.model.Team
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class PlayerStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPlayerStatsBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var playerStatAdapter: RecyclerView
    private lateinit var adapter: PlayerStatAdapter
    private lateinit var userArrayList: ArrayList<Users>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerStatsBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userArrayList = ArrayList<Users>()


        playerStatAdapter = binding.attendanceRecyclerview;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        playerStatAdapter.layoutManager = linearLayoutManager;
        adapter = PlayerStatAdapter(userArrayList, this);
        playerStatAdapter.adapter = adapter
        databaseref = FirebaseDatabase.getInstance().getReference("users");
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                for (dataSnapshot in snapshot.children) {
                    var user = dataSnapshot.getValue(Users::class.java)!!
                    userArrayList.add(user)
                }
                adapter.notifyDataSetChanged();
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        setContentView(binding.root)
    }
}