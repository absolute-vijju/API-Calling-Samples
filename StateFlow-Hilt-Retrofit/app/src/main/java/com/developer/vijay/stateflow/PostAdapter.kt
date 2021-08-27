package com.developer.vijay.stateflow

import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.developer.vijay.stateflow.databinding.ItemPostBinding

class PostAdapter : RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    private var postList = listOf<PostResponse.PostResponseItem>()

    inner class PostViewHolder(val mBinding: ItemPostBinding) :
        RecyclerView.ViewHolder(mBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder(
            ItemPostBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val animation =
            AnimationUtils.loadAnimation(holder.mBinding.root.context, android.R.anim.slide_in_left)
        animation.duration = 500
        holder.apply {
            mBinding.post = postList[adapterPosition]
            mBinding.root.animation = animation
        }
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun setData(postList: List<PostResponse.PostResponseItem>) {
        this.postList = postList
        notifyDataSetChanged()
    }

}