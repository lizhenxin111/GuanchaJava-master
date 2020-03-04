package com.lzx.guanchajava.view.activity

import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.kittinunf.fuel.Fuel
import com.google.gson.Gson
import com.lzx.guanchajava.POJO.api.Api
import com.lzx.guanchajava.POJO.bean.userDetail.message.SendMessage
import com.lzx.guanchajava.POJO.bean.userDetail.messageDetail.MessageList
import com.lzx.guanchajava.POJO.bean.userDetail.messageDetail.Msg
import com.lzx.guanchajava.R
import com.lzx.guanchajava.adapter.userDetailAdapter.userMessageDerail.MessageListAdapter
import com.lzx.guanchajava.adapter.userDetailAdapter.userMessageDerail.TypeReceive
import com.lzx.guanchajava.adapter.userDetailAdapter.userMessageDerail.TypeSend
import com.lzx.guanchajava.util.ResourceUtil
import com.lzx.guanchajava.util.TimeUtil
import kotlinx.android.synthetic.main.activity_user_message.*
import kotlinx.android.synthetic.main.content_user_message.*
import org.jetbrains.anko.longToast
import org.jetbrains.anko.sdk27.coroutines.onClick

/**
 * 私信
 */
class UserMessageActivity : BaseActivity() {
    override fun loadUI() {
        setContentView(R.layout.activity_user_message)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "与 $NAME 的对话啊"

        message_list.layoutManager = LinearLayoutManager(applicationContext)
        message_list.adapter = mAdapter

        message_send.onClick { sendMessage(message_edit.text.toString()) }
    }

    override fun loadContent() {
        loadMessage()
    }

    override fun loadOther() {
        UID = intent.getStringExtra("UID")
        NAME = intent.getStringExtra("NAME")
    }

    private lateinit var UID: String
    private lateinit var NAME: String

    private var pageNo = 1
    private lateinit var mMsgFormat: Msg

    private val mAdapter = MessageListAdapter(listOf(TypeSend(), TypeReceive()))

    private fun loadMessage() {
        Fuel.post(Api.messageList(), listOf("touid" to UID, "page_no" to pageNo++, "page_size" to "20")).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val t = result.get()
                val data = Gson().fromJson(t, MessageList::class.java).data.items
                val list = data.msgs


                val senderUid = data.sender[0].uid
                val senderPic = data.sender[0].avatar
                val receiverPic = data.receiver[0].avatar
                val receiverUid = data.receiver[0].uid

                for (x in list) {
                    if (x.sender_id == senderUid) {
                        x.isSender = true
                        x.avater = senderPic
                    } else {
                        x.isSender = false
                        x.avater = receiverPic
                    }
                }

                mAdapter.data.addAll(list.asReversed())
                mAdapter.notifyDataSetChanged()
                scrollToBottom()


                if (!::mMsgFormat.isInitialized) {
                    mMsgFormat = Msg("", "", false, 0, receiverUid, senderUid, true, senderPic)
                }

                Api.setMessageRead(UID)
            }
        }
    }

    private fun sendMessage(msg: String) {
        if (msg.isNullOrBlank()) return
        Fuel.post(Api.sendMessage(), listOf("touid" to UID, "msg" to msg)).responseObject(SendMessage.Deserializer()) { request, response, result ->
            val (data, error) = result
            if (error != null) {
                longToast(ResourceUtil.getString(R.string.str_no_network))
            } else {
                val r = result.get()
                Toast.makeText(applicationContext, "发送${r.msg}", Toast.LENGTH_SHORT).show()
                if (r.code == 0) {      //向列表中添加发送的消息
                    val sendedData = mMsgFormat
                    sendedData.content = msg
                    sendedData.created_at = TimeUtil.timeAll()
                    mAdapter.data.add(sendedData)
                    mAdapter.notifyItemInserted(mAdapter.itemCount)
                    scrollToBottom()
                    message_edit.text = Editable.Factory.getInstance().newEditable("")
                    message_edit.clearFocus()
                }
            }
        }
    }

    private fun refreshMessage() {
        loadMessage()
    }

    private fun scrollToBottom() {
        message_list.scrollToPosition(mAdapter.itemCount - 1)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_message_list, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.menu_refresh -> refreshMessage()
        }
        return super.onOptionsItemSelected(item)
    }
}
