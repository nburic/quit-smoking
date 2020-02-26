package com.example.sampleapp.ui.trophies

import com.example.sampleapp.R
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.mt_item_image.view.*


class ImageItem(private val imgRes: Int,
                private val achieved: Boolean) : Item() {

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.root.img_badge.setImageResource(imgRes)

        when (achieved) {
            true -> viewHolder.root.alpha = 1f
            false -> viewHolder.root.alpha = 0.3f
        }
    }

    override fun getLayout(): Int {
        return R.layout.mt_item_image
    }

    override fun getSpanSize(spanCount: Int, position: Int): Int {
        return 1
    }
}