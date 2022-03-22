package com.dias.notesapp.utils

import android.content.Context
import android.view.View
import android.widget.AdapterView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.dias.notesapp.R

object HelperFunctions {
    fun spinnerListener(
        context: Context?,
        priorityIndicator: CardView,
    ): AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            context?.let {
                when (parent?.selectedItemPosition) {
                    0 -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(it,
                        R.color.pink))
                    1 -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(it,
                        R.color.yellow))
                    2 -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(it,
                        R.color.green))
                }
            }
        }

        override fun onNothingSelected(p0: AdapterView<*>?) {
            TODO("Not yet implemented")
        }

    }
}