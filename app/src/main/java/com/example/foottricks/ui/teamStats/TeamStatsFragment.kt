package com.example.foottricks.ui.teamStats

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.foottricks.R
import com.example.foottricks.databinding.ActivityTeamStatsBinding
import com.example.foottricks.databinding.FragmentTeamBinding
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


class TeamStatsFragment : Fragment() {
    private var _binding: FragmentTeamStatsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTeamStatsBinding.inflate(inflater, container, false)
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        val root: View = binding.root

        var user_uid= auth.currentUser!!.uid
        var user= Users();

        databaseref = FirebaseDatabase.getInstance().getReference("users");

        databaseref.addValueEventListener(object : ValueEventListener {
            lateinit var team: Team
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    var users = dataSnapshot.getValue(Users::class.java)!!
                    if(users.uuid==user_uid)
                    {
                        user=users
                    }
                }
                test(user)

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


        return root

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun test(user:Users) {

        databaseref = FirebaseDatabase.getInstance().getReference("teams").child(user.teamId.toString());
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var team=snapshot.getValue(Team::class.java)
                var entryList = ArrayList<PieEntry>()

                Log.d("real", team!!.team_losses.toString())
                if(team!!.team_losses=="0" && team!!.team_draws=="0" && team!!.team_wins=="0")
                {
                    entryList.add(PieEntry(500F, "Wins"))
                    entryList.add(PieEntry( 300F, "Draws"))
                    entryList.add(PieEntry(100F, "Losses"))
                }
                else{
                    entryList.add(PieEntry(team.team_wins?.toFloat() ?: 500F, "Wins"))
                    entryList.add(PieEntry(team.team_draws?.toFloat() ?: 300F, "Draws"))
                    entryList.add(PieEntry(team.team_losses?.toFloat() ?: 100F, "Losses"))

                }

                var pieDataSet: PieDataSet = PieDataSet(entryList, "Pie list data")
                var pieData: PieData = PieData(pieDataSet)
                pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS.toList())
                pieDataSet.setValueTextColor(resources.getColor(R.color.yellow))
                pieData.setValueTextSize(12f)
                binding.pieChart.data = pieData
                var test= Description()
                test.text="Wins and losses of team"
                binding.pieChart.description= test
                binding.pieChart.invalidate()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })    }



}