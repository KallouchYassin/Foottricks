package com.example.foottricks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.R
import com.example.foottricks.activity.DisscusionActivity.Companion.rImage
import com.example.foottricks.activity.DisscusionActivity.Companion.sImage
import com.example.foottricks.model.Messages
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class MessageAdapter(

    private val messagesList: ArrayList<Messages>,
    private val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val ITEM_SEND: Int = 1
        private const val ITEM_RECEIVE: Int = 2
    }


    class SenderViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var circleImageView: CircleImageView = itemView.findViewById(R.id.profile_image)
        var txtmsg: TextView = itemView.findViewById(R.id.txtMessage)

    }

    class ReceiverViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var circleImageView: CircleImageView = itemView.findViewById(R.id.profile_image)
        var txtmsg: TextView = itemView.findViewById(R.id.txtMessage)


    }

    override fun getItemCount(): Int {

        return messagesList.size;
    }

    override fun getItemViewType(position: Int): Int {
        var message= messagesList[position]
        return if(FirebaseAuth.getInstance().currentUser!!.uid == message.senderId) {
            ITEM_SEND
        } else {
            ITEM_RECEIVE
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_SEND) {

            var view= LayoutInflater.from(context).inflate(R.layout.sender_layout_item, parent, false);
            return MessageAdapter.SenderViewholder(view);

        } else {
            var view=LayoutInflater.from(context).inflate(R.layout.receiver_layout_item, parent, false);
            return MessageAdapter.ReceiverViewholder(view);

        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        var messages=messagesList.get(position);
        if (holder is SenderViewholder) {
            val senderViewHolder = holder as SenderViewholder
            senderViewHolder.txtmsg.setText(messages.message)
            Picasso.get().load(sImage).into(senderViewHolder.circleImageView)

        }
        else {
            val receiverViewholder = holder as ReceiverViewholder
            receiverViewholder.txtmsg.setText(messages.message)
            Picasso.get().load(rImage).into(receiverViewholder.circleImageView)



        }

    }


}
