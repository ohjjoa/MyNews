package com.archive.mynews.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.viewpager.widget.ViewPager
import com.archive.mynews.R
import com.archive.mynews.common.view.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity(), ChangeCountryDialogFragment.ChangeCountryListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TopHeadingAdapter.ViewHolder(viewPager)

        val fragmentAdapter = MyNewsPagerAdapter(supportFragmentManager)
        viewPager.adapter = fragmentAdapter
        tabLayout.setupWithViewPager(viewPager)

        viewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {


            }
            override fun onPageSelected(position: Int) {
                if (position == 0) {
                    button_change_country.visibility = View.VISIBLE
                } else {
                    button_change_country.visibility = View.GONE
                }
                // 페이지 이동시 hide keyboard
                (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).also {
                    it.hideSoftInputFromWindow(window.currentFocus!!.windowToken, 0)
                }

                if (position != 1) {
                    // 키보드 처리
                }
            }

        })

        // TODO: 국가변경 액티비티 화면 이동용 테스트 버튼입니다.
        //  나중에 화면 디자인에 맞춰서 변경해주세요.
        button_change_country.setOnClickListener {
            ChangeCountryDialogFragment.show(fragmentManager = supportFragmentManager)
        }
    }

    override fun onClickChange() {
        //Toast.makeText(this, "국가변경 완료", Toast.LENGTH_SHORT).show()
        // TODO: 국가변경 완료됐으므로 화면 갱신
        // TODO: TopHeadingFragment한테 갱신하라고 연락하기
        showIndicator()
        val fragment = supportFragmentManager.fragments[viewPager.currentItem]
        if (fragment is TopHeadingFragment) {
            hideIndicator()
            fragment.refresh()
        }
    }

}
