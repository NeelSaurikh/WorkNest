package com.example.worknest.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.worknest.R
import com.example.worknest.activities.TaskListActivity
import com.example.worknest.databinding.ItemCardBinding
import com.example.worknest.models.Card
import com.example.worknest.models.SelectedMembers

open class CardListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<Card>
) : RecyclerView.Adapter<CardListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val model = list[position]
        val binding = holder.binding

        if (model.labelColor.isNotEmpty()) {
            binding.viewLabelColor.visibility = View.VISIBLE
            binding.viewLabelColor.setBackgroundColor(Color.parseColor(model.labelColor))
        } else {
            binding.viewLabelColor.visibility = View.GONE
        }

        binding.tvCardName.text = model.name

        if ((context as TaskListActivity).mAssignedMemberDetailList.isNotEmpty()) {
            val selectedMembersList: ArrayList<SelectedMembers> = ArrayList()
            for (i in context.mAssignedMemberDetailList.indices) {
                for (j in model.assignedTo) {
                    if (context.mAssignedMemberDetailList[i].id == j) {
                        val selectedMembers = SelectedMembers(
                            context.mAssignedMemberDetailList[i].id,
                            context.mAssignedMemberDetailList[i].image
                        )
                        selectedMembersList.add(selectedMembers)
                    }
                }
            }
            if (selectedMembersList.isNotEmpty()) {
                if (selectedMembersList.size == 1 && selectedMembersList[0].id == model.createdBy) {
                    binding.rvCardSelectedMembersList.visibility = View.GONE
                } else {
                    binding.rvCardSelectedMembersList.visibility = View.VISIBLE
                    binding.rvCardSelectedMembersList.layoutManager = GridLayoutManager(context, 4)
                    val adapter = CardMemberListItemsAdapter(context, selectedMembersList, false)
                    binding.rvCardSelectedMembersList.adapter = adapter
                    adapter.setOnClickListener(
                        object : CardMemberListItemsAdapter.OnClickListener {
                            override fun onClick() {
                                onClickListener?.onClick(position)
                            }
                        }
                    )
                }
            } else {
                binding.rvCardSelectedMembersList.visibility = View.GONE
            }
        } else {
            binding.rvCardSelectedMembersList.visibility = View.GONE
        }

        binding.root.setOnClickListener {
            onClickListener?.onClick(position)
        }
    }

    override fun getItemCount(): Int = list.size

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick(position: Int)
    }

    class MyViewHolder(val binding: ItemCardBinding) : RecyclerView.ViewHolder(binding.root)
}