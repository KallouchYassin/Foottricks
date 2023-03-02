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

class LeagueAdapter(
    private val teamList: ArrayList<Team>,
    private val context: Context
): RecyclerView.Adapter<LeagueAdapter.Viewholder>()  {
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var teamImg: CircleImageView = itemView.findViewById(com.example.foottricks.R.id.standings_team_logo)
        var teamName: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_name)
        var team_pts: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_pts)
        var team_mp: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_mp)
        var team_g: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_g)
        var team_n: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_n)
        var team_p: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_p)
        var team_gf: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_gf)
        var team_ga: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_ga)
        var team_gd: TextView = itemView.findViewById(com.example.foottricks.R.id.standings_team_gd)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view = LayoutInflater.from(context).inflate(R.layout.table_team_standings, parent, false);
        return Viewholder(view);
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var teams: Team = teamList[position]

        holder.teamName.text = teams.team_name
        Picasso.get().load(teams.team_imageUri).into(holder.teamImg)
        holder.team_n.text = teams.team_draws.toString()
        holder.team_mp.text = teams.team_match_played.toString()
        holder.team_pts.text = teams.team_points.toString()
        holder.team_g.text = teams.team_wins.toString()
        holder.team_p.text = teams.team_losses.toString()
        holder.team_ga.text = teams.team_goals_against.toString()
        holder.team_gd.text = teams.team_goals_differential.toString()
        holder.team_gf.text = teams.team_goals_for.toString()

    }

    override fun getItemCount(): Int {
        return teamList.size;
    }

}
