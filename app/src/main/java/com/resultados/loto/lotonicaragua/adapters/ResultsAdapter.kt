package com.resultados.loto.lotonicaragua.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.resultados.loto.lotonicaragua.data.LotoResult
import com.resultados.loto.lotonicaragua.R
import com.resultados.loto.lotonicaragua.setHidden
import com.resultados.loto.lotonicaragua.setVisible
import kotlinx.android.synthetic.main.loto_result_item.view.*

class ResultsAdapter(): ListAdapter<LotoResult, ResultsAdapter.ViewHolder>(DiffCallback()) {

    class DiffCallback: DiffUtil.ItemCallback<LotoResult>() {
        override fun areItemsTheSame(oldItem: LotoResult, newItem: LotoResult): Boolean {
            return oldItem.code == newItem.code
        }

        override fun areContentsTheSame(oldItem: LotoResult, newItem: LotoResult): Boolean {
            return oldItem == newItem
        }
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        @SuppressLint("SetTextI18n")
        fun bind(result: LotoResult){
            itemView.apply {
                item_date.text = result.date
                item_time.text = result.time
                item_result1.text = result.result1
                if(result.result2.isNotEmpty()){
                    item_result2.text = result.result2
                    item_result2.setVisible()
                }else
                    item_result2.setHidden()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context!!).inflate(R.layout.loto_result_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}