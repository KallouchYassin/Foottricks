package com.example.foottricks.ui.chat

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foottricks.activity.LoginActivity
import com.example.foottricks.databinding.FragmentChatBinding
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private val binding get() = _binding!!

    private lateinit var adapter: ChatAdapter
    private lateinit var mainUserRecyclerView: RecyclerView

    private lateinit var usersArrayList: ArrayList<Users>
    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        Log.d("test", currentUser.toString())
        if (currentUser == null) {
            var intent = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intent)

        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val fragmentChatBinding =
            ViewModelProvider(this).get(ChatViewModel::class.java)
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        val root: View = binding.root
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        val currentUserUid = auth.currentUser!!.uid


        var databaseRef: DatabaseReference = database.getReference("users")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                usersArrayList= ArrayList<Users>();
                    for (dataSnapshot in snapshot.children) {

                        val users = dataSnapshot.getValue(Users::class.java)
                        if (users!!.uuid!=currentUserUid)
                        {
                            usersArrayList.add(users!!)
                        }

                    }




                mainUserRecyclerView = binding.userRecyclerview
                mainUserRecyclerView.layoutManager = LinearLayoutManager(requireContext())
                adapter= ChatAdapter(usersArrayList, context!!);
                mainUserRecyclerView.adapter=adapter;
                adapter.notifyDataSetChanged();
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })


        if (auth.currentUser == null) {

            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)

        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}