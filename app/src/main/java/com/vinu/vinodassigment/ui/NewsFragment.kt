package com.vinu.vinodassigment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinu.vinodassigment.ui.adapters.NewsRecyclerViewAdapter
import com.vinu.vinodassigment.R
import com.vinu.vinodassigment.models.NewsModel
import com.vinu.vinodassigment.models.ResponseModel
import com.vinu.vinodassigment.viewmodels.NewsViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class NewsFragment : Fragment() {

    private lateinit var newsViewModel: NewsViewModel
    private val list = ArrayList<NewsModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false)
        activity?.application?.let {
            newsViewModel =
                ViewModelProvider(this).get(NewsViewModel::class.java)
        }
        return rootView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initializeSwipeRefreshLayout()

        initializeNewsRecyclerView()

        observeNewsData()

        if (savedInstanceState == null) {
            fetchNewsFeeds()
        }
    }


    /*
   * Name : observeNewsData()
   *  Description : Observing live news data
   */
    private fun observeNewsData() {
        newsViewModel.responseModel.observe(viewLifecycleOwner, Observer {
            populateData(it)
        })

    }

    /*
    * Name : initializeSwipeRefreshLayout()
    *  Description : intialized the swipe refresh layout and its listener
    */
    private fun initializeSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshLayout.isRefreshing) {
                fetchNewsFeeds()
            }
        }
    }

    /*
    * Name : initializeNewsRecyclerView()
    *  Description : intialized the adapter and recyclerview
    */
    private fun initializeNewsRecyclerView() {
        newsRecyclerView.adapter = NewsRecyclerViewAdapter(list)
        newsRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }


    private fun enableNewsFeedView(isEnabled: Boolean) {
        newsRecyclerView.visibility = if (isEnabled) View.VISIBLE else View.GONE
        listEmptyTextView.visibility = if (!isEnabled) View.VISIBLE else View.GONE
    }

    /*
     * Name : fetchNewsFeeds()
     *  Description : Fetching data from API call and Database
     */
    private fun fetchNewsFeeds() {
        swipeRefreshLayout.isRefreshing = true
        newsViewModel.getNewsFeed()
    }


    /*
     * Name : populateData()
     *  Description : Populating the recyclerview with some handling for display on screen
     */
    private fun populateData(it: ResponseModel?) {
        activity?.title = it?.title
        if (it?.rows?.size ?: 0 > 0) {
            list.clear()
            list.addAll(it?.rows!!)
            newsRecyclerView.adapter?.notifyDataSetChanged()
            enableNewsFeedView(true)
        }
        if (!it?.errorMessage.isNullOrBlank()) {
            Toast.makeText(context, it?.errorMessage, Toast.LENGTH_LONG).show()
        }
        if (list.isEmpty()) {
            enableNewsFeedView(false)
        }
        if (it?.isDataFromDb == false) {
            swipeRefreshLayout.isRefreshing = false
        }
    }
}