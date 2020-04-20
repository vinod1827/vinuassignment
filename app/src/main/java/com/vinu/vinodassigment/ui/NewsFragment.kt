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
import com.vinu.vinodassigment.utils.Constants.RESPONSE_DATA_KEY
import com.vinu.vinodassigment.utils.isNetworkAvailable
import kotlinx.android.synthetic.main.fragment_home.*


class NewsFragment : Fragment() {

    private var newsFragmentViewModel: NewsFragmentViewModel? = null
    private val list = ArrayList<NewsModel>()
    private var responseModel: ResponseModel? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        activity?.application?.let {
            newsFragmentViewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(it)
                .create(NewsFragmentViewModel::class.java)
        }

        if (savedInstanceState != null) {
            responseModel = savedInstanceState.getParcelable(RESPONSE_DATA_KEY)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        initializeSwipeRefreshLayout()

        initializeNewsRecyclerView()

        newsFragmentViewModel?.responseModel?.observe(viewLifecycleOwner, Observer {
            responseModel = it
            swipeRefreshLayout.isRefreshing = false
            populateData(it)
        })

        if (responseModel == null) {
            fetchNewsFeeds()
        } else {
            populateData(responseModel)
        }
    }

    private fun initializeSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener {
            if (swipeRefreshLayout.isRefreshing) {
                fetchNewsFeeds()
            }
        }
    }

    private fun initializeNewsRecyclerView() {
        newsRecyclerView.adapter = NewsRecyclerViewAdapter(list)
        newsRecyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
    }

    private fun enableNewsFeedView(isEnabled: Boolean) {
        newsRecyclerView.visibility = if (isEnabled) View.VISIBLE else View.GONE
        listEmptyTextView.visibility = if (!isEnabled) View.VISIBLE else View.GONE
    }

    private fun fetchNewsFeeds() {
        if (isNetworkAvailable(activity)) {
            swipeRefreshLayout.isRefreshing = true
            newsFragmentViewModel?.getNewsFeed()
        } else {
            swipeRefreshLayout.isRefreshing = false
            Toast.makeText(
                activity,
                getString(R.string.you_are_offline),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun populateData(it: ResponseModel?) {
        list.clear()
        activity?.title = it?.title
        if (it?.rows?.size ?: 0 > 0) {
            list.addAll(it?.rows!!)
            newsRecyclerView.adapter?.notifyDataSetChanged()
            enableNewsFeedView(true)
        } else {
            enableNewsFeedView(false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelable(RESPONSE_DATA_KEY, responseModel)
    }
}