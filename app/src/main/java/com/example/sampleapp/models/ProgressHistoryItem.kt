package com.example.sampleapp.models


class ProgressHistoryItem(
    var icon: Int,
    var value: String,
    type: ProgressHistoryItemType) {

    val title = when (type) {
        ProgressHistoryItemType.LIFE -> {
            "[Lost life]"
        }
        ProgressHistoryItemType.MONEY -> {
            "[Spent money]"
        }
        ProgressHistoryItemType.SMOKE -> {
            "[Cigarettes smoked]"
        }
    }

}