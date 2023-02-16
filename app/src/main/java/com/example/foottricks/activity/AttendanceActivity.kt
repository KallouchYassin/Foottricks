package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebView
import android.widget.TableRow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.adapter.AttendanceAdapter
import com.example.foottricks.adapter.StatsAdapter
import com.example.foottricks.databinding.ActivityAttendanceBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class AttendanceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAttendanceBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var attendanceAdapter: RecyclerView
    private lateinit var adapter: AttendanceAdapter
    private lateinit var userArrayList: ArrayList<Users>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAttendanceBinding.inflate(layoutInflater)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userArrayList = ArrayList<Users>()


        attendanceAdapter = binding.attendanceRecyclerview;
        var linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        attendanceAdapter.layoutManager = linearLayoutManager;
        adapter = AttendanceAdapter(userArrayList, this);
        attendanceAdapter.adapter = adapter


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