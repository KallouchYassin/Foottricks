package com.example.foottricks.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import com.example.foottricks.ui.calendar.CalendarAdapter
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class StatsAdapter(
    private val userList: ArrayList<Users>,
    private val type: String,
    private val uuid_event: String,
    private val context: Context
) : RecyclerView.Adapter<StatsAdapter.Viewholder>() {

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var noteCol: TextView = itemView.findViewById(com.example.foottricks.R.id.note_col)
        var avgCol: TextView = itemView.findViewById(com.example.foottricks.R.id.avg_col)
        var assistCol: TextView = itemView.findViewById(com.example.foottricks.R.id.assist_col)
        var rcCol: TextView = itemView.findViewById(com.example.foottricks.R.id.rc_col)
        var ycCol: TextView = itemView.findViewById(com.example.foottricks.R.id.yc_col)
        var goalsCol: TextView = itemView.findViewById(com.example.foottricks.R.id.goals_col)
        var timeCol: TextView = itemView.findViewById(com.example.foottricks.R.id.time_col)
        var stats_user_image: CircleImageView =
            itemView.findViewById(com.example.foottricks.R.id.stats_user_image)
        var stats_name: TextView =
            itemView.findViewById(com.example.foottricks.R.id.stats_user_name)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(context).inflate(R.layout.table_rows_stats, parent, false);
        return Viewholder(view);
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var users: Users = userList.get(position)

            holder.stats_name.text = users.firstname + " " + users.lastname
        Picasso.get().load(users.imageUri).into(holder.stats_user_image)
            holder.assistCol.text = users.assist
            holder.goalsCol.text = users.goals
            holder.ycCol.text = users.yc
            holder.rcCol.text = users.rc
            holder.timeCol.text = users.time
            holder.avgCol.text = users.avg
            holder.noteCol.text = users.note

//        val userRef = FirebaseDatabase.getInstance().reference.child("matches").child()
//        val userData = mapOf(
//            "rc" to users.rc,
//            "yc" to  users.yc,
//            "time" to  users.time,
//            "goals" to  users.goals,
//            "assist" to  users.assist,
//            "avg" to  users.avg,
//            "note" to  users.note
//        )
//        userRef.setValue(userData)
//            .addOnSuccessListener {
//                Log.d(TAG, "User data updated successfully")
//            }
//            .addOnFailureListener {
//                Log.e(TAG, "Error updating user data: ${it.message}", it)
//            }
    }

    override fun getItemCount(): Int {
        return userList.size;
    }


}