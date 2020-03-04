package com.lzx.guanchajava.util

import android.content.Context

const val PREF_SAVE_CONTENT = "_PREF_SAVE_CONTENT_"

object SaveContent {

    private val SAVE_ARTICLE = "SAVE_ARTICLE"
    private val SAVE_COMMENT = "SAVE_COMMENT"

    fun setSaveArticle(status: Boolean) {
        App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE).edit()
                .putBoolean(SAVE_ARTICLE, status)
                .apply()
    }

    fun hasSavedArticle() =
            App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE)
                    .getBoolean(SAVE_ARTICLE, false)

    fun savePostArticle(title: String, content: String) {
        App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE).edit()
                .putString("TITLE", title)
                .putString("CONTENT", content)
                .apply()
    }


    fun getSavedTitle() =
            App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE)
                    .getString("TITLE", "")

    fun getSavedContent() =
            App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE)
                    .getString("CONTENT", "")



    fun setSaveComment(status: Boolean) {
        App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE).edit()
                .putBoolean(SAVE_COMMENT, status)
                .apply()
    }

    fun hasSavedComment() =
            App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE)
                    .getBoolean(SAVE_COMMENT, false)

    fun savePostComment(comment: String) {
        App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE).edit()
                .putString("COMMENT", comment)
                .apply()
    }

    fun clearSavedComment() {
        App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE).edit().clear().apply()
    }

    fun getSavedComment() =
            App.context.getSharedPreferences(PREF_SAVE_CONTENT, Context.MODE_PRIVATE)
                    .getString("COMMENT", "")
}