package com.example.foottricks.activity

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.adapter.DialogAdapter
import com.example.foottricks.adapter.DialogMotmAdapter
import com.example.foottricks.databinding.ActivityDetaileventBinding
import com.example.foottricks.databinding.ActivityDisscusionBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Trainings
import com.example.foottricks.model.Users
import com.example.foottricks.ui.calendar.CalendarAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.squareup.picasso.Picasso

class DetaileventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetaileventBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    var type = ""
    var event_uuid = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetaileventBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance()
        event_uuid=intent.getStringExtra("uuid").toString();
        auth = FirebaseAuth.getInstance();
        val currentUserUid = auth.currentUser!!.uid
        type = intent.getStringExtra("type").toString()
        databaseref = FirebaseDatabase.getInstance().getReference(type);
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    if (type == "matches") {
                        var events = dataSnapshot.getValue(Matches::class.java)!!
                        if (events!!.UUID == intent.getStringExtra("uuid").toString()) {
//
                            if (events.ratings != null) {
                                for (e in events.ratings!!) {

                                    if (e.key == currentUserUid) {
                                        binding.ratingMatch.visibility = View.GONE
                                        binding.ratingbar.rating = e.value.toFloat()
                                        binding.txtRating.text = "You gave a ${e.value.toFloat()}"
                                    }

                                }
                                Log.d("rating", events.ratings.toString())
                            }
                        }
                    }

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    if (type == "matches") {
                        var events = dataSnapshot.getValue(Matches::class.java)!!
                        if (events!!.UUID == intent.getStringExtra("uuid").toString()) {
                            if (events.motm != null) {
                                for (e in events.motm!!) {
                                    if (e.key == currentUserUid) {
                                        binding.btnMotm.visibility = View.GONE
                                        Picasso.get().load(e.value.imageUri).into(binding.imgMotm)
                                        binding.txtMotm.text =
                                            "You have voted for ${e.value.lastname} ${e.value.firstname}"
                                    }
                                }
                                Log.d("rating", events.ratings.toString())
                            }
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        var receiver = intent.getStringExtra("type")
        if (receiver == "matches") {
            binding.cardDescription.visibility = View.GONE
            binding.cardTraining.visibility = View.GONE
            binding.numberAbsent.text = intent.getStringExtra("absent") ?: "0";
            binding.numberPresent.text = intent.getStringExtra("present") ?: "0";
            binding.numberPending.text = intent.getStringExtra("summon") ?: "0";
            binding.myTeam.text = intent.getStringExtra("teamID")
            binding.oppTeam.text = intent.getStringExtra("opp")
            binding.chDay.text =
                "<<J${intent.getStringExtra("ch_day")}>> : ${intent.getStringExtra("side")} "
            binding.beginMatch.text = intent.getStringExtra("begin_date")

        }
        else {
            binding.cardManOfMatch.visibility = View.GONE
            binding.cardTraining.visibility = View.VISIBLE
            binding.cardStats.visibility = View.GONE
            binding.cardOpponents.visibility = View.GONE
            binding.cardNote.visibility = View.GONE
            binding.cardMatchInfo.visibility = View.GONE
            binding.numberAbsent.text = intent.getStringExtra("absent") ?: "0";
            binding.numberPresent.text = intent.getStringExtra("present") ?: "0";
            binding.numberPending.text = intent.getStringExtra("pending") ?: "0";
            binding.trainingDesc.text = intent.getStringExtra("description");
            Log.d("jaune", intent.getStringExtra("btn").toString())
            if (intent.getStringExtra("btn") == "true") {
                binding.btnAbsent.visibility = View.GONE

            } else if (intent.getStringExtra("btn") == "false") {
                binding.btnPresent.visibility = View.GONE
            }
            if (intent.getStringExtra("recurrency") == "true") {
                binding.trainingName.text =
                    "${intent.getStringExtra("tr_name")} : Training every  ${intent.getStringExtra("listOfDays")}\n at ${
                        intent.getStringExtra("recc_time_hour")
                    }:${intent.getStringExtra("recc_time_minute")}";
            } else {
                binding.trainingName.text =
                    "${intent.getStringExtra("tr_name")} : Training of ${intent.getStringExtra("begin_date")}\n at ${
                        intent.getStringExtra("app_hour")
                    }:${intent.getStringExtra("app_minute")}";
            }


        }
        binding.ratingMatch.setOnClickListener {
            databaseref = FirebaseDatabase.getInstance().getReference(type);
            databaseref.child(intent.getStringExtra("uuid").toString()).child("ratings")
                .child(currentUserUid)
                .setValue(binding.ratingbar.rating).addOnCompleteListener {
                    Log.d("reussi", "oui")
                    binding.ratingMatch.visibility = View.GONE
                    binding.txtRating.text = "You gave a ${binding.ratingbar.rating}"
                }
        }
        binding.btnMotm.setOnClickListener {
            var dialog: Dialog = Dialog(this, R.style.Dialoge)
            dialog.setContentView(com.example.foottricks.R.layout.dialog_summoning);
            dialog.setCanceledOnTouchOutside(true);
            var mainEventRecyclerView: RecyclerView
            var adapter: DialogMotmAdapter
            var userArrayList = ArrayList<Users>()
            val currentUserUid = auth.currentUser!!.uid
            var databaseRef: DatabaseReference = database.getReference("matches")
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userArrayList = ArrayList<Users>();
                    for (dataSnapshot in snapshot.children) {

                        val matches = dataSnapshot.getValue(Matches::class.java)
                        if (matches?.summon != null) {
                            for (u in matches.summon) {
                                userArrayList.add(u.value)
                            }
                        }

                    }
                    Log.d("users", userArrayList.toString())
                    mainEventRecyclerView =
                        dialog.findViewById<RecyclerView>(R.id.summoning_recyvlerview)
                    mainEventRecyclerView.layoutManager =
                        LinearLayoutManager(this@DetaileventActivity)
                    adapter = DialogMotmAdapter(
                        userArrayList,
                        this@DetaileventActivity,
                        intent.getStringExtra("uuid").toString(),
                        currentUserUid
                    );
                    mainEventRecyclerView.adapter = adapter;
                    adapter.notifyDataSetChanged();
                    dialog.show()
                    var btn = dialog.findViewById<TextView>(R.id.btnOkSummoning)
                    btn.setOnClickListener {
                         recreate()
                        dialog.dismiss()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }

        binding.cardClick.setOnClickListener {
            val intent = Intent(this@DetaileventActivity, PresenceActivity::class.java)
            intent.putExtra("type", type.toString())
            intent.putExtra("uuid_event",event_uuid.toString() )
            startActivity(intent)
        }
        binding.btnStats.setOnClickListener {
            val intent = Intent(this@DetaileventActivity, MatchStatsActivity::class.java)
            intent.putExtra("type", type.toString())
            intent.putExtra("uuid_event",event_uuid.toString() )
            startActivity(intent)
        }

        setContentView(binding.root)

    }
}