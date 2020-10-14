package com.igorf.currency.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.igorf.currency.R
import kotlinx.android.synthetic.main.chooser_list_item.view.listCodeTextView
import kotlinx.android.synthetic.main.chooser_list_item.view.listNameTextView

class CurrencyChooserAdapter(
    private val list: ArrayList<Pair<String, String>>,
    private val itemClick: (Pair<String, String>) -> Unit
) :
    RecyclerView.Adapter<CurrencyChooserAdapter.CurrencyChooserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyChooserViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.chooser_list_item, parent, false)
        return CurrencyChooserViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrencyChooserViewHolder, position: Int) {
        holder.bindView(list[position])
    }

    fun updateList(newList: Map<String, String>) {
        list.clear()
        list.addAll(
            newList.entries.map { Pair(it.key, it.value) }
        )
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    inner class CurrencyChooserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val code = itemView.listCodeTextView
        private val name = itemView.listNameTextView

        fun bindView(item: Pair<String, String>) {
            code.text = item.first
            name.text = item.second

            itemView.setOnClickListener {
                itemClick.invoke(item)
            }
        }
    }
}