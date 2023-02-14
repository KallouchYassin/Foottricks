package com.example.foottricks.adapter

import android.app.Dialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.model.Matches
import com.example.foottricks.model.Users
import com.example.foottricks.ui.calendar.CalendarAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlin.math.abs

class PresenceAdapter(
    private val summonList: ArrayList<Users>,
    private val presentList: ArrayList<Users>,
    private val absentList: ArrayList<Users>,
    private val type: String,
    private val uuid_event: String,
    private val context: Context
) : RecyclerView.Adapter<PresenceAdapter.Viewholder>()  {

    companion object {
        private lateinit var auth: FirebaseAuth
        private lateinit var database: FirebaseDatabase


    }
    class Viewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userImg: CircleImageView = itemView.findViewById(com.example.foottricks.R.id.presence_image)
        var presenceName: TextView = itemView.findViewById(com.example.foottricks.R.id.presence_name)
        var presence: TextView = itemView.findViewById(com.example.foottricks.R.id.user_presence)
        var more_btn: ImageView = itemView.findViewById(com.example.foottricks.R.id.more_btn)


    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        var view= LayoutInflater.from(context).inflate(R.layout.item_user_presence, parent, false);
        return Viewholder(view);    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        var dialog: Dialog = Dialog(context, R.style.Dialoge)
        dialog.setContentView(com.example.foottricks.R.layout.dialog_modify_presence);
        dialog.setCanceledOnTouchOutside(true);
        database = FirebaseDatabase.getInstance();
        var user=Users()
        if (position < summonList.size){
            var summon: Users = summonList.get(position)
            user = summonList.get(position);
            holder.presenceName.text=summon.firstname+" "+summon.lastname
            Picasso.get().load(summon.imageUri).into(holder.userImg)
            holder.presence.text="Summoned"
            var cImg=dialog.findViewById<CircleImageView>(R.id.image_dialog_presence)
            Picasso.get().load(summon.imageUri).into(cImg)

        }
        else if (position < summonList.size + presentList.size ) {
            var present: Users = presentList.get(position)
            user = presentList.get(position);
            holder.presenceName.text=present.firstname+" "+present.lastname
            Picasso.get().load(present.imageUri).into(holder.userImg)
            holder.presence.text="Present"
            var cImg=dialog.findViewById<CircleImageView>(R.id.image_dialog_presence)
            Picasso.get().load(present.imageUri).into(cImg)

        }
        else{
            var absent: Users = absentList.get(position)
            user = absentList.get(position);

            holder.presenceName.text=absent.firstname+" "+absent.lastname
            Picasso.get().load(absent.imageUri).into(holder.userImg)
            holder.presence.text="Absent"
            var cImg=dialog.findViewById<CircleImageView>(R.id.image_dialog_presence)
            Picasso.get().load(absent.imageUri).into(cImg)

        }
        holder.more_btn.setOnClickListener{

            dialog.show()

            var pBtn= dialog.findViewById<TextView>(R.id.btn_present)
            var aBtn= dialog.findViewById<TextView>(R.id.btn_absent)

            pBtn.setOnClickListener {


                        database.reference.child(type).child(uuid_event).child("present")
                            .child(user.uuid.toString())
                            .setValue(user).addOnCompleteListener {

                            }
                        if (type=="trainings") {
                            database.reference.child(type).child(uuid_event).child("pending")
                                .child(user.uuid.toString())
                                .removeValue()
                        }else{
                            database.reference.child(type).child(uuid_event).child("summon")
                                .child(user.uuid.toString())
                                .removeValue()
                        }
                        database.reference.child(type).child(uuid_event).child("absent")
                            .child(user.uuid.toString())
                            .removeValue()




                dialog.dismiss()
            }
            aBtn.setOnClickListener {
                database.reference.child(type).child(uuid_event).child("absent")
                    .child(user.uuid.toString())
                    .setValue(user).addOnCompleteListener {
                        Log.d("reussi", "oui")
                    }
                if(type=="trainings") {
                    database.reference.child(type).child(uuid_event).child("pending")
                        .child(user.uuid.toString())
                        .removeValue()
                }
                else{
                    database.reference.child(type).child(uuid_event).child("summon")
                        .child(user.uuid.toString())
                        .removeValue()
                }
                database.reference.child(type).child(uuid_event).child("present")
                    .child(user.uuid.toString())
                    .removeValue()

                dialog.dismiss()
            }

            }






    }

    override fun getItemCount(): Int {
        return summonList.size+presentList.size+absentList.size;
    }
}