package com.lzx.guanchajava.view.fragment.base

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.lzx.guanchajava.R

open class BaseFragment : Fragment() {
    private lateinit var mLoadingProgressBar : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLoadingProgressBar = AlertDialog.Builder(context!!)
                .setCancelable(false)
                .setView(R.layout.layout_loading)
                .create()
    }
    protected fun showLoading() {
        mLoadingProgressBar.show()
    }

    protected fun dismissLoading() {
        mLoadingProgressBar.dismiss()
    }

    fun goto(cls: Class<*>, vararg extras: Pair<String, String>) {
        val intent = Intent(activity, cls)
        for (x in extras) {
            intent.putExtra(x.first, x.second)
        }
        activity!!.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(activity).toBundle())
    }

}