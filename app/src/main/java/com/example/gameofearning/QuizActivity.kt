package com.example.gameofearning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.gameofearning.Model.Question
import com.example.gameofearning.Model.User
import com.example.gameofearning.databinding.ActivityQuizBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.getValue
import com.google.firebase.firestore.firestore

class QuizActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityQuizBinding.inflate(layoutInflater)
    }
    var currentQestion = 0
    var score = 0
    var currentChance = 0L

    private lateinit var qestionList: ArrayList<Question>

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        Firebase.database.reference.child("Users").child(Firebase.auth.currentUser!!.uid)
            .addValueEventListener(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.exists()) {
                            currentChance = snapshot.value as Long
                        }

                        var user = snapshot.getValue<User>(User::class.java)
                        binding.name.text = user?.name

                    }

                    override fun onCancelled(error: DatabaseError) {
                        //TODO("Not yet implemented")
                    }

                }
            )
        setContentView(binding.root)

        qestionList = ArrayList<Question>()
        var image = intent.getIntExtra("catogaryImg", 0)
        var catText = intent.getStringExtra("questionType")
        Firebase.firestore.collection("Questions")
            .document(catText.toString())
            .collection("question1").get().addOnSuccessListener { questionData ->
                qestionList.clear()
                for (data in questionData.documents) {
                    var question: Question? = data.toObject(Question::class.java)
                    qestionList.add(question!!)
                }
                if (qestionList.size > 0) {
                    binding.question.text = qestionList.get(currentQestion).question
                    binding.option1.text = qestionList.get(currentQestion).option1
                    binding.option2.text = qestionList.get(currentQestion).option2
                    binding.option3.text = qestionList.get(currentQestion).option3
                    binding.option4.text = qestionList.get(currentQestion).option4
                }

            }

        binding.catogaryImg.setImageResource(image)

        binding.Coin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = Withdrwal()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.coin.setOnClickListener {
            val bottomSheetDialog: BottomSheetDialogFragment = Withdrwal()
            bottomSheetDialog.show(this@QuizActivity.supportFragmentManager, "TEST")
            bottomSheetDialog.enterTransition
        }
        binding.option1.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option1.toString())
        }
        binding.option2.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option2.toString())
        }
        binding.option3.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option3.toString())
        }
        binding.option4.setOnClickListener {
            nextQuestionAndScoreUpdate(binding.option4.toString())
        }
    }

    private fun nextQuestionAndScoreUpdate(s: String) {
        if (s.equals(qestionList.get(currentQestion).ans)) {
            score += 10
            Toast.makeText(this, score.toString(), Toast.LENGTH_SHORT).show()
        }

        currentQestion++
        if (currentQestion >= qestionList.size) {

            if (score >= (score / qestionList.size * 10) * 100) {
                binding.winner.visibility = View.VISIBLE
                var currentChance = 0


                Firebase.database.reference.child("PlayChance")
                    .child(Firebase.auth.currentUser!!.uid)
                    .setValue(currentChance + 1)

                var isUpdated = false
                if (isUpdated) {

                } else {
                    Firebase.database.reference.child("PlayChance")
                        .child(Firebase.auth.currentUser!!.uid)
                        .addValueEventListener(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                if (snapshot.exists()) {
                                    currentChance = (snapshot.value as Long).toInt()
                                }

                                // Toast.makeText(this@QuizActivity, currentChance.toString(), Toast.LENGTH_SHORT ).show()


                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })
                }


            } else {
                binding.sorry.visibility = View.VISIBLE
            }

        } else {
            binding.question.text = qestionList.get(currentQestion).question
            binding.option1.text = qestionList.get(currentQestion).option1
            binding.option2.text = qestionList.get(currentQestion).option2
            binding.option3.text = qestionList.get(currentQestion).option3
            binding.option4.text = qestionList.get(currentQestion).option4
        }
    }

}
