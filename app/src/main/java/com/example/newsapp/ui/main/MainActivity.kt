package com.example.newsapp.ui.main

import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.news.base.BaseActivity
import com.example.newsapp.Adapter.NewsAdapter
import com.example.newsapp.R
import com.example.newsapp.api.ApiManager
import com.example.newsapp.api.model.NewsResponse
import com.example.newsapp.api.model.SourcesResponse
import com.example.newsapp.api.model.Source
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : BaseActivity() {
   var newsAdapter=NewsAdapter(null)
        lateinit var  viewModel: HomeViewModel
        lateinit var viewDataBinding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // setContentView(R.layout.activity_main)
       viewDataBinding = DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
      viewModel  = ViewModelProvider(this).get(HomeViewModel::class.java)
        viewDataBinding.recyclerView.adapter=newsAdapter

        viewModel.getSources()
       // listen- subscribe on live data
        observeLiveData()
    }


fun observeLiveData(){
    viewModel.sourcesLiveData.observe(this,
        // call back
        Observer {data ->
            showTabs(data) //handle view
        })

    viewModel.newsListLiveData.observe(this,
    Observer {
        newsAdapter.changeData(it)
        progress_bar.visibility=View.GONE

    })
    viewModel.messageLiveData.observe(this, Observer {
        showMessage(message = it,posActionName = getString(R.string.ok)
            ,posAction = DialogInterface.OnClickListener{ dialogInterface, i ->
                dialogInterface.dismiss()
            })

    })
    viewModel.showProgressBar.observe(this, Observer {
        progress_bar.visibility=View.GONE
    })

}


 fun showTabs(source:List<Source?>?){
    source?.forEach {
        val tab = tab_layout.newTab()
        tab.setText(it?.name)
        tab.setTag(it)
        tab_layout.addTab(tab)

    }

    tab_layout.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
        override fun onTabSelected(tab: TabLayout.Tab?) {
            val source =tab?.tag as Source?

           viewModel.getNews(source?.id)

        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {

        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
            val source =tab?.tag as Source?
            //getNews(source?.id)
            viewModel.getNews(source?.id)

        }

    })

     viewDataBinding.tabLayout.getTabAt(0)?.select()
}

}