package com.vinu.vinodassigment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vinu.vinodassigment.ui.adapters.NewsRecyclerViewAdapter
import com.vinu.vinodassigment.R
import com.vinu.vinodassigment.api.RetrofitBuilder
import com.vinu.vinodassigment.database.NewsRoomDatabase
import com.vinu.vinodassigment.models.NewsModel
import com.vinu.vinodassigment.models.ResponseModel
import com.vinu.vinodassigment.repository.NewsRepository
import kotlinx.android.synthetic.main.fragment_home.*

class NewsFragment : Fragment() {

    private lateinit var newsFragmentViewModel: NewsFragmentViewModel
    private val list = ArrayList<NewsModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_home, container, false)

        val database = NewsRoomDatabase.getDatabase(requireContext().applicationContext)

        val repository = NewsRepository(RetrofitBuilder.apiService, database)

        val factory = ListViewModelFactory(repository)

        activity?.application?.let {
            newsFragmentViewModel =
                ViewModelProvider(this, factory).get(NewsFragmentViewModel::class.java)
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

    private fun observeNewsData() {
        newsFragmentViewModel.responseModel.observe(viewLifecycleOwner, Observer {
            populateData(it)
        })

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
        swipeRefreshLayout.isRefreshing = true
        newsFragmentViewModel.getNewsFeed()
    }

    private fun populateData(it: ResponseModel?) {
        activity?.title = it?.title
        if (it?.rows?.size ?: 0 > 0) {
            list.clear()
            list.addAll(it?.rows!!)
            newsRecyclerView.adapter?.notifyDataSetChanged()
            enableNewsFeedView(true)
        }
        if (!it?.exceptionMsg.isNullOrBlank()) {
            Toast.makeText(context, it?.exceptionMsg, Toast.LENGTH_LONG).show()
        }
        if (list.isEmpty()) {
            enableNewsFeedView(false)
        }
        if (it?.isDataFromDb == false) {
            swipeRefreshLayout.isRefreshing = false
        }
    }

    @Suppress("UNCHECKED_CAST")
    class ListViewModelFactory(var repository: NewsRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsFragmentViewModel::class.java)) {
                return NewsFragmentViewModel(repository) as T
            }
            throw IllegalArgumentException("ViewModel class problem")
        }
    }
}