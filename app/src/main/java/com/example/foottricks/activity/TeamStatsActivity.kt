package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.foottricks.adapter.VPAdapter
import com.example.foottricks.databinding.ActivityTeamStatsBinding
import com.example.foottricks.ui.teamStats.MostAssistFragment
import com.example.foottricks.ui.teamStats.MostGoalsFragment
import com.example.foottricks.ui.teamStats.MostRcFragment
import com.example.foottricks.ui.teamStats.TeamStatsFragment

class TeamStatsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamStatsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamStatsBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Team Statistics"

        binding.tableLayout.setupWithViewPager(binding.viewpager)

        val fragments = listOf(TeamStatsFragment(), MostAssistFragment(),MostGoalsFragment(),MostRcFragment())
        val adapter = VPAdapter(supportFragmentManager, fragments)
        binding.viewpager.adapter = adapter
        setContentView(binding.root)
    }


}