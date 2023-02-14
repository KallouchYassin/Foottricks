package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.MessageAdapter
import com.example.foottricks.adapter.PresenceAdapter
import com.example.foottricks.databinding.ActivityPresenceBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Messages
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class PresenceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPresenceBinding
    private lateinit var summonArrayList: ArrayList<Users>
    private lateinit var presentArrayList: ArrayList<Users>
    private lateinit var absentArrayList: ArrayList<Users>

    private lateinit var database: FirebaseDatabase
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseref: DatabaseReference


    private lateinit var presenceAdapter: RecyclerView
    private lateinit var adapter: PresenceAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPresenceBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        summonArrayList=ArrayList<Users>()
        presentArrayList=ArrayList<Users>()
        absentArrayList=ArrayList<Users>()
        var type = intent.getStringExtra("type").toString()
        var uuid_event =intent.getStringExtra("uuid_event").toString()

        presenceAdapter = binding.presenceAdapter;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        presenceAdapter.layoutManager = linearLayoutManager;
        adapter = PresenceAdapter(summonArrayList,presentArrayList,absentArrayList,type ,uuid_event,this);
        presenceAdapter.adapter = adapter


        databaseref = FirebaseDatabase.getInstance().getReference(type);
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                summonArrayList.clear()
                presentArrayList.clear()
                absentArrayList.clear()
                for (dataSnapshot in snapshot.children) {
                    if (type == "matches") {

                        var events = dataSnapshot.getValue(Matches::class.java)!!
                        if (events!!.UUID == intent.getStringExtra("uuid_event").toString()) {
                            if (events.summon?.isNotEmpty() == true) {
                                for (e in events.summon!!)
                            {
                                summonArrayList.add(e.value)
                            }
                            }
                            if (events.absent?.isNotEmpty() == true){
                                Log.d("hama","fams")
                                for (e in events.absent!!)
                                {
                                    absentArrayList.add(e.value)
                                }
                            }
                            if (events.present?.isNotEmpty() == true){
                                Log.d("hama","fams")

                                for (e in events.present!!)
                                {
                                    presentArrayList.add(e.value)
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