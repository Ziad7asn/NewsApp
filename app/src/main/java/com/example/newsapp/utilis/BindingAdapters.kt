package com.example.newsapp.utilis

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("imageURL")
fun loadImage(image : ImageView,url:String){
    Glide.with(image).load(url).into(image)
}
//@BindingAdapter("dateFormat")
//fun changeDateFormat(news: String) {
//
//    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//    val date: Date =
//        dateFormat.parse(news) //You will get date object relative to server/client timezone wherever it is parsed
//    val formatter: DateFormat =
//        SimpleDateFormat("yyyy-MM-dd") //If you need time just put specific format for time like 'HH:mm:ss'
//    val dateStr: String = formatter.format(date)
//    fun (){
//
//    }
//}