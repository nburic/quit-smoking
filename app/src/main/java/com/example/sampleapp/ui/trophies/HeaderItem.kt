//package com.example.sampleapp.ui.trophies
//
//import com.example.sampleapp.R
//import com.xwray.groupie.kotlinandroidextensions.Item
//import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
//import kotlinx.android.synthetic.main.mt_item_header.view.*
//
//
//internal class HeaderItem(private val title: Int) : Item() {
//
//    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
//        viewHolder.root.tv_header_title.text = viewHolder.root.context.getString(title)
//    }
//
//    override fun getLayout(): Int {
//        return R.layout.mt_item_header
//    }
//
//    override fun getSpanSize(spanCount: Int, position: Int): Int {
//        return 2
//    }
//}