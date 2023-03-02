package com.example.foottricks.adapter

import android.content.Context
import android.service.autofill.FieldClassification.Match
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import de.hdodenhof.circleimageview.CircleImageView

class TeamResultAdapter(
    private val matchesList: ArrayList<Matches>,
    private val context: Context
) : RecyclerView.Adapter<TeamResultAdapter.Viewholder>()  {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var team: TextView = itemView.findViewById(com.example.foottricks.R.id.team_name)
        var opponent: TextView = itemView.findViewById(com.example.foottricks.R.id.opp_teamname)
        var result: TextView = itemView.findViewById(com.example.foottricks.R.id.match_result)
        var date: TextView = itemView.findViewById(com.example.foottricks.R.id.match_date)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view= LayoutInflater.from(context).inflate(R.layout.item_match_result, parent, false);
        return Viewholder(view);     }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var match: Matches = matchesList.get(position)
        holder.team.text = match.teamId
        holder.opponent.text =match.opponent
        holder.result.text = match.result
        holder.date.text = match.begin_date.toString()



    }

    override fun getItemCount(): Int {
return matchesList.size ;
    }
}