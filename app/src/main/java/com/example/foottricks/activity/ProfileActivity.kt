package com.example.foottricks.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.foottricks.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var imageUrl: Uri
    private lateinit var storage: FirebaseStorage
    val auth = FirebaseAuth.getInstance();
    private lateinit var database: DatabaseReference

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        storage = FirebaseStorage.getInstance();
        database =
            FirebaseDatabase.getInstance().getReference("users").child(auth.currentUser!!.uid);

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                //retrieving the user name
                var imageUri =
                    dataSnapshot.child("imageUri").getValue(String::class.java)

                Picasso.get().load(imageUri).into(binding.profileImage)

            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("Error", "Failed to read value.", error.toException())
            }
        })

        binding.profileImage.setOnClickListener {

            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 10)


        }

        binding.btnLogout.setOnClickListener {
            var dialog: Dialog = Dialog(this, com.example.foottricks.R.style.Dialoge)
            dialog.setContentView(com.example.foottricks.R.layout.dialog_layout);

            var yesBtn = dialog.findViewById<TextView>(com.example.foottricks.R.id.yesBtn)
            var noBtn = dialog.findViewById<TextView>(com.example.foottricks.R.id.noBtn)

            yesBtn.setOnClickListener {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))


            }
            noBtn.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show();

        }
        binding.imgToolbarBtnBack.setOnClickListener {
            it.setBackgroundColor(Color.GRAY)
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            val selectedImage = data?.data
            Picasso.get().load(selectedImage).into(binding.profileImage)
            var storageRef = storage.getReference().child("upload").child(auth.uid!!);
            storageRef.putFile(selectedImage!!).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        Log.d("test", uri.toString());

                        var map = HashMap<String, Any>()
                        map["imageUri"] = uri.toString();
                        database.updateChildren(map).addOnSuccessListener {
                            Log.d("test", "oui oui cets mehdi")
                        }
                    }
                }
            }

        }
    }
}