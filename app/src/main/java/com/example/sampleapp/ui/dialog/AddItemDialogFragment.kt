package com.example.sampleapp.ui.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.sampleapp.R
import com.example.sampleapp.databinding.FragmentDialogAddItemBinding

class AddItemDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "AddItemDialogFragment"
    }

    private lateinit var binding: FragmentDialogAddItemBinding

    var onSubmitClick: ((name: String, price: Int) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)

            binding = FragmentDialogAddItemBinding.inflate(LayoutInflater.from(context))

            builder.setView(binding.root)
                    .setTitle(R.string.store_dialog_title)
                    .setMessage(R.string.store_dialog_message)
                    .setPositiveButton(R.string.common_submit) { dialog, _ ->
                        val name = binding.etItemTitle.text.toString()
                        val price = binding.etItemPrice.text.toString().toIntOrNull()

                        if (name.isEmpty() || price == null) return@setPositiveButton

                        onSubmitClick?.invoke(name, price)
                        dialog.dismiss()
                    }
                    .setNegativeButton(R.string.common_cancel) { dialog, _ ->
                        dialog.cancel()
                    }

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

}