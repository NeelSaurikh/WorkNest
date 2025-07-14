package com.example.worknest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.worknest.R
import com.example.worknest.databinding.ItemMemberBinding
import com.example.worknest.models.User
import com.example.worknest.utils.Constants

open class MemberListItemsAdapter(
    private val context: Context,
    private var list: ArrayList<User>
) : RecyclerView.Adapter<MemberListItemsAdapter.MyViewHolder>() {

    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemMemberBinding.inflate(LayoutInflater.from(context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val model = list[position]

        with(holder.binding) {
            Glide.with(context)
                .load(model.image)
                .centerCrop()
                .placeholder(R.drawable.ic_user_place_holder)
                .into(ivMemberImage)

            tvMemberName.text = model.name
            tvMemberEmail.text = model.email

            if(model.selected){
                ivSelectedMember.visibility = View.VISIBLE
            }else{
                ivSelectedMember.visibility = View.GONE
            }
//            ivSelectedMember.visibility = if (model.selected) ViewGroup.VISIBLE else ViewGroup.GONE

//            root.setOnClickListener {
//                onClickListener?.let {
//                    val action = if (model.selected) Constants.UN_SELECT else Constants.SELECT
//                    it.onClick(position, model, action)
//                }
//            }
            root.setOnClickListener {
                if(onClickListener != null){
                    if(model.selected){
                        onClickListener!!.onClick(position,model, Constants.UN_SELECT)
                    }else{
                        onClickListener!!.onClick(position,model, Constants.SELECT)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListener: OnClickListener) {
        this.onClickListener = onClickListener
    }

    class MyViewHolder(val binding: ItemMemberBinding) : RecyclerView.ViewHolder(binding.root)

    interface OnClickListener {
        fun onClick(position: Int, user: User, action: String)
    }
}
