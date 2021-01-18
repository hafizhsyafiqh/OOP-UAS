package com.example.crudapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crudapp.Database.Sepatu
import kotlinx.android.synthetic.main.adapter_sepatu.view.*

class SepatuAdapter (private val AllSepatu: ArrayList<Sepatu>, private val listener: OnAdapterListener) : RecyclerView.Adapter<SepatuAdapter.SepatuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SepatuViewHolder {
        return SepatuViewHolder(
            LayoutInflater.from(parent.context).inflate( R.layout.adapter_sepatu, parent, false)
        )
    }

    override fun getItemCount() = AllSepatu.size

    override fun onBindViewHolder(holder: SepatuViewHolder, position: Int) {
        val sepatu = AllSepatu[position]
        holder.view.text_merk.text = sepatu.merk
        holder.view.text_merk.setOnClickListener {
            listener.onClick(sepatu)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(sepatu)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(sepatu)
        }
    }

    class SepatuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Sepatu>) {
        AllSepatu.clear()
        AllSepatu.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener {
        fun onClick(sepatu: Sepatu)
        fun onDelete(sepatu: Sepatu)
        fun onUpdate(sepatu: Sepatu)
    }
}