package com.maha.NewsAppMvvm.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.maha.NewsAppMvvm.R
import com.maha.NewsAppMvvm.models.Article

class NewsAdapter :RecyclerView.Adapter<NewsAdapter.ArticlceViewHolder>() {

    class ArticlceViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
    {
        val ivImageView = itemView.findViewById<ImageView>(R.id.ivArticleImage)
        val txtSource = itemView.findViewById<TextView>(R.id.tvSource)
        val tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        val tvDiscription = itemView.findViewById<TextView>(R.id.tvDescription)
        val tvPublishedat = itemView.findViewById<TextView>(R.id.tvPublishedAt)
    }


    private val differCallBack = object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }
    }
        var differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlceViewHolder {
        return ArticlceViewHolder( LayoutInflater.from(parent.context).
        inflate(R.layout.item_article_preview,parent,false))
    }

    override fun getItemCount(): Int {
      return  differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticlceViewHolder, position: Int) {
        var article = differ.currentList[position]
        holder.apply {
           Glide.with(itemView.context).load(article.urlToImage).into(ivImageView)
           tvTitle.text=article.title
            txtSource.text=article.source.name
            tvDiscription.text=article.description
            tvPublishedat.text=article.publishedAt
            itemView.setOnClickListener{
                onItemClickListener?.let { it(article) }
            }
        }
    }
private var onItemClickListener:((Article)->Unit) ?= null

    fun setOnItemClickListener(listener:(Article)->Unit){
        onItemClickListener = listener
    }

}