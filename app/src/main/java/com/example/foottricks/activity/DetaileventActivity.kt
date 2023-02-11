package com.example.foottricks.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.foottricks.R
import com.example.foottricks.databinding.ActivityDetaileventBinding
import com.example.foottricks.databinding.ActivityDisscusionBinding

class DetaileventActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetaileventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetaileventBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var receiver = intent.getStringExtra("matches")
        Log.d("kekette", receiver.toString())
        if (receiver == "true") {

            binding.cardDescription.visibility = View.GONE
            binding.cardTraining.visibility = View.GONE

            binding.myTeam.text = intent.getStringExtra("teamID")
            binding.oppTeam.text = intent.getStringExtra("opp")
            binding.chDay.text = "<<J${intent.getStringExtra("ch_day")}>> : ${intent.getStringExtra("side")} "
            binding.beginMatch.text = intent.getStringExtra("begin_date")

        } else {
            binding.cardManOfMatch.visibility = View.GONE
            binding.cardTraining.visibility = View.VISIBLE
            binding.cardStats.visibility = View.GONE
            binding.cardOpponents.visibility = View.GONE
            binding.cardNote.visibility = View.GONE
            binding.cardMatchInfo.visibility = View.GONE
            binding.numberAbsent.text="2";
            binding.numberPresent.text="4";
            binding.trainingDesc.text=intent.getStringExtra("description");
            if (intent.getStringExtra("recurrency")=="true"){
                binding.trainingName.text="${intent.getStringExtra("tr_name")} : Training every  ${intent.getStringExtra("listOfDays")}\n at ${intent.getStringExtra("recc_time_hour")}:${intent.getStringExtra("recc_time_minute")}";


            }
            else{
                binding.trainingName.text="${intent.getStringExtra("tr_name")} : Training of ${intent.getStringExtra("begin_date")}\n at ${intent.getStringExtra("app_hour")}:${intent.getStringExtra("app_minute")}";


            }


        }


    }
}