package com.example.newsapp.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.api.model.NewsItem
import com.example.newsapp.databinding.ItemNewsBinding
import kotlinx.android.synthetic.main.item_news.view.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(var newsList: List<NewsItem?>?):RecyclerView.Adapter<NewsAdapter.ViewHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
      //  val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        val viewBinding = DataBindingUtil.inflate<ItemNewsBinding>(
            LayoutInflater.from(parent.context),R.layout.item_news,parent,false)


        return ViewHolder(viewBinding)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
                val news = newsList?.get(position)
              //  holder.title.setText(news?.title)
              //  holder.desc.setText(news?.description)
            holder.bind(news)

        val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val date: Date =
            dateFormat.parse(news?.publishedAt) //You will get date object relative to server/client timezone wherever it is parsed
        val formatter: DateFormat =
            SimpleDateFormat("yyyy-MM-dd") //If you need time just put specific format for time like 'HH:mm:ss'
        val dateStr: String = formatter.format(date)

                holder.viewBinding.date.setText(dateStr)
                //Glide.with(holder.itemView).load(news?.urlToImage).into(holder.image)
             //   holder.viewBinding.image.loadImage(news?.urlToImage)
    }

    override fun getItemCount(): Int {
        return newsList?.size ?:0
    }
    class ViewHolder(val viewBinding: ItemNewsBinding) : RecyclerView.ViewHolder(viewBinding.root){
//        val title = itemView.title
//        val date = itemView.date
//        val desc = itemView.desc
//        val image = itemView.image
        fun bind(news:NewsItem?){
        viewBinding.news=news
        viewBinding.executePendingBindings()
}
    }

    fun ImageView.loadImage(url: String?){
        Glide.with(this).load(url).into(this)
    }

    fun changeData(newsList: List<NewsItem?>?){
        this.newsList=newsList
        notifyDataSetChanged()
    }
}