package com.vinu.vinodassigment.ui.adapters

import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.vinu.vinodassigment.R
import com.vinu.vinodassigment.models.NewsModel
import kotlinx.android.synthetic.main.single_news_item_layout.view.*

class NewsRecyclerViewAdapter(
    private var newsList: ArrayList<NewsModel>
) :
    RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.single_news_item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = newsList.size

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = newsList[position]
        holder.itemView.titleTextView.text = item.title
        holder.itemView.descriptionTextView.text = item.description
        loadImage(holder, item)
    }

    private fun loadImage(holder: NewsViewHolder, item: NewsModel) {

        if (newsList.size > 0 && item.imageHref != null) {

            Glide.with(holder.itemView.context)
                .load(item.imageHref)
                .placeholder(R.drawable.ic_placeholder)
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.newsImageView.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.itemView.newsImageView.visibility = View.VISIBLE
                        return false
                    }

                })
                .into(holder.itemView.newsImageView)
        } else {
            Glide.with(holder.itemView.context).clear(holder.itemView.newsImageView)
            holder.itemView.newsImageView.visibility = View.GONE
        }

    }

    class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}