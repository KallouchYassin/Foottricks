package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.AttendanceAdapter
import com.example.foottricks.adapter.LeagueAdapter
import com.example.foottricks.databinding.ActivityAttendanceBinding
import com.example.foottricks.databinding.ActivityLeagueBinding
import com.example.foottricks.model.Team
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class LeagueActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLeagueBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
   private lateinit var leagueAdapter: RecyclerView
    private lateinit var adapter: LeagueAdapter
    private lateinit var teamArrayList: ArrayList<Team>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLeagueBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        teamArrayList = ArrayList<Team>()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "League Standings"

        leagueAdapter = binding.attendanceRecyclerview;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        leagueAdapter.layoutManager = linearLayoutManager;
        adapter = LeagueAdapter(teamArrayList, this);
        leagueAdapter.adapter = adapter
        databaseref = FirebaseDatabase.getInstance().getReference("team");
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                teamArrayList.clear()
                for (dataSnapshot in snapshot.children) {
                    var team = dataSnapshot.getValue(Team::class.java)!!
                    teamArrayList.add(team)
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