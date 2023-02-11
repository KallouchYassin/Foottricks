package com.example.foottricks.ui.chat

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.activity.DisscusionActivity
import com.example.foottricks.R
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

public class ChatAdapter(
    private val usersList: ArrayList<Users>,
    private val chatFragment: Context
) : RecyclerView.Adapter<ChatAdapter.Viewholder>() {


    override fun getItemCount(): Int {


        return usersList.size;
    }

    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var user_img: CircleImageView = itemView.findViewById(R.id.user_image)
        var user_name: TextView = itemView.findViewById(R.id.user_name)

        var user_email: TextView = itemView.findViewById(R.id.user_mail)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {



        val view = LayoutInflater.from(chatFragment).inflate(R.layout.item_user_row, parent, false)
        return Viewholder(view);
    }


    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var users: Users = usersList.get(position)



            holder.user_email.text = users.email;
            holder.user_name.text = users.firstname +" "+users.lastname;
            Picasso.get().load(users.imageUri).into(holder.user_img)

            holder.itemView.setOnClickListener {


                val intent = Intent(chatFragment, DisscusionActivity::class.java)
                intent.putExtra("name", users.firstname +" "+users.lastname)
                intent.putExtra("receiverImg", users.imageUri)

                intent.putExtra("uid", users.uuid)

                chatFragment.startActivity(intent);

        }





    }

}