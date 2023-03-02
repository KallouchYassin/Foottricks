package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.PlayerStatAdapter
import com.example.foottricks.adapter.TeamResultAdapter
import com.example.foottricks.databinding.ActivityAttendanceBinding
import com.example.foottricks.databinding.ActivityPlayerStatsBinding
import com.example.foottricks.databinding.ActivityTeamResultBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class TeamResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamResultBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var teamResultAdapter: RecyclerView
    private lateinit var adapter: TeamResultAdapter
    private lateinit var matchesArrayList: ArrayList<Matches>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamResultBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        matchesArrayList = ArrayList<Matches>()


        teamResultAdapter = binding.matchesResult;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        teamResultAdapter.layoutManager = linearLayoutManager;
        adapter = TeamResultAdapter(matchesArrayList, this);
        teamResultAdapter.adapter = adapter
        databaseref = FirebaseDatabase.getInstance().getReference("matches");
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                matchesArrayList.clear()
                for (dataSnapshot in snapshot.children) {
                    var match = dataSnapshot.getValue(Matches::class.java)!!
                    matchesArrayList.add(match)
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