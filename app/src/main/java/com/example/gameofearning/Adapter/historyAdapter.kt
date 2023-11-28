package com.example.gameofearning.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gameofearning.Model.historyModelClass
import com.example.gameofearning.databinding.HistoryitemBinding

class historyAdapter(var ListHistory: ArrayList<historyModelClass>) :
    RecyclerView.Adapter<historyAdapter.HistotyCoinViewHolder>() {


    class HistotyCoinViewHolder(var binding: HistoryitemBinding) :
        RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistotyCoinViewHolder {
        return HistotyCoinViewHolder(HistoryitemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount() = ListHistory.size

    override fun onBindViewHolder(holder: HistotyCoinViewHolder, position: Int) {
        holder.binding.Time.text = ListHistory[position].timeAndDate
        holder.binding.coin.text = ListHistory[position].coin
    }
}
