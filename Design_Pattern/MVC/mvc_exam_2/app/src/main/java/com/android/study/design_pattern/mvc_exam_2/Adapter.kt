package com.android.study.design_pattern.mvc_exam_2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.study.design_pattern.mvc_exam_2.databinding.ActivityItemBinding

class Adapter(private val itemClickListener: ItemClickListener)
    : ListAdapter<Model, Adapter.ViewHodler>(difUtil){

    inner class ViewHodler(private val binding: ActivityItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(item: Model) {
                    binding.itemTextView.text = item.name
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHodler {
        return ViewHodler(
            ActivityItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHodler, position: Int) {
        val models = currentList[position]

        holder.bind(models)

        holder.itemView.setOnClickListener {
            itemClickListener.onClick(models)
        }
    }

    companion object {
        val difUtil = object : DiffUtil.ItemCallback<Model>() {
            override fun areItemsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: Model, newItem: Model): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface  ItemClickListener {
        fun onClick(models: Model)
    }
}