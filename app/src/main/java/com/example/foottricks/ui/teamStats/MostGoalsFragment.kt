package com.example.foottricks.ui.teamStats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.activity.DisscusionActivity
import com.example.foottricks.adapter.MostGoalsAdapter
import com.example.foottricks.adapter.PlayerStatAdapter
import com.example.foottricks.databinding.FragmentMostGoalsBinding
import com.example.foottricks.databinding.FragmentTeamStatsBinding
import com.example.foottricks.model.Team
import com.example.foottricks.model.Users
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso


class MostGoalsFragment : Fragment() {

    private var _binding: FragmentMostGoalsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var mostGoalsAdapter: RecyclerView
    private lateinit var adapter: MostGoalsAdapter
    private lateinit var userArrayList: ArrayList<Users>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMostGoalsBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        val root: View = binding.root

        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userArrayList = ArrayList<Users>()

        var user = Users();
        var user_uid = auth.currentUser!!.uid

        mostGoalsAdapter = binding.recyclerViewMostGoals;
        var linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.stackFromEnd = true
        mostGoalsAdapter.layoutManager = linearLayoutManager;
        adapter = MostGoalsAdapter(userArrayList, requireContext());
        mostGoalsAdapter.adapter = adapter
        databaseref = FirebaseDatabase.getInstance().getReference("users");



        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    var users = dataSnapshot.getValue(Users::class.java)!!
                    if (users.uuid == user_uid) {
                        user = users
                    }
                }
                test(user)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return root;
    }

    private fun test(user2: Users) {
        databaseref = FirebaseDatabase.getInstance().getReference("users");


        databaseref.addValueEventListener(object : ValueEventListener {
            var goals = 0
            var u = Users()
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    var users = dataSnapshot.getValue(Users::class.java)!!
                    if (users.teamId == user2.teamId) {
                        if (users.goals!!.toInt() > goals) {
                            goals = users.goals!!.toInt()
                            u = users
                        }
                    }
                }
                binding.mostGoalsName.text = u.lastname + " " + u.firstname
                Picasso.get().load(u.imageUri).into(binding.mostGoalsImg)
                databaseref.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        userArrayList.clear()
                        for (dataSnapshot in snapshot.children) {
                            var user = dataSnapshot.getValue(Users::class.java)!!
                            if (user.teamId == user2.teamId) {
                                if (user != u) {
                                    userArrayList.add(user)

                                }

                            }
                        }
                        adapter.notifyDataSetChanged();

                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }



}