package com.example.gameofearning.Fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.gameofearning.Model.User
import com.example.gameofearning.R
import com.example.gameofearning.databinding.FragmentProfileBinding
import com.google.firebase.Firebase
import com.google.firebase.app
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class ProfileFragment : Fragment() {
    val binding by lazy {
        FragmentProfileBinding.inflate(layoutInflater)
    }
    var isExpand = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.imageButton.setOnClickListener {
            if (isExpand) {
                binding.expandabelconstraintLayout.visibility = View.VISIBLE
                binding.imageButton.setImageResource(R.drawable.arrowup)
            } else {
                binding.expandabelconstraintLayout.visibility = View.GONE
                binding.imageButton.setImageResource(R.drawable.downarrow)
            }
            isExpand = !isExpand
        }
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>(User::class.java)
                        binding.Name.text = user?.name
                        binding.NameUp.text = user?.name
                        binding.Email.text = user?.email
                        binding.Password.text = user?.password
                        binding.Age.text = user?.age.toString()

                    }

                    override fun onCancelled(error: DatabaseError) {
                        //TODO("Not yet implemented")
                    }

                }
            )

        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {

    }
}