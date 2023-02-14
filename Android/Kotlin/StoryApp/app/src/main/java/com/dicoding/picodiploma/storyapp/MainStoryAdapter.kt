package com.dicoding.picodiploma.storyapp

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MainStoryAdapter(private val listStories: ArrayList<StoryList>) : RecyclerView.Adapter<MainStoryAdapter.ViewHolder>() {
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imagePhoto:ImageView = view.findViewById(R.id.imgView)
        val txtName:TextView= view.findViewById(R.id.tvName)

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_story, viewGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtName.text = listStories[position].name
        Glide.with(holder.itemView.context)
            .load(listStories[position].photo)
            .into(holder.imagePhoto)
        holder.itemView.setOnClickListener {
            val optionsCompat: ActivityOptionsCompat =
                ActivityOptionsCompat.makeSceneTransitionAnimation(
                    holder.itemView.context as Activity,
                    Pair(holder.imagePhoto, "transition_imgDetail"),
                    Pair(holder.txtName, "transition_tvName"),
                )
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.DETAIL_ACTIVITY, listStories[position])
            holder.itemView.context.startActivity(intent, optionsCompat.toBundle())
        }
    }

    override fun getItemCount(): Int {
        return listStories.size
    }
}
