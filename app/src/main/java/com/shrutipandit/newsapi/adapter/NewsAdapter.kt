package com.shrutipandit.newsapi.adapter

    import android.content.Intent
    import android.net.Uri
    import android.view.LayoutInflater
    import android.view.ViewGroup
    import androidx.recyclerview.widget.RecyclerView
    import com.bumptech.glide.Glide
    import com.shrutipandit.newsapi.databinding.NewsItemBinding
    import com.shrutipandit.newsapi.db.NewsArticle

class NewsAdapter(private var newsArticles: List<NewsArticle>) :
        RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = NewsItemBinding.inflate(inflater, parent, false)
            return NewsViewHolder(binding)
        }

        override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            val article = newsArticles[position]
            holder.bind(article)
        }
        fun updateData(newData: List<NewsArticle>) {
            newsArticles = newData
            notifyDataSetChanged()
        }


        override fun getItemCount() = newsArticles.size

        inner class NewsViewHolder(private val binding: NewsItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(article: NewsArticle) {
                Glide.with(binding.root)
                    .load(article.urlToImage)
                    .into(binding.newsImage)

                binding.newsTitle.text = article.title
                binding.newsDescription.text = article.description

                binding.readMoreButton.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                    binding.root.context.startActivity(intent)
                }
            }
        }
    }
