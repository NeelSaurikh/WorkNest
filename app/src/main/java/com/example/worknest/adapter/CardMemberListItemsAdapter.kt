package com.example.worknest.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worknest.R
import com.example.worknest.databinding.ItemCardSelectedMemberBinding
import com.example.worknest.models.SelectedMembers

open class CardMemberListItemsAdapter(
    private val context: Context,
    private val list: ArrayList<SelectedMembers>,
    private val assignMembers: Boolean
) : RecyclerView.Adapter<CardMemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemCardSelectedMemberBinding.inflate(
            LayoutInflater.from(context), parent, false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]
        val binding = holder.binding

        if (position == list.size - 1 && assignMembers) {
            binding.ivAddMember.visibility = android.view.View.VISIBLE
            binding.ivSelectedMemberImage.visibility = android.view.View.GONE
        } else {
            binding.ivAddMember.visibility = android.view.View.GONE
            binding.ivSelectedMemberImage.visibility = android.view.View.VISIBLE

            Glide.with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(binding.ivSelectedMemberImage)
        }

        binding.root.setOnClickListener {
            onClickListener?.onClick()
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onClick()
    }

    class MyViewHolder(val binding: ItemCardSelectedMemberBinding) :
        RecyclerView.ViewHolder(binding.root)
}