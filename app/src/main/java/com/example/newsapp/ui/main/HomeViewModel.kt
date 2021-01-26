package com.example.newsapp.ui.main

import android.content.DialogInterface
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.NewsItem
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.Source
import com.example.newsapp.api.model.SourcesResponse
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    // handel logic - hold data

    val sourcesLiveData = MutableLiveData<List<Source?>>()
    val newsListLiveData= MutableLiveData<List<NewsItem?>>()
    val messageLiveData = MutableLiveData<String>()
    val showProgressBar = MutableLiveData<Boolean>()

    fun getSources(){
        ApiManager.getApi().getSources("4dfbba62ede4466c99f70e144c3c3eb6","en")
            .enqueue(object : Callback<SourcesResponse> {
                override fun onResponse(
                    call: Call<SourcesResponse>,
                    response: Response<SourcesResponse>
                ) {
                    if(response.isSuccessful&&response.body()?.status.equals("ok")){
                       // showTabs(response.body()?.sources)
                        //data is exist -> put in the live data
                        sourcesLiveData.value= response.body()?.sources

                    }else{
                        messageLiveData.value=response.body()?.message?:""

                        /*   showMessage(message = response.body()?.message?:"",posActionName = getString(
                               R.string.ok
                           )
                               ,posAction = DialogInterface.OnClickListener{ dialogInterface, i ->
                                   dialogInterface.dismiss()
                               })*/
                    }
                }

                override fun onFailure(call: Call<SourcesResponse>, t: Throwable) {
                    messageLiveData.value= t.localizedMessage
                 /*   showMessage(message = t.localizedMessage,posActionName = getString(R.string.ok)
                        ,posAction = DialogInterface.OnClickListener{ dialogInterface, i ->
                            dialogInterface.dismiss()
                        })*/
                }

            })
    }
    fun getNews(sourceId: String?) {
        ApiManager.getApi().getNews("4dfbba62ede4466c99f70e144c3c3eb6",sourceId!!,"en")
            .enqueue(object : Callback<NewsResponse>{
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    showProgressBar.value=false
                    //progress_bar.visibility= View.GONE
                    if(response.isSuccessful&&response.body()?.status.equals("ok")){
                        val list = response.body()?.articles
                     //   newsAdapter.changeData(list)
                       // newsListLiveData.postValue(list) thread safety
                        newsListLiveData.value = list
                    } else {
                        messageLiveData.value=response.body()?.message?:""

                        /*  showMessage(message = response.body()?.message?:"",posActionName = getString(
                              R.string.ok
                          )
                              ,posAction = DialogInterface.OnClickListener{ dialogInterface, i ->
                                  dialogInterface.dismiss()
                              })*/
                    }

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    messageLiveData.value= t.localizedMessage

                    /*   showMessage(message = t.localizedMessage,posActionName = getString(R.string.ok)
                           ,posAction = DialogInterface.OnClickListener{ dialogInterface, i ->
                               dialogInterface.dismiss()
                           })*/
                }

            })
    }
}