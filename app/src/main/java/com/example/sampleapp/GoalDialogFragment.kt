package com.example.sampleapp

import android.content.Context
import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_goal_list_dialog.*
import kotlinx.android.synthetic.main.fragment_goal_list_dialog_item.view.*

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    GoalDialogFragment.newInstance().show(supportFragmentManager, TAG)
 * </pre>
 *
 * You activity (or fragment) needs to implement [GoalDialogFragment.Listener].
 */
class GoalDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "GoalDialogFragment"

        fun newInstance(): GoalDialogFragment {
            return GoalDialogFragment()
        }
    }

    private var mListener: Listener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_goal_list_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        list.layoutManager = LinearLayoutManager(context)
        list.adapter = GoalAdapter()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val parent = parentFragment
        if (parent != null) {
            mListener = parent as Listener
        } else {
            mListener = context as Listener
        }
    }

    override fun onDetach() {
        mListener = null
        super.onDetach()
    }

    interface Listener {
        fun onGoalClicked(position: Int)
    }

    private inner class ViewHolder internal constructor(inflater: LayoutInflater, parent: ViewGroup)
        : RecyclerView.ViewHolder(inflater.inflate(R.layout.fragment_goal_list_dialog_item, parent, false)) {

        internal val text: TextView = itemView.text

        init {
            text.setOnClickListener {
                mListener?.let {
                    it.onGoalClicked(adapterPosition)
                    dismiss()
                }
            }
        }
    }

    private inner class GoalAdapter internal constructor() : RecyclerView.Adapter<ViewHolder>() {

        private val mItemCount = 10

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(parent.context), parent)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.text.text = position.toString()
        }

        override fun getItemCount(): Int {
            return mItemCount
        }
    }
}
