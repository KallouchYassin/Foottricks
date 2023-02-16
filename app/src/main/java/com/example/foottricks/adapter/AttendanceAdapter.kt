package com.example.foottricks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.model.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class AttendanceAdapter(
    private val userList: ArrayList<Users>,
    private val context: Context
) : RecyclerView.Adapter<AttendanceAdapter.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userImg: CircleImageView =
            itemView.findViewById(com.example.foottricks.R.id.attendance_user_image)
        var userName: TextView =
            itemView.findViewById(com.example.foottricks.R.id.attendance_user_name)
        var present: TextView =
            itemView.findViewById(com.example.foottricks.R.id.attendance_present)
        var concov: TextView =
            itemView.findViewById(com.example.foottricks.R.id.attendance_convocated)

        var absent: TextView = itemView.findViewById(com.example.foottricks.R.id.attendance_absent)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(context).inflate(R.layout.table_presence, parent, false);
        return Viewholder(view); }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var users: Users = userList[position]

        holder.userName.text = users.firstname + " " + users.lastname
        Picasso.get().load(users.imageUri).into(holder.userImg)
        holder.present.text = users.present.toString()
        holder.absent.text = users.absent.toString()
        holder.concov.text = users.convocated.toString()

    }

    override fun getItemCount(): Int {
        return userList.size;
    }
}