package com.example.sampleapp.ui.settings

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import java.util.*


class DatePickerFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        const val TAG = "DatePickerFragment"

        fun newInstance(): DatePickerFragment {
            return DatePickerFragment()
        }
    }

    internal var onDateSet: ((timestamp: Long) -> Unit)? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        return DatePickerDialog(requireContext(), this, year, month, day)
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val timestamp = GregorianCalendar(year, month, dayOfMonth).timeInMillis
        onDateSet?.invoke(timestamp)
    }
}