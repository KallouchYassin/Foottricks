package com.example.foottricks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import de.hdodenhof.circleimageview.CircleImageView

class MostAssistAdapter(
    private val userList: ArrayList<Users>,
    private val context: Context
) : RecyclerView.Adapter<MostAssistAdapter.Viewholder>() {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mg_name: TextView =
            itemView.findViewById(com.example.foottricks.R.id.item_most_goals_name)
        var scored: TextView =
            itemView.findViewById(com.example.foottricks.R.id.item_most_goals_scored)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_most_goals, parent, false);
        return Viewholder(view); }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var player: Users = userList[position]
        holder.mg_name.text = "${position + 1} - ${player.firstname} ${player.lastname}"
        holder.scored.text = "${player.assist}"

    }

    override fun getItemCount(): Int {
        return userList.size;
    }
}