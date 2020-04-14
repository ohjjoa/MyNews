package com.archive.mynews.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.archive.mynews.R
import com.archive.mynews.model.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_top_heading.view.*


class TopHeadingAdapter(val context: Context, private val articleList: List<Article>) : RecyclerView.Adapter<TopHeadingAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_top_heading, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.bindView(articleList[position], context)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layout_top_heading = view.image_source_framework
        val date = view.text_top_heading_date
        val imageTopHeading = view.image_top_heading
        val content = view.text_top_heading_content
        val sourcemedia = view.text_top_heading_media

        fun bindView(article: Article, context: Context) {
            date.text = article.publishedAt.toString()
            content.text = article.description
            sourcemedia.text = article.url
            Glide.with(context).load(article.urlToImage).into(imageTopHeading)

            layout_top_heading.setOnClickListener(View.OnClickListener {
                Toast.makeText(context, "사이트 연결", Toast.LENGTH_SHORT).show()

                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                context.startActivity(intent)
            })
        }
    }
}
