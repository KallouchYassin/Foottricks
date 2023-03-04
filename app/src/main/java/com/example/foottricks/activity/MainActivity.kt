package com.example.foottricks.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.foottricks.R
import com.example.foottricks.databinding.ActivityMainBinding
import com.example.foottricks.model.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var user: FirebaseUser
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase


    public override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        val databaseref = FirebaseDatabase.getInstance().reference.child("users");
        var u: Users? =null;


        databaseref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {

                    val users = dataSnapshot.getValue(Users::class.java)
                    if (users!!.uuid != currentUser?.uid) {
                        u=users
                    }

                }
                if(currentUser == null){

                    if(u?.teamId == null){
                        Log.d("mechant", u.toString());

                        var intent = Intent(this@MainActivity, JoinTeamActivity::class.java)
                        startActivity(intent)
                        finish()
                    }else {
                    var intent = Intent(this@MainActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()}
                }



            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance();
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        user = auth.currentUser!!

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val hView = navView.getHeaderView(0)
        val textViewName = hView.findViewById(R.id.nav_name) as TextView
        val textViewEmail = hView.findViewById(R.id.nav_email) as TextView
        val profileImg = hView.findViewById(R.id.imageView) as ImageView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        profileImg.setOnClickListener {
            var intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
            finish()

        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_chat, R.id.nav_calendar,R.id.nav_team
            ), drawerLayout
        )

        if (user == null) {
            var intent = Intent(this@MainActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }




        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        val userRef = FirebaseDatabase.getInstance().reference.child("users").child(uid!!)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //retrieving the user name
                val email = dataSnapshot.child("email").getValue(String::class.java)
                val lastname = dataSnapshot.child("lastname").getValue(String::class.java)
                val firstname = dataSnapshot.child("firstname").getValue(String::class.java)

               val imgProfile= dataSnapshot.child("imageUri").getValue(String::class.java)
                Picasso.get().load(imgProfile).into(profileImg)

                textViewEmail.text = email
                textViewName.text = "$firstname $lastname";

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


}