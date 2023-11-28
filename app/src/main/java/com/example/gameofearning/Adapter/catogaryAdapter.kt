package com.example.gameofearning.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.gameofearning.Model.catogaryModelclass
import com.example.gameofearning.QuizActivity
import com.example.gameofearning.databinding.CatogaryitemsBinding

class catogaryAdapter(
    var categoryList: ArrayList<catogaryModelclass>,
    var requireActivity: FragmentActivity
) :
    RecyclerView.Adapter<catogaryAdapter.MycatogaryViewHolder>() {


    class MycatogaryViewHolder(var binding: CatogaryitemsBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MycatogaryViewHolder {
        return MycatogaryViewHolder(
            CatogaryitemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = categoryList.size

    override fun onBindViewHolder(holder: MycatogaryViewHolder, position: Int) {
        var dataList = categoryList[position]
        holder.binding.catogaryImage.setImageResource(dataList.catImage)
        holder.binding.catogary.text = dataList.catText
        holder.binding.catogaryButoon.setOnClickListener {
            var intent = Intent(requireActivity, QuizActivity::class.java)

            intent.putExtra("catogaryImg", dataList.catImage)
            intent.putExtra("questionType",dataList.catText)
            requireActivity.startActivity(intent)
        }
    }
}

