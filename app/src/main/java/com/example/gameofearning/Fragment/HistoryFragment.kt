package com.example.gameofearning.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.gameofearning.Adapter.historyAdapter
import com.example.gameofearning.Model.User
import com.example.gameofearning.Model.historyModelClass
import com.example.gameofearning.R
import com.example.gameofearning.Withdrwal
import com.example.gameofearning.databinding.FragmentHistoryBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class HistoryFragment : Fragment() {
    val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }

    private var ListHistory = ArrayList<historyModelClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ListHistory.add(historyModelClass("12:03", "200"))
        ListHistory.add(historyModelClass("01:45", "500"))
        ListHistory.add(historyModelClass("03:08", "1000"))
        ListHistory.add(historyModelClass("12:12", "1200"))
        ListHistory.add(historyModelClass("10:35", "800"))
        ListHistory.add(historyModelClass("06:30", "100"))
    }

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
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        var adapter = historyAdapter(ListHistory)
        binding.historyRecyclerView.adapter = adapter
        binding.historyRecyclerView.setHasFixedSize(true)
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
}