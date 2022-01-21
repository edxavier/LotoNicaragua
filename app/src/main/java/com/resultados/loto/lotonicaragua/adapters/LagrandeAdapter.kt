package com.resultados.loto.lotonicaragua.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.databinding.LagrandeItemBinding

class LagrandeAdapter(): ListAdapter<LotoResult, LagrandeAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<LotoResult>() {
        override fun areItemsTheSame(oldItem: LotoResult, newItem: LotoResult): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: LotoResult, newItem: LotoResult): Boolean {
            return oldItem == newItem
        }
    }

    inner class ViewHolder(private val binding: LagrandeItemBinding): RecyclerView.ViewHolder(binding.root){

        @SuppressLint("SetTextI18n")
        fun bind(result: LotoResult){

            binding.apply {
                itemDate.text = result.date
                itemResult1.text = result.result1
                itemResult2.text = result.result2
                itemResult3.text = result.result3
                itemResult4.text = result.result4
                itemResult5.text = result.result5
                itemResult6.text = result.result6
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LagrandeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        //return ViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.lagrande_item, parent, false))
        return  ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}