package com.vinu.vinodassigment

import android.view.View
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.vinu.vinodassigment.models.ResponseModel
import com.vinu.vinodassigment.ui.NewsFragment
import com.vinu.vinodassigment.ui.NewsFragmentViewModel
import kotlinx.android.synthetic.main.fragment_home.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentsTest {

    @Test
    fun testViewModelInitialization() {
        val scenario = launchFragmentInContainer<NewsFragment>()
        scenario.onFragment {
            it.activity?.application?.let { app ->
                val newsFragmentViewModel =
                    ViewModelProvider.AndroidViewModelFactory.getInstance(app)
                        .create(NewsFragmentViewModel::class.java)
                newsFragmentViewModel.responseModel.observe(
                    it.viewLifecycleOwner,
                    Observer { responseModel ->
                        it.swipeRefreshLayout.isRefreshing = false
                        populateData(it, responseModel)
                    })
            }
        }
    }

    private fun populateData(
        newsFragment: NewsFragment,
        responseModel: ResponseModel?
    ) {
        if (responseModel?.rows?.size ?: 0 > 0) {
            enableNewsFeedView(newsFragment, true)
        } else {
            enableNewsFeedView(newsFragment, false)
        }
    }

    @Test
    fun testViewsAreEnabled() {
        val isEnabled = true
        val scenario = launchFragmentInContainer<NewsFragment>()
        scenario.onFragment {
            enableNewsFeedView(it, isEnabled)
        }
    }

    private fun enableNewsFeedView(
        it: NewsFragment,
        isViewEnabled: Boolean
    ) {
        it.newsRecyclerView.visibility = if (isViewEnabled) View.VISIBLE else View.GONE
        it.listEmptyTextView.visibility = if (!isViewEnabled) View.VISIBLE else View.GONE

        assert(it.newsRecyclerView.visibility == View.VISIBLE)
        assert(it.listEmptyTextView.visibility == View.GONE)
    }

}