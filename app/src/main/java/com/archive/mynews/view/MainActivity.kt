package com.archive.mynews.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import androidx.viewpager.widget.ViewPager
import com.archive.mynews.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_everything.*

class MainActivity : AppCompatActivity(), ChangeCountryDialogFragment.ChangeCountryListener {

    var isKeyboardShowing = false
    var keypadBaseHeight = 0

    private val contentAnimDuration = 300L
    private val contentAnimDelay = 400L

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
        val fragment = supportFragmentManager.fragments[viewPager.currentItem]
        if (fragment is TopHeadingFragment) {
            fragment.refresh()
        }
    }

//    private fun onGlobalLayout() {
//        val r = Rect()
//        rootView.getWindowVisibleDisplayFrame(r)
//        val screenHeight: Int = rootView.rootView.height
//        val keypadHeight: Int = screenHeight - r.bottom
//        if (keypadBaseHeight == 0) {
//            keypadBaseHeight = keypadHeight
//        }
//        if (keypadHeight > screenHeight * 0.15) {
//            if (!isKeyboardShowing) {
//                isKeyboardShowing = true
//                rootView.setPadding(0, 0, 0, keypadHeight)
//                val height: Int = screenHeight - keypadBaseHeight
//            }
//        }
//    }

//    private fun animateContentHeight(params: ViewGroup.MarginLayoutParams, targetMargin: Int) {
//        val paramsAnimator = ObjectAnimator.ofInt(params.bottomMargin, targetMargin).apply {
//            duration = contentAnimDuration
//            interpolator = FastOutSlowInInterpolator()
//            startDelay = contentAnimDelay
//        }
//        paramsAnimator.addUpdateListener {
//            params.bottomMargin = it.animatedValue as Int
//            rootView.layoutParams = params
//        }
//        paramsAnimator.start()
//    }
}
