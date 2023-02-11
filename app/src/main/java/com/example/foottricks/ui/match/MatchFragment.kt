package com.example.foottricks.ui.match

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.foottricks.databinding.FragmentMatchBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.ui.calendar.CalendarFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*


class MatchFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentMatchBinding? = null
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference

    private lateinit var teamId: String

    private lateinit var type: String


    private val binding get() = _binding!!

    var day = 0
    var month = 0
    var year = 0
    var hour = 0
    var minute = 0

    var savedDay = 0
    var savedMonth = 0
    var savedYear = 0
    var savedHour = 0
    var savedMinute = 0
    var check = 0

    var DayBegin = 0;
    var minuteBegin = 0;
    var hourBegin = 0;
    var yearBegin = 0;
    var monthBegin = 0;


    var DayEnd = 0;
    var minuteEnd = 0;
    var hourEnd = 0;
    var yearEnd = 0;
    var monthEnd = 0;


    var minuteApp = 0;
    var hourApp = 0;


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMatchBinding.inflate(inflater, container, false)
        val root: View = binding.root
        type = binding.radiobtn3.text.toString()
        binding.matchHomeNot.setOnCheckedChangeListener { radiobtn, isChekced ->

            if (binding.radiobtn3.isChecked) {
                type = binding.radiobtn3.text.toString()

            } else if (binding.radiobtn4.isChecked) {
                type = binding.radiobtn4.text.toString()

            } else {
                type = binding.radiobtn5.text.toString()

            }
        }

        auth = FirebaseAuth.getInstance();
        binding.dateBegin.setOnClickListener {
            getDateTimeCalendar()
            check = 0
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.dateEnd.setOnClickListener {
            getDateTimeCalendar()
            check = 1
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.appTime.setOnClickListener {
            getDateTimeCalendar()
            check = 2
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
        databaseref =
            FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid);

        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //retrieving the user name
                teamId =
                    dataSnapshot.child("team").getValue(String::class.java).toString()

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

        binding.btnValidate.setOnClickListener {
            if (binding.opponent.text!!.isEmpty() || binding.dayCh.text!!.isEmpty() || binding.matchPlaceEdit.text!!.isEmpty()) {
                if (binding.opponent.text!!.isEmpty()) {


                    binding.opponent.error = "Entre opponent";
                }
                if (binding.dayCh.text!!.isEmpty()) {
                    binding.dayCh.error = "Entre championship day";
                }
                if (binding.matchPlaceEdit.text!!.isEmpty()) {
                    binding.matchPlaceEdit.error = "Entre a place";
                }
                Toast.makeText(requireContext(), "Enter valid details", Toast.LENGTH_SHORT).show();

            } else {

                var date: Date = Date(yearBegin, monthBegin, DayBegin, hourBegin, minuteBegin)
                var dateEnd: Date = Date(yearEnd, monthEnd, DayEnd, hourEnd, minuteEnd)
                val uuid = UUID.randomUUID().toString()

                val event: Matches = Matches(
                    binding.opponent.text.toString(),
                    binding.dayCh.text.toString(),
                    date,
                    dateEnd, minuteApp.toString(), hourApp.toString(),
                    binding.matchPlaceEdit.text.toString(),
                    type,
                    teamId,uuid
                )

                database = FirebaseDatabase.getInstance()
                database.reference.child("matches").child(uuid)
                    .setValue(event).addOnCompleteListener {

                        Log.d("reussi", "oui")
                    }


                var intent = Intent(requireContext(), CalendarFragment::class.java)
                startActivity(intent)
            }
        }



        return root
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute = cal.get(Calendar.MINUTE)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year
        getDateTimeCalendar()
        TimePickerDialog(requireContext(), this, hour, minute, true).show()
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute
        if (check == 0) {
            minuteBegin = savedMinute
            hourBegin = savedHour
            yearBegin = savedYear
            DayBegin = savedDay;
            monthBegin = savedMonth
            binding.dateBegin.hint = "$savedDay-$savedMonth-$savedYear at $savedHour:$savedMinute";

        } else if (check == 1) {
            minuteEnd = savedMinute
            hourEnd = savedHour
            yearEnd = savedYear
            DayEnd = savedDay;
            monthEnd = savedMonth
            binding.dateEnd.hint = "$savedDay-$savedMonth-$savedYear at $savedHour:$savedMinute";

        } else {
            minuteApp = savedMinute
            hourApp = savedHour

            binding.appTime.hint = "$savedHour:$savedMinute";

        }
    }


}