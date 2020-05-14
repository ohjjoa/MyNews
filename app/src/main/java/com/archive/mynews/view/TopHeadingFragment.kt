package com.archive.mynews.view

import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.archive.mynews.R
import com.archive.mynews.api.NewsError
import com.archive.mynews.api.NewsRepository
import com.archive.mynews.api.NewsResponse
import com.archive.mynews.api.Result
import kotlinx.android.synthetic.main.fragment_everything.*
import kotlinx.android.synthetic.main.fragment_top_heading.*
import kotlinx.android.synthetic.main.fragment_top_heading.view.*


/**
 * A simple [Fragment] subclass.
 */
class TopHeadingFragment : Fragment() {

    lateinit var topHeadingRecyclerView : RecyclerView
    private lateinit var adapter : TopHeadingAdapter
    private var page = 1

    var isKeyboardShowing = false
    var keypadBaseHeight = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val articleView = inflater.inflate(R.layout.fragment_top_heading, container, false)

        adapter = TopHeadingAdapter(requireContext())
        topHeadingRecyclerView = articleView.recycler_top_heading as RecyclerView
        topHeadingRecyclerView.adapter = adapter
        topHeadingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        NewsRepository.getTopHeadlines(page = page, callback = object : Result<NewsResponse> {
            override fun onSuccess(response: NewsResponse) {
                adapter.addArticleList(response.articles)
                page += 1
            }

            override fun onFailure(error: NewsError) {

            }
        })
        return articleView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler_top_heading.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recycler_top_heading.canScrollVertically(1)) {

                    NewsRepository.getTopHeadlines(page = page, callback = object : Result<NewsResponse> {
                        override fun onSuccess(response: NewsResponse) {
                            adapter.replaceArticleList(response.articles)
                            page = 1
                        }

                        override fun onFailure(error: NewsError) {

                        }
                    })
                }
            }
        })
    }

    fun refresh() {
        NewsRepository.getTopHeadlines(page = page, callback = object : Result<NewsResponse> {
            override fun onSuccess(response: NewsResponse) {
                adapter.replaceArticleList(response.articles)
                page = 1
            }

            override fun onFailure(error: NewsError) {

            }
        })
    }

}
