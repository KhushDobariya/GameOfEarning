package com.example.gameofearning.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gameofearning.Adapter.catogaryAdapter
import com.example.gameofearning.Model.User
import com.example.gameofearning.Model.catogaryModelclass
import com.example.gameofearning.R
import com.example.gameofearning.Withdrwal
import com.example.gameofearning.databinding.FragmentHomeBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class HomeFragment : Fragment() {
    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private var categoryList = ArrayList<catogaryModelclass>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding.Coin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = Withdrwal()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.coin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = Withdrwal()
            bottomSheetDialog.show(requireActivity().supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }

        // Inflate the layout for this fragment
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        var user = snapshot.getValue<User>(User::class.java)
                        binding.name.text = user?.name
                    }

                    override fun onCancelled(error: DatabaseError) {
                        //TODO("Not yet implemented")
                    }

                }
            )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryList.clear()
        categoryList.add(catogaryModelclass(R.drawable.scince1, "Scince"))
        categoryList.add(catogaryModelclass(R.drawable.english1, "english"))
        categoryList.add(catogaryModelclass(R.drawable.geography, "Geography"))
        categoryList.add(catogaryModelclass(R.drawable.math, "Mathematics"))
        binding.catogaryRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
        var adapter = catogaryAdapter(categoryList, requireActivity())
        binding.catogaryRecyclerView.adapter = adapter
        binding.catogaryRecyclerView.setHasFixedSize(true)
    }

    companion object {

    }
}