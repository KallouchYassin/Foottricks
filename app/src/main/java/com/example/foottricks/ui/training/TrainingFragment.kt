package com.example.foottricks.ui.training

import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.foottricks.R
import com.example.foottricks.databinding.FragmentTrainingBinding
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Trainings
import com.example.foottricks.model.Users
import com.example.foottricks.ui.calendar.CalendarAdapter
import com.example.foottricks.ui.calendar.CalendarFragment
import com.google.android.gms.dynamic.SupportFragmentWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class TrainingFragment : Fragment(), DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener {

    private var _binding: FragmentTrainingBinding? = null
    private val binding get() = _binding!!
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseref: DatabaseReference
    private lateinit var teamId: String
    private lateinit var listOfDays: ArrayList<Int>
    var uList=kotlin.collections.HashMap<String,Users>()


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

    var minuteBeginApp = 0;
    var hourBeginApp = 0;

    var minuteEndApp = 0;
    var hourEndApp = 0;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTrainingBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = FirebaseAuth.getInstance();
        databaseref =
            FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid);



        binding.trainingBegin.setOnClickListener {
            getDateTimeCalendar()
            check = 0
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.trainingEnd.setOnClickListener {
            getDateTimeCalendar()
            check = 1
            DatePickerDialog(requireContext(), this, year, month, day).show()
        }
        binding.trainingappTime.setOnClickListener {
            getDateTimeCalendar()
            check = 2
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
        binding.hourBegin.setOnClickListener {
            getDateTimeCalendar()
            check = 3
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
        binding.hourEnd.setOnClickListener {
            getDateTimeCalendar()
            check = 4
            TimePickerDialog(requireContext(), this, hour, minute, true).show()
        }
        var databaseRef=  FirebaseDatabase.getInstance().getReference("users")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val users = dataSnapshot.getValue(Users::class.java)
                    uList.put(users!!.uuid.toString(),users!!)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        binding.checkboxReccurency.setOnCheckedChangeListener { buttonView, isChecked ->


            if (isChecked) {
                binding.layoutRecurrent.visibility = View.VISIBLE;
                binding.layoutNotRecurrent.visibility = View.GONE;
            } else {
                binding.layoutRecurrent.visibility = View.GONE;
                binding.layoutNotRecurrent.visibility = View.VISIBLE;
            }
            Log.d("reccuuucu", isChecked.toString())

        }
        binding.dayOfRecurrence.setOnClickListener {

            var dialog: Dialog = Dialog(requireContext(), R.style.Dialoge)
            dialog.setContentView(com.example.foottricks.R.layout.dialog_recurrency_layout);

            var mondayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.monday)
            var tuesdayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.tuesday)
            var wednesdayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.wednesday)
            var thursdayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.thursday)
            var fridayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.friday)
            var saturdayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.saturday)
            var sundayBtn = dialog.findViewById<CheckBox>(com.example.foottricks.R.id.sunday)
            var okBtn = dialog.findViewById<TextView>(com.example.foottricks.R.id.ok_btn)
            okBtn.setOnClickListener {
                listOfDays = ArrayList<Int>()
                if (mondayBtn.isChecked) {
                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()} ${mondayBtn.text}";
                    listOfDays.add(2)
                }
                if (tuesdayBtn.isChecked) {

                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()}  ${tuesdayBtn.text}";
                    listOfDays.add(3)
                }
                if (wednesdayBtn.isChecked) {
                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()}  ${wednesdayBtn.text}";
                    listOfDays.add(4)
                }
                if (thursdayBtn.isChecked) {
                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()}  ${thursdayBtn.text}";
                    listOfDays.add(5)
                }
                if (fridayBtn.isChecked) {
                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()}  ${fridayBtn.text}";
                    listOfDays.add(6)
                }
                if (saturdayBtn.isChecked) {
                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()}  ${saturdayBtn.text}";
                    listOfDays.add(7)
                }
                if (sundayBtn.isChecked) {
                    binding.dayOfRecurrence.text =
                        "${binding.dayOfRecurrence.text.toString()}  ${sundayBtn.text}";
                    listOfDays.add(1)
                } else if (!mondayBtn.isChecked && !tuesdayBtn.isChecked && !wednesdayBtn.isChecked && !thursdayBtn.isChecked && !fridayBtn.isChecked && !saturdayBtn.isChecked && !sundayBtn.isChecked) {
                    binding.dayOfRecurrence.text = "Day of recurrence"

                }
                dialog.dismiss()
            }
            dialog.show();
        }
        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //retrieving the user name
                teamId =
                    dataSnapshot.child("team").getValue(String::class.java).toString()
                Log.d("reussi", "oui4")
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })
        binding.btnValidateTraining.setOnClickListener {

            if (binding.trainingName.text!!.isEmpty() || binding.trainingDescription.text!!.isEmpty() || binding.matchPlaceEdit.text!!.isEmpty()) {
                if (binding.trainingName.text!!.isEmpty()) {


                    binding.trainingName.error = "Entre name for the training";
                }
                if (binding.trainingDescription.text!!.isEmpty()) {
                    binding.trainingDescription.error = "Entre description for the training";
                }
                if (binding.matchPlaceEdit.text!!.isEmpty()) {
                    binding.matchPlaceEdit.error = "Entre a place";
                }
                Toast.makeText(requireContext(), "Enter valid details", Toast.LENGTH_SHORT).show();

            } else {

                if (binding.checkboxReccurency.isChecked) {
                    val events = mutableListOf<String>()
                    val calendar = Calendar.getInstance()
                    calendar.time = Date(2023,12,31)
                    val endYear = calendar.get(Calendar.YEAR)
                    calendar.time = Date()
                    while (calendar.get(Calendar.YEAR) <= endYear) {
                        val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
                        if (dayOfWeek in listOfDays) {
                            val uuid = UUID.randomUUID().toString()
                            val training = Trainings(
                                binding.trainingName.text.toString(),
                                binding.trainingDescription.text.toString(),
                                calendar.time,
                                null,
                                null,
                                null,
                                listOfDays,
                                minuteBeginApp.toString(),
                                hourBeginApp.toString(),
                                minuteEndApp.toString(),
                                hourEndApp.toString(),
                                binding.matchPlaceEdit.text.toString(),
                                teamId,uuid,uList
                            )

                            database = FirebaseDatabase.getInstance()
                            database.reference.child("trainings").push()
                                .setValue(training).addOnCompleteListener {

                                    Log.d("reussi", "oui")
                                }

                        }
                        calendar.add(Calendar.DATE, 1)
                    }

                } else {


                    var date: Date = Date(yearBegin, monthBegin, DayBegin, hourBegin, minuteBegin)
                    var dateEnd: Date = Date(yearEnd, monthEnd, DayEnd, hourEnd, minuteEnd)
                    val uuid = UUID.randomUUID().toString()
                    val training: Trainings = Trainings(
                        binding.trainingName.text.toString(),
                        binding.trainingDescription.text.toString(),
                        date,
                        dateEnd, minuteApp.toString(), hourApp.toString(),
                        null, null, null, null, null,
                        binding.matchPlaceEdit.text.toString(),
                        teamId, uuid,uList
                    )

                    database = FirebaseDatabase.getInstance()
                    database.reference.child("trainings").child(uuid)
                        .setValue(training).addOnCompleteListener {

                            Log.d("reussi", "oui")
                        }


                }
                var intent = Intent(this.requireContext(), CalendarFragment::class.java)

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
            binding.trainingBegin.hint =
                "$savedDay-$savedMonth-$savedYear at $savedHour:$savedMinute";

        } else if (check == 1) {
            minuteEnd = savedMinute
            hourEnd = savedHour
            yearEnd = savedYear
            DayEnd = savedDay;
            monthEnd = savedMonth
            binding.trainingEnd.hint =
                "$savedDay-$savedMonth-$savedYear at $savedHour:$savedMinute";

        } else if (check == 2) {
            minuteApp = savedMinute
            hourApp = savedHour

            binding.trainingappTime.hint = "$savedHour:$savedMinute";

        } else if (check == 3) {
            minuteBeginApp = savedMinute
            hourBeginApp = savedHour

            binding.hourBegin.hint = "$savedHour:$savedMinute";

        } else if (check == 4) {
            minuteEndApp = savedMinute
            hourEndApp = savedHour

            binding.hourEnd.hint = "$savedHour:$savedMinute";

        }
    }
    fun createRecurringEvents(endDate: Date, daysOfWeek: List<Int>): List<String> {
        val events = mutableListOf<String>()
        val calendar = Calendar.getInstance()
        calendar.time = endDate
        val endYear = calendar.get(Calendar.YEAR)
        calendar.time = Date()
        while (calendar.get(Calendar.YEAR) <= endYear) {
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek in daysOfWeek) {
                events.add(calendar.time.toString())
            }
            calendar.add(Calendar.DATE, 1)
        }
        return events
    }


}