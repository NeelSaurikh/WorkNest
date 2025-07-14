package com.example.worknest.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.worknest.databinding.ItemLabelColorBinding

class LabelColorListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<String>,
    private val mSelectedColor: String
) : RecyclerView.Adapter<LabelColorListItemsAdapter.MyViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLabelColorBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val colorCode = list[position]
        holder.bind(colorCode, colorCode == mSelectedColor)
    }

    override fun getItemCount(): Int = list.size

    inner class MyViewHolder(private val binding: ItemLabelColorBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(colorCode: String, isSelected: Boolean) {
            binding.viewMain.setBackgroundColor(Color.parseColor(colorCode))
            binding.ivSelectedColor.visibility = if (isSelected) View.VISIBLE else View.GONE

            binding.root.setOnClickListener {
                onItemClickListener?.onClick(adapterPosition, colorCode)
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(position: Int, color: String)
    }
}
