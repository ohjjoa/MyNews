package com.archive.mynews.common.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.archive.mynews.R

@SuppressLint("Registered")
abstract class BaseActivity : AppCompatActivity(), BaseContract.View {

    var busyFlg = false
        private set
    private var progressView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

//    override fun onBackPressed() {
//        // 通信中はバックボタンを無効化
//        if (!busyFlg) {
//            val fragment = supportFragmentManager.findFragmentById(R.id.fragment)
//            if (fragment is OnBackPressedListener) {
//                fragment.onBackPressed()
//            } else {
//                super.onBackPressed()
//            }
//        }
//    }

    @SuppressLint("InflateParams")
    override fun showIndicator() {
        synchronized(this) {
            busyFlg = true
            if (progressView == null) {
                val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT)
                progressView = layoutInflater.inflate(R.layout.progress_loading, null)
                addContentView(progressView, params)
            }
            progressView?.let {
                it.visibility = View.VISIBLE
                it.animate()
                    .alpha(INDICATOR_ALPHA_SHOW)
                    .setDuration(INDICATOR_ANIMATION_DURATION)
                    .setListener(null)
            }
        }
    }

    override fun hideIndicator() {
        synchronized(this) {
            progressView?.let {
                it.animate()
                    .alpha(INDICATOR_ALPHA_HIDE)
                    .setDuration(INDICATOR_ANIMATION_DURATION)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator?) {
                            super.onAnimationEnd(animation)
                            it.visibility = View.GONE
                            busyFlg = false
                        }
                    })
            }
        }
    }

    companion object {
        private const val INDICATOR_ANIMATION_DURATION: Long = 300
        private const val INDICATOR_ALPHA_SHOW = 1.0f
        private const val INDICATOR_ALPHA_HIDE = 0.0f
    }
}