package com.example.gameofearning.Fragment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.gameofearning.Model.User
import com.example.gameofearning.Withdrwal
import com.example.gameofearning.databinding.FragmentSpinBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlin.random.Random


class SpinFragment : Fragment() {
    private lateinit var binding: FragmentSpinBinding
    private lateinit var timer: CountDownTimer
    private val itemTitle = arrayOf("100", "Try Again", "500", "Trey Again", "200", "try Again")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSpinBinding.inflate(inflater, container, false)
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

    private fun showResult(itemTitle: String) {
        Toast.makeText(requireContext(), itemTitle, Toast.LENGTH_SHORT).show()
        binding.spin.isEnabled = true // Enable the button again
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        binding.spin.setOnClickListener {
            binding.spin.isEnabled = false // Disable the button while the wheel is spinning

            val spin = Random.nextInt(6) // Generate a random va;ue between 0 and 5
            val degrees = 60f * spin // calculate the rotation degrees based on the random value

            timer = object : CountDownTimer(5000, 50) {
                var rotation = 0f

                override fun onTick(millisUntilFinished: Long) {
                    rotation += 5f // Rotate the wheel
                    if (rotation >= degrees) {
                        rotation = degrees
                        timer.cancel()
                        showResult(itemTitle[spin])
                    }
                    binding.wheel.rotation = rotation
                }

                override fun onFinish() {}
            }.start()
        }
    }
}


