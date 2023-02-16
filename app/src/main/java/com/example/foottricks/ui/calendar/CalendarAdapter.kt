package com.example.foottricks.ui.calendar

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.activity.DetaileventActivity
import com.example.foottricks.activity.DisscusionActivity
import com.example.foottricks.adapter.DialogAdapter
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Trainings
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class CalendarAdapter(
    private val matchesList: ArrayList<Matches>,
    private val trainingsList: ArrayList<Trainings>,
    private val calendarFragment: Context
) : RecyclerView.Adapter<CalendarAdapter.Viewholder>() {
    companion object {
        private lateinit var auth: FirebaseAuth
        private lateinit var database: FirebaseDatabase
        private lateinit var view2: View


    }

    override fun getItemCount(): Int {
        return matchesList.size + trainingsList.size;
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var place: TextView = itemView.findViewById(R.id.place)
        var app_time: TextView = itemView.findViewById(R.id.app_time)

        var opponents: TextView = itemView.findViewById(R.id.versusTxt)

        var day: TextView = itemView.findViewById(R.id.day_txt)
        var writtenDay: TextView = itemView.findViewById(R.id.writtenDay_txt)

        var hourOfmatch: TextView = itemView.findViewById(R.id.hourOFmatch)
        var month_txt: TextView = itemView.findViewById(R.id.month_txt)
        var championship_day: TextView = itemView.findViewById(R.id.championship_day)
        var btnPresent: TextView = itemView.findViewById(R.id.btnPresent)
        var btnAbsent: TextView = itemView.findViewById(R.id.btnAbsent)
        var btnConvoc: TextView = itemView.findViewById(R.id.btn_summoning_players)
        val layout1: LinearLayout = view2.findViewById(R.id.layoutListFinished)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        val view =
            LayoutInflater.from(calendarFragment).inflate(R.layout.item_events_row, parent, false)
        view2 =
            LayoutInflater.from(calendarFragment).inflate(R.layout.fragment_calendar, parent, false)

        return Viewholder(view);
    }


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        val intent = Intent(calendarFragment, DetaileventActivity::class.java)
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        val currentUserUid = auth.currentUser!!.uid
        var user = Users()
        var uList=kotlin.collections.ArrayList<Users>()


        var dayFormat: SimpleDateFormat = SimpleDateFormat("EEE")
        var monthFormat: SimpleDateFormat = SimpleDateFormat("MMM")
        var yearFormat: SimpleDateFormat = SimpleDateFormat("EEEE dd MMMM ")
        var uuid: String
        var type="matches"
        if (position < matchesList.size) {
            holder.btnPresent.visibility = View.VISIBLE;
            holder.btnAbsent.visibility = View.VISIBLE;
            var matches: Matches = matchesList.get(position) as Matches
            var dateFormat2: Date = matches.begin_date!!
            uuid = matchesList.get(position).UUID.toString()
            holder.place.text = "${matches.match_place}";
            holder.app_time.text =
                "Appointment Time at ${matches.appointment_time_hour}:${matches.appointment_time_minute}";
            holder.day.text = SimpleDateFormat("dd").format(matches.begin_date);
            holder.writtenDay.text = dayFormat.format(matches.begin_date);
            holder.opponents.text = "${matches.teamId} VS ${matches.opponent} ";
            holder.championship_day.text =
                "<<J${matches.championship_day}>> : ${matches.match_side}";
            holder.hourOfmatch.text = SimpleDateFormat("HH:mm").format(matches.begin_date);
            holder.month_txt.text = monthFormat.format(matches.begin_date);
            intent.putExtra("opp", matches.opponent)
            intent.putExtra("teamID", matches.teamId)
            intent.putExtra("begin_date", matches.begin_date.toString())
            intent.putExtra("uuid", matches.UUID)
            intent.putExtra("side", matches.match_side)
            intent.putExtra("place", matches.match_place)
            if(matches.present!= null ) {
                intent.putExtra("present", matches.present?.size.toString() ?: 0)
            } else{intent.putExtra("present",  0) }
            if(matches.absent!= null ) {
                intent.putExtra("absent", matches.absent?.size.toString() ?: 0)

            }else{intent.putExtra("absent",  0) }
            if(matches.summon!= null ) { intent.putExtra("summon", matches.summon?.size.toString() ?: 0) }else{intent.putExtra("pending",  0) }

            intent.putExtra("ch_day", matches.championship_day)
            intent.putExtra("type", "matches")

        }
        else {

            type="trainings"
            holder.btnPresent.visibility = View.VISIBLE;
            holder.btnAbsent.visibility = View.VISIBLE;
            holder.btnConvoc.visibility = View.GONE;
            uuid = trainingsList.get(position - matchesList.size).UUID.toString()
            var trainings: Trainings = trainingsList.get(position - matchesList.size) as Trainings
            intent.putExtra("uuid", trainings.UUID)
            intent.putExtra("type", "trainings")
            if(trainings.present!= null ) {
                intent.putExtra("present", trainings.present?.size.toString() ?: 0)
            } else{intent.putExtra("present",  0) }
            if(trainings.absent!= null ) {
                intent.putExtra("absent", trainings.absent?.size.toString() ?: 0)

            }else{intent.putExtra("absent",  0) }
            if(trainings.pending!= null ) { intent.putExtra("pending", trainings.pending?.size.toString() ?: 0) }else{intent.putExtra("pending",  0) }

            intent.putExtra("teamID", trainings.teamId)
            intent.putExtra("tr_name", trainings.training_name)
            intent.putExtra("description", trainings.training_description)
            if(trainings.present!= null && trainings.present?.contains(currentUserUid) == true)
            {
                intent.putExtra("btn", "true")
                holder.btnAbsent.visibility=View.GONE
            }
            else if(trainings.absent!= null && trainings.absent?.contains(currentUserUid) == true){
                intent.putExtra("btn", "false")

                holder.btnPresent.visibility=View.GONE
            }
            else{
                intent.putExtra("btn", "pen")

            }

            if (trainings.begin_date != null) {
                var dateFormat2: Date = trainings.begin_date!!
                holder.app_time.text =
                    "Appointment Time at ${trainings.appointment_time_hour}:${trainings.appointment_time_minute}";
                holder.opponents.text = "Training of ${yearFormat.format(trainings.begin_date)}";

                holder.hourOfmatch.text =
                    dateFormat2.hours.toString() + ":" + dateFormat2.minutes.toString();
                holder.day.text = dateFormat2.day.toString();
                holder.writtenDay.text = dayFormat.format(trainings.begin_date);
                holder.month_txt.text = monthFormat.format(trainings.begin_date);

                intent.putExtra("begin_date", trainings.begin_date.toString())
                intent.putExtra("tr_place", trainings.match_place)
                intent.putExtra("app_hour", trainings.appointment_time_hour)
                intent.putExtra("app_minute", trainings.appointment_time_minute)
                intent.putExtra("recurrency", "false")


            } else {
                holder.opponents.text = "Recurrent training of Monday ";
                holder.app_time.text =
                    "From ${trainings.recurr_time_begin_hour}:${trainings.recurr_time_begin_minute} until ${trainings.recurr_time_end_hour}:${trainings.recurr_time_end_minute}  ";
                holder.day.text = "Every";
                holder.hourOfmatch.text = "";
                holder.writtenDay.text = "Mon";
                holder.month_txt.text = "";
                intent.putExtra("recurrency", "true")

                intent.putExtra("recc_time_hour", trainings.recurr_time_begin_hour)
                intent.putExtra("recc_time_minute", trainings.recurr_time_begin_minute)
                intent.putExtra("listOfDays", trainings.listOfdays)

            }


            holder.place.text = "${trainings.match_place}";

            holder.championship_day.text = "";

        }

        var databaseRef: DatabaseReference = CalendarAdapter.database.getReference("users")
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val users = dataSnapshot.getValue(Users::class.java)
                    if (users!!.uuid == currentUserUid) {
                        user = users;

                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

        holder.btnConvoc.setOnClickListener {
            var dialog: Dialog = Dialog(calendarFragment, R.style.Dialoge)
            dialog.setContentView(com.example.foottricks.R.layout.dialog_summoning);
            dialog.setCanceledOnTouchOutside(true);

            var mainEventRecyclerView: RecyclerView
            var adapter: DialogAdapter
            var userArrayList = ArrayList<Users>()

            val currentUserUid = auth.currentUser!!.uid
            var databaseRef: DatabaseReference = database.getReference("users")


            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    userArrayList = ArrayList<Users>();
                    for (dataSnapshot in snapshot.children) {

                        val users = dataSnapshot.getValue(Users::class.java)
                        if (users!!.uuid != currentUserUid) {
                            userArrayList.add(users!!)
                        }

                    }

                    mainEventRecyclerView =
                        dialog.findViewById<RecyclerView>(R.id.summoning_recyvlerview)
                    mainEventRecyclerView.layoutManager = LinearLayoutManager(calendarFragment)
                    adapter = DialogAdapter(userArrayList, calendarFragment, uuid);
                    mainEventRecyclerView.adapter = adapter;
                    adapter.notifyDataSetChanged();
                    dialog.show()
                    var btn = dialog.findViewById<TextView>(R.id.btnOkSummoning)


                    btn.setOnClickListener {
                        // Handle the button click event
                        dialog.dismiss()
                    }


                }


                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
        }
        holder.itemView.setOnClickListener {
            calendarFragment.startActivity(intent);

        }


        holder.btnPresent.setOnClickListener {
            database.reference.child("users").child(user.uuid.toString()).child("present")
                .setValue(user.present!! +1)
            var databaseRef: DatabaseReference = CalendarAdapter.database.getReference("users")
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val users = dataSnapshot.getValue(Users::class.java)
                        Log.d("reussi", users.toString())
                        if (users!!.uuid == currentUserUid) {
                            user = users;
                        }
                    }

                    database.reference.child(type).child(uuid).child("present")
                        .child(user.uuid.toString())
                        .setValue(user).addOnCompleteListener {

                        }
                    if (type=="trainings") {
                        database.reference.child(type).child(uuid).child("pending")
                            .child(user.uuid.toString())
                            .removeValue()
                    }else{
                        database.reference.child(type).child(uuid).child("summon")
                            .child(user.uuid.toString())
                            .removeValue()
                    }
                    database.reference.child(type).child(uuid).child("absent")
                        .child(user.uuid.toString())
                        .removeValue()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }



            })

            notifyDataSetChanged()
        }
        holder.btnAbsent.setOnClickListener {
            database.reference.child("users").child(user.uuid.toString()).child("absent")
                .setValue(user.absent!! +1)
            var databaseRef: DatabaseReference = CalendarAdapter.database.getReference("users")
            databaseRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (dataSnapshot in snapshot.children) {
                        val users = dataSnapshot.getValue(Users::class.java)
                        if (users!!.uuid == currentUserUid) {
                            user = users;

                        }
                    }
//                    var present= user.present?.minus(1)
//                    var convoc= user.convocated?.plus(1)

//                    database.reference.child("users").child(user.uuid.toString()).child("present")
//                        .setValue(present).addOnCompleteListener {
//
//                        }
//                    database.reference.child("users").child(user.uuid.toString()).child("convocated")
//                        .setValue(convoc).addOnCompleteListener {
//
//                        }
                    database.reference.child(type).child(uuid).child("absent")
                        .child(user.uuid.toString())
                        .setValue(user).addOnCompleteListener {
                            Log.d("reussi", "oui")
                        }
                    if(type=="trainings") {
                        database.reference.child(type).child(uuid).child("pending")
                            .child(user.uuid.toString())
                            .removeValue()
                    }
                    else{
                        database.reference.child(type).child(uuid).child("summon")
                        .child(user.uuid.toString())
                        .removeValue()
                    }
                    database.reference.child(type).child(uuid).child("present")
                        .child(user.uuid.toString())
                        .removeValue()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }


            })
            notifyDataSetChanged()


        }
    }


}