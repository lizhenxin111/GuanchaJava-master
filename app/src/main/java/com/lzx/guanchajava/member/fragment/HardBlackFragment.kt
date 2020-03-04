package com.lzx.guanchajava.member.fragment

import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.button.MaterialButton
import com.lzx.guanchajava.POJO.TAG
import com.lzx.guanchajava.view.fragment.base.BaseListFragment
import com.lzx.guanchajava.member.adapter.HardBlackAdapter
import com.lzx.guanchajava.member.bean.DBHardBlack
import com.lzx.guanchajava.member.db.HardBlackDB

class HardBlackFragment : BaseListFragment<HardBlackAdapter>() {

    companion object {
        fun newInstance() = HardBlackFragment()
        fun newInstance(loadNow : Boolean) = HardBlackFragment().apply {
            arguments = Bundle().apply {
                putBoolean(TAG.TAG_FRAGMENT_NEWS_LIST_LOADNOW, loadNow)
            }
        }
    }

    override fun getAdapter(): HardBlackAdapter = HardBlackAdapter(onButtnoClick)

    override fun requestData(mAdapter: HardBlackAdapter, mSwipe: SwipeRefreshLayout) {
        mAdapter.data.clear()
        mAdapter.data.addAll(HardBlackDB.queryAllDB())
        mAdapter.notifyDataSetChanged()
        mSwipe.isRefreshing = false
    }

    private val onButtnoClick = object : HardBlackAdapter.OnButtonClickListener {
        override fun onButtonClick(btn: MaterialButton, user: DBHardBlack) {
            if (user.blacked) {
                btn.text = "拉黑"
                HardBlackDB.deleteDB(user)
            } else {
                btn.text = "移除"
                HardBlackDB.saveUser(user)
            }
            user.blacked = !user.blacked
        }

    }
}