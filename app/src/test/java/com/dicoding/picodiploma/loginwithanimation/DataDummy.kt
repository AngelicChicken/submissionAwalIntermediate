package com.dicoding.picodiploma.loginwithanimation

import com.dicoding.picodiploma.loginwithanimation.data.api.response.ListStoryItem
import kotlin.text.Typography.quote

class DataDummy {
    companion object {
        fun generateDummyStoryResponse(): List<ListStoryItem> {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "date + $i",
                "nama + $i",
                "deskripsi + $i",
                0.0,
                "id + $i",
                0.0
            )
            items.add(story)
        }
        return items
    }
    }
}