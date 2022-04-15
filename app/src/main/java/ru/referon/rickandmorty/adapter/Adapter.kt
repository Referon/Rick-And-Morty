package ru.referon.rickandmorty.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.referon.rickandmorty.databinding.CardLayoutBinding
import ru.referon.rickandmorty.model.Result

interface OnInteractionListener {
    fun onPerson(info: Result) {}
}

class Adapter(
    private val onInteractionListener: OnInteractionListener
) : androidx.recyclerview.widget.ListAdapter<Result, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardLayoutBinding,
    private val onInteractionListener: OnInteractionListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(info: Result) {

        binding.apply {
            name.text = info.name
            species.text = info.species
            gender.text = info.gender
            Glide.with(itemView)
                .load(info.image)
                .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                .apply(RequestOptions().override(300, 300))
                .into(image)

            card.setOnClickListener {
                onInteractionListener.onPerson(info)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Result>() {
    override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
        return oldItem == newItem
    }

}