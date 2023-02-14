package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.TableRow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.PresenceAdapter
import com.example.foottricks.adapter.StatsAdapter
import com.example.foottricks.databinding.ActivityEventBinding
import com.example.foottricks.databinding.ActivityMatchStatsBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class MatchStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMatchStatsBinding
    private lateinit var userArrayList: ArrayList<Users>

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseref: DatabaseReference
    private lateinit var statsAdapter: RecyclerView
    private lateinit var adapter: StatsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMatchStatsBinding.inflate(layoutInflater);
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userArrayList = ArrayList<Users>()
        val headerView = LayoutInflater.from(this).inflate(com.example.foottricks.R.layout.table_header_stats, null) as TableRow
        binding.tableLayout.addView(headerView, 0)
        var type = intent.getStringExtra("type").toString()
        var uuid_event = intent.getStringExtra("uuid_event").toString()

        statsAdapter = binding.myRecyclerView;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        statsAdapter.layoutManager = linearLayoutManager;
        adapter = StatsAdapter(userArrayList,type, uuid_event, this);
        statsAdapter.adapter = adapter


        databaseref = FirebaseDatabase.getInstance().getReference(type);
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userArrayList.clear()
                for (dataSnapshot in snapshot.children) {
                    if (type == "matches") {
                        var events = dataSnapshot.getValue(Matches::class.java)!!
                        if (events!!.UUID == intent.getStringExtra("uuid_event").toString()) {
                            if (events.present?.isNotEmpty() == true) {
                                for (e in events.present!!) {
                                    userArrayList.add(e.value)
                                }
                            }
                        }
                    }
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