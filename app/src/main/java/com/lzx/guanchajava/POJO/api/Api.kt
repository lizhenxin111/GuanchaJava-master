package com.lzx.guanchajava.POJO.api

import com.github.kittinunf.fuel.Fuel
import com.lzx.guanchajava.R
import com.lzx.guanchajava.util.App
import com.lzx.guanchajava.util.LoginUtil
import com.lzx.guanchajava.util.ResourceUtil
import org.jetbrains.anko.longToast

object Api {
    fun officalRegister() = "https://m.guancha.cn:443/register.html"
    fun officalRetrieve() = "https://m.guancha.cn:443/resetpassword.html"

    fun yaowen(): String {
        return "https://app.guancha.cn:443/news/yaowen-new.json?pageNo="
    }   //要闻

    fun shiping(): String {
        return "https://app.guancha.cn:443/news/common-list.json?type=1&id=&pageNo="
    }   //时评

    fun pyq(): String {
        return "https://app.guancha.cn:443/news/common-list.json?type=2&id=&pageNo="
    }   //朋友圈

    fun caij(): String {
        return "https://app.guancha.cn:443/news/common-list.json?type=5&id=CaiJing&pageNo="
    }   //财经

    fun chanj(): String {
        return "https://app.guancha.cn:443/news/common-list.json?type=5&id=ChanJing&pageNo="
    }   //产经

    fun kej(): String {
        return "https://app.guancha.cn:443/news/common-list.json?type=5&id=GongYe%C2%B7KeJi&pageNo="
    }
    fun qic() : String {
        return "https://app.guancha.cn:443/news/common-list.json?type=5&id=qiche&pageNo="
    }
    fun guoj() : String {
        return "https://app.guancha.cn:443/news/common-list.json?type=5&id=GuoJi%C2%B7ZhanLue&pageNo="
    }
    fun junshi() : String {
        return "https://app.guancha.cn:443/news/common-list.json?type=5&id=JunShi&pageNo="
    }
    fun ship() : String {
        return "https://app.guancha.cn:443/news/common-list.json?type=4&id=GuanWangKanPian&pageNo&pageNo="
    }
    fun xinshd() : String {
        return "https://app.guancha.cn:443/news/common-list.json?type=4&id=XinShiDai&pageNo&pageNo="
    }
    fun gund() : String {
        return "https://app.guancha.cn:443/news/common-list.json?type=3&id=&pageNo="
    }

    fun fwZxfb(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=2&page_no="
    }   //风闻最新发布

    fun fwZl(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=5&page_no="
    }   //风闻专栏

    fun fwZxhf(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=1&page_no="
    }   //风闻最新回复

    fun fwZr24h(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=3&page_no="
    }   //风闻最热24h

    fun fwZr7(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=7&page_no="
    }

    fun fwZr3(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=6&page_no="
    }

    fun fwZr3m(): String {
        return "https://app.guancha.cn:443/main/get-index-list?order=8&page_no="
    }


    fun newsRecomend(articleId: String): String {
        return "https://api.guancha.cn:443/Appdata/getDetailExtend?id=$articleId"
    }

    fun postRecomend(articleId: String): String {
        return "https://app.guancha.cn:443/user/get-recommend?post_id=$articleId"
    }

    fun xwnr(id: String, type: String): String {
        return "https://app.guancha.cn:443/news/content?access-token=${LoginUtil.getToken()}&id=${id}&type=${type}"
    }   //新闻内容

    fun bqnr(id: String): String {
        return "https://app.guancha.cn:443/news/common-list.json?type=4&id=${id}&pageNo="
    }   //标签内容

    fun fwnr(id: String): String {
        return "https://app.guancha.cn:443/post/get-post-by-id?access-token=${LoginUtil.getToken()}&post_id=$id"
    }   //风闻内容

    fun postTopicReply(id: String): String {
        return "https://app.guancha.cn:443/post/get-post-by-topicid?topic_id=$id&order=1&page_no="
    }

    fun postTopicPublish(id: String): String {
        return "https://app.guancha.cn:443/post/get-post-by-topicid?topic_id=$id&order=2&page_no="
    }

    fun getComment(id: String, type: String): String {
        return "https://app.guancha.cn:443/comment/cmt-list.json?access-token=${LoginUtil.getToken()}&codeId=$id&codeType=$type&pageNo="
    }   //文章的评论。codetype==1：新闻；codetype==2：风闻

    fun getChildComment(id: String): String {
        return "https://app.guancha.cn:443/comment/get-child-comments?access-token=${LoginUtil.getToken()}&comment_id=$id"
    }   //楼中楼回复

    fun zlzj(): String {
        return "https://api.guancha.cn:443/Appdata/AuthorList/"
    }   //专栏作家

    fun zjxq(id: String, pageNo: String): String {
        return "https://api.guancha.cn:443/Appdata/NewsList/?newstype=0&size=20&id=$id&type=2&page="
    }   //作家详情

    fun userInfo(): String {
        return "https://app.guancha.cn:443/user/get-user-info?access-token=${LoginUtil.getToken()}"
    }   //用户信息post,参数：uid

    fun userComment(id: String): String {
        return "https://app.guancha.cn:443/user/get-user-comments-v2?uid=${id}&page_size=20&page_no="
    }   //用户的评论列表

    fun userPraise(id: String): String {
        return "https://app.guancha.cn:443/user/get-praise-list?uid=${id}&page_size=20&page_no="
    }   //用户的点赞列表

    fun userCollect(id: String): String {
        return "https://app.guancha.cn:443/user/get-collection-list?uid=${id}&type=1&from=2&page_size=20&page_no="
    }   //用户的收藏列表

    fun userPublish(id: String): String {
        return "https://app.guancha.cn:443/user/get-published-list?uid=${id}&page_size=20&page_no="
    }   //用户发表的文章

    fun userAttention(id: String): String {
        return "https://app.guancha.cn:443//user/get-attention-user-list?uid=${id}&access-token=${LoginUtil.getToken()}&page_size=20&page_no="
    }   //用户关注列表

    fun userFans(id: String): String {
        return "https://app.guancha.cn:443/user/get-fans-list?uid=${id}&access-token=${LoginUtil.getToken()}&page_size=20&page_no="
    }   //用户粉丝

    fun userVoted() = "https://app.guancha.cn:443/user/get-notice-by-type?access-token=${LoginUtil.getToken()}&page_size=20&type=vote&page_no="

    fun editUsername(): String {
        return "https://app.guancha.cn:443/user/edit-nickname?access-token=${LoginUtil.getToken()}"
    }   //修改昵称post,user_nick

    fun editOtherInfo(): String {
        return "https://app.guancha.cn:443/user/edit-user-others?access-token=${LoginUtil.getToken()}"
    }   //修改其他信息post, user_description=没啥签名&user_profession=&user_graduate=&user_sex=0

    fun hotKey(): String {
        return "https://app.guancha.cn:443/main/get-hot-keyword"
    }   //热搜

    fun searchNews(word: String): String {
        return "https://app.guancha.cn:443/main/search-v2?keyword=$word&type=search_news&page="
    }

    fun searchPost(word: String): String {
        return "https://app.guancha.cn:443/main/search-v2?keyword=$word&type=search_post&page="
    }

    fun searchPostTopic(word: String): String {
        return "https://app.guancha.cn:443/main/search-v2?keyword=$word&type=search_topic&access-token=${LoginUtil.getToken()}&page="
    }

    fun searchNewsTopic(word: String): String {
        return "https://app.guancha.cn:443/main/search-v2?keyword=$word&type=search_special&access-token=${LoginUtil.getToken()}&page="
    }

    fun searchUser(user: String): String {
        return "https://app.guancha.cn:443/main/search-v2?keyword=$user&type=search_user&access-token=${LoginUtil.getToken()}&page="
    }

    fun searchAuthor(author: String): String {
        return "https://app.guancha.cn:443/main/search-v2?keyword=$author&type=search_news_author&access-token=${LoginUtil.getToken()}&page="
    }


    fun getMessage(): String {
        return "https://app.guancha.cn:443/user/get-msg?access-token=${LoginUtil.getToken()}"
    }   //私信post:page_no,page_size

    fun messageList(): String {
        return "https://app.guancha.cn:443/user/get-msg-list?access-token=${LoginUtil.getToken()}"
    }   //和某个人的私信列表post: touid=274211&page_no=1&page_size=20

    fun sendMessage(): String {
        return "https://app.guancha.cn:443/user/send-msg?access-token=${LoginUtil.getToken()}"
    }   //发送私信post:touid,msg

    fun login(): String {
        return "https://app.guancha.cn:443/user/login"
    }   //登录post:username&password&from=3&phone_code=86

    fun isTokenValid(): String {
        return "https://app.guancha.cn:443//user/is-valid-access-token?token=${LoginUtil.getToken()}"
    }   //token是否有效

    fun publishComment(): String {
        return "https://app.guancha.cn:443/comment/create?access-token=${LoginUtil.getToken()}"
    }   //发表评论parant_id=0&from=cms&type=1&access_device=3&code_id=392767&content=科技改变生活。。。     type=1:新闻type=2:风闻

    fun hasNewNotice(): String {
        return "https://app.guancha.cn:443/user/has-new-notice?access-token=${LoginUtil.getToken()}"
    }   //是否有新通知

    fun noticeCount(): String {
        return "https://app.guancha.cn:443/user/get-all-notice-count?access-token=${LoginUtil.getToken()}"
    }   //通知数量post

    fun replied(uid: Int): String {
        return "https://app.guancha.cn:443/user/get-reply-list-v2?access-token=${LoginUtil.getToken()}&uid=$uid&page_size=20&page_no="
    }   //被回复

    fun praised(uid: Int): String {
        return "https://app.guancha.cn:443/user/get-notice-by-type?uid=$uid&access-token=${LoginUtil.getToken()}&page_size=20&type=collection_praise&page_no="
    }   //被点赞

    fun followed(): String {
        return "https://app.guancha.cn:443/user/get-notice-by-type?access-token=${LoginUtil.getToken()}&page_size=20&type=attention&page_no="
    }   //被关注

    fun subscription(): String {
        return "https://app.guancha.cn:443/user/get-subscription?access-token=${LoginUtil.getToken()}&page_size=20&page_no="
    }   //订阅

    fun blackList(): String {
        return "https://app.guancha.cn:443/user/get-blacklist?access-token=${LoginUtil.getToken()}&page_size=20&page_no="
    }   //黑名单列表，get

    fun attentionUser(): String {
        return "https://app.guancha.cn:443/user/attention?access-token=${LoginUtil.getToken()}"
    }   //post:touid

    fun attentionPostTopic(): String {
        return "https://app.guancha.cn:443/user/attention-topic?access-token=${LoginUtil.getToken()}"
    }   //post:topic_id

    fun voteTo(): String {
        return "https://app.guancha.cn:443/user/do-vote?access-token=${LoginUtil.getToken()}"
    }   //投票post: post_id投票栏目id，vote_extend_ids投票项目id

    fun clearNotice(type: String) {
        val url = "https://app.guancha.cn:443/user/clear-notice-by-type?access-token=${LoginUtil.getToken()}"
        Fuel.post(url, listOf("type" to type)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            }
        }
    }

    fun setMessageRead(uid: String) {
        val url = "https://app.guancha.cn:443/user/set-msg-read-by-uid?access-token=${LoginUtil.getToken()}"
        Fuel.post(url, listOf("uid" to uid)).responseString { request, response, result ->
            val (data, error) = result
            if (error != null) {
                App.context.longToast(ResourceUtil.getString(R.string.str_no_network))
            }
        }
    }

    fun collectArticle(): String {
        return "https://app.guancha.cn:443/user/collect?access-token=${LoginUtil.getToken()}"
    }   //收藏code_id&from=2&type=1新闻2风闻

    fun praiseTopic(): String {
        return "https://app.guancha.cn:443/user/praise-post?access-token=${LoginUtil.getToken()}"
    }   //赞文章post_id=93972&from=2

    fun praiseComment(): String {
        return "https://app.guancha.cn:443/comment/praise?access-token=${LoginUtil.getToken()}"
    }   //点赞回复comment_id=12130499&from=cms

    fun treadComment(): String {
        return "https://app.guancha.cn:443/comment/tread?access-token=${LoginUtil.getToken()}"
    }   //踩回复id=12130499&from=cms

    fun black(): String {
        return "https://app.guancha.cn:443/user/blacklist?access-token=${LoginUtil.getToken()}"
    }   //拉黑、解除拉黑，post:touid


    fun sendCaptcha(): String {
        return "https://app.guancha.cn:443/user/send-phone-message"
    }   //发送验证码post:mobile=18743227146&phone_code=86

    fun checkCaptcha(): String {
        return "https://app.guancha.cn:443/user/check-message-captcha"
    }   //检查验证码psot:mobile=18743227146&captcha=9998&phone_code=86

    fun signUp(): String {
        return "https://app.guancha.cn:443/user/signup"
    }   //注册post:user_nick=测试1号&password=li.774717903&captcha=9998&mobile=18743227146&from=3&phone_code=86

    fun retrieve(): String {
        return "https://app.guancha.cn:443/user/reset-password"
    }

    fun shareInfo(id: String, type: String) =
            if (type.equals("1")) "https://app.guancha.cn/news/share-data.json?id=$id"
            else "https://app.guancha.cn/post/share-data.json?id=$id"

    fun postArticle(): String {
        return "https://app.guancha.cn:443/post/publish-post?access-token=${LoginUtil.getToken()}"
    }   //风闻发帖，post：topic&title&content&is_anonymous=0&accessDevice=3

    fun postArticleTopics(): String {
        return "https://app.guancha.cn:443/topic/get-select-topic"
    }

    fun feedback(): String {
        return "https://api.github.com/repos/lizhenxin111/GuanchaJava/issues?access_token=16d11f2f2f423e02e2ada07b9fa626d485b56e89"
    }
}