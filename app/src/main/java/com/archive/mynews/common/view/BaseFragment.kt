package com.archive.mynews.common.view

import androidx.fragment.app.Fragment

open class BaseFragment(layout: Int) : Fragment(layout), BaseContract.View {

    override fun showIndicator() {
        activity?.let {
            if (it is BaseActivity) {
                it.showIndicator()
            }
        }
    }

    override fun hideIndicator() {
        activity?.let {
            if (it is BaseActivity) {
                it.hideIndicator()
            }
        }
    }
}