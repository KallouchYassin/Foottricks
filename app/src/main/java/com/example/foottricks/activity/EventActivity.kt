package com.example.foottricks.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.foottricks.R
import com.example.foottricks.databinding.ActivityEventBinding
import com.example.foottricks.databinding.ActivityLogin2Binding
import com.example.foottricks.ui.calendar.CalendarFragment
import com.example.foottricks.ui.match.MatchFragment
import com.example.foottricks.ui.training.TrainingFragment

class EventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEventBinding.inflate(layoutInflater);
        setContentView(binding.root);
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Add event"
        replaceFragment(MatchFragment())
        binding.bottomNavigationView.setOnItemSelectedListener{

            when(it.itemId){
                R.id.match->replaceFragment(MatchFragment())



                R.id.training->replaceFragment(TrainingFragment())
           else->{

           }
            }
            true
        }

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // navigate back to the previous activity
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun replaceFragment(fragment: Fragment){
        val fragmentManager=supportFragmentManager
        val fragmentTransaction=fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
}