package com.archive.mynews.view

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.archive.mynews.R
import com.archive.mynews.api.NewsError
import com.archive.mynews.api.NewsRepository
import com.archive.mynews.api.NewsResponse
import com.archive.mynews.api.Result
import com.archive.mynews.common.view.BaseFragment
import kotlinx.android.synthetic.main.fragment_everything.*
import kotlinx.android.synthetic.main.fragment_everything.view.*
import java.lang.String


/**
 * A simple [Fragment] subclass.
 */
class EverythingFragment : BaseFragment(R.layout.fragment_top_heading) {
    lateinit var everythingRecyclerView : RecyclerView
    private lateinit var adapter : EverythingAdapter
    private var page = 1

    // 처음 키워드
    private var keyword = "korea"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val articleView = inflater.inflate(R.layout.fragment_everything, container, false)

        adapter = EverythingAdapter(requireContext())
        everythingRecyclerView = articleView.recycler_everything as RecyclerView
        everythingRecyclerView.adapter = adapter
        everythingRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        search()
        return articleView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (newsSearch != null) {
            showIndicator()
            newsSearch.setOnEditorActionListener(TextView.OnEditorActionListener { v, actionId, event ->
                when (actionId) {
                    EditorInfo.IME_ACTION_SEARCH -> {
                        val imm: InputMethodManager =
                            activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(getView()!!.windowToken, 0)
                        page = 1
                        keyword = newsSearch.text.toString()
                        search()
                        hideIndicator()
                    }

                    else -> {
                        showIndicator()
                        Toast.makeText(context, "검색실패", Toast.LENGTH_SHORT).show()
                        return@OnEditorActionListener false
                    }
                }
                true
            })
        }

        showIndicator()
        recycler_everything.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recycler_everything.canScrollVertically(1)) {
                    NewsRepository.getKeywordNews(keyword, page = page, callback = object : Result<NewsResponse> {
                        override fun onSuccess(response: NewsResponse) {
                            adapter.addArticleList(response.articles)
                            page += 1
                            hideIndicator()
                        }

                        override fun onFailure(error: NewsError) {
                            hideIndicator()
                        }
                    })
                }
            }
        })
    }

    // 검색을 수행하는 메소드
    private fun search() {
//        if (newsSearch.isEmpty()) {
//            adapter.addArticleList(articleList)
//        } else {
//            val filteredList = mutableListOf<Article>()
//            for (article in articleList) {
//                if (article.title.contains(newsSearch)
//                    || article.description.contains(newsSearch)) {
//                    filteredList.add(article)
//                }
//            }
//            adapter.addArticleList(filteredList)
//        }

        showIndicator()
        NewsRepository.getKeywordNews(keyword, page = page, callback = object : Result<NewsResponse> {
            override fun onSuccess(response: NewsResponse) {
                Toast.makeText(context, "검색성공", Toast.LENGTH_SHORT).show()
                recycler_everything.scrollToPosition(0)
                adapter.replaceArticleList(response.articles)
                page += 1
                hideIndicator()
            }

            override fun onFailure(error: NewsError) {
                Toast.makeText(context, "검색실패", Toast.LENGTH_SHORT).show()
                hideIndicator()
            }
        })
    }
}