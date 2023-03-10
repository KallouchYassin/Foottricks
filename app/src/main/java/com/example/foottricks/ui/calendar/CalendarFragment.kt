package com.example.foottricks.ui.calendar

import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.activity.EventActivity
import com.example.foottricks.databinding.FragmentCalendarBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Trainings
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var matchesArrayList: ArrayList<Matches>
    private lateinit var trainingsArrayList: ArrayList<Trainings>
    private lateinit var mainEventRecyclerView: RecyclerView
    private lateinit var adapter: CalendarAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        matchesArrayList = ArrayList<Matches>();
        trainingsArrayList = ArrayList<Trainings>();
        auth = FirebaseAuth.getInstance();
        val currentUser = auth.currentUser
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("users/${currentUser?.uid}")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val userData = snapshot.getValue(Users::class.java)
              if (userData?.role =="player")
              {
                  binding.fab.visibility=View.GONE;
                  binding.btnGetStarted.visibility=View.GONE;
              }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error here
            }
        })


        databaseref = FirebaseDatabase.getInstance().getReference("matches");
        databaseref.addValueEventListener(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onDataChange(snapshot: DataSnapshot) {
                matchesArrayList = ArrayList<Matches>();
                for (dataSnapshot in snapshot.children) {
                    val matches = dataSnapshot.getValue(Matches::class.java)

                    matchesArrayList.add(matches!!)
                }
                if (matchesArrayList.isNotEmpty()) {
                    binding.linearlayoutCalendar.visibility = View.GONE;
                    binding.layoutListToCome.visibility = View.VISIBLE;
                    binding.addButtonLayout.visibility = View.VISIBLE;
                    binding.linearlayoutBtn.visibility = View.GONE;


                    mainEventRecyclerView = binding.eventRecyclerview
                    mainEventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    adapter = CalendarAdapter(matchesArrayList,trainingsArrayList, requireContext());
                    mainEventRecyclerView.adapter = adapter;
                    adapter.notifyDataSetChanged();

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

        databaseref = FirebaseDatabase.getInstance().getReference("trainings");
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                trainingsArrayList = ArrayList<Trainings>();
                for (dataSnapshot in snapshot.children) {
                    val trainings = dataSnapshot.getValue(Trainings::class.java)

                    trainingsArrayList.add(trainings!!)
                }
                if (trainingsArrayList.isNotEmpty()) {
                    binding.linearlayoutCalendar.visibility = View.GONE;
                    binding.layoutListToCome.visibility = View.VISIBLE;
                    binding.addButtonLayout.visibility = View.VISIBLE;
                    binding.linearlayoutBtn.visibility = View.GONE;


                    mainEventRecyclerView = binding.eventRecyclerview
                    mainEventRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                    adapter = CalendarAdapter(matchesArrayList,trainingsArrayList, requireContext());
                    mainEventRecyclerView.adapter = adapter;
                    adapter.notifyDataSetChanged();

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

        binding.btnGetStarted.setOnClickListener {

            var intent = Intent(requireContext(), EventActivity::class.java)
            startActivity(intent)
        }
        binding.fab.setOnClickListener {

            var intent = Intent(requireContext(), EventActivity::class.java)
            startActivity(intent)
        }


        return root;


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}