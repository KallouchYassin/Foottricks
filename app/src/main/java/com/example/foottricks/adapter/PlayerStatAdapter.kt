package com.example.foottricks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.model.Team
import com.example.foottricks.model.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class PlayerStatAdapter(
    private val userList: ArrayList<Users>,
    private val context: Context
): RecyclerView.Adapter<PlayerStatAdapter.Viewholder>()  {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var assistCol: TextView = itemView.findViewById(com.example.foottricks.R.id.player_assist)
        var rcCol: TextView = itemView.findViewById(com.example.foottricks.R.id.player_rc)
        var ycCol: TextView = itemView.findViewById(com.example.foottricks.R.id.player_yc)
        var goalsCol: TextView = itemView.findViewById(com.example.foottricks.R.id.player_goals)
        var timeCol: TextView = itemView.findViewById(com.example.foottricks.R.id.player_time)
        var stats_user_image: CircleImageView =
            itemView.findViewById(com.example.foottricks.R.id.player_img)
        var stats_name: TextView =
            itemView.findViewById(com.example.foottricks.R.id.player_name)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(context).inflate(R.layout.table_player_stats, parent, false);
        return Viewholder(view);    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var player: Users = userList[position]

        holder.assistCol.text = player.assist
        holder.goalsCol.text = player.goals
        holder.ycCol.text = player.yc
        holder.rcCol.text = player.rc
        holder.timeCol.text = player.time
        holder.stats_name.text = player.lastname+" "+player.firstname

        Picasso.get().load(player.imageUri).into(holder.stats_user_image)

    }

    override fun getItemCount(): Int {
        return userList.size;
    }
}