package com.resultados.loto.lotonicaragua.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.databinding.LotoResultItemBinding
import com.resultados.loto.lotonicaragua.setHidden
import com.resultados.loto.lotonicaragua.setVisible

class ResultsAdapter(): ListAdapter<LotoResult, ResultsAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<LotoResult>() {
        override fun areItemsTheSame(oldItem: LotoResult, newItem: LotoResult): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: LotoResult, newItem: LotoResult): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: LotoResultItemBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(result: LotoResult){
            binding.apply {
                itemDate.text = result.date
                itemTime.text = result.time
                itemResult1.text = result.result1
                if(result.result2.isNotEmpty()){
                    itemResult2.text = result.result2
                    itemResult2.setVisible()
                }else
                    itemResult2.setHidden()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LotoResultItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}