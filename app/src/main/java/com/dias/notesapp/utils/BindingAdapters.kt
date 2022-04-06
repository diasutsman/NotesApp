package com.dias.notesapp.utils

import android.view.View
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dias.notesapp.R
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.entity.Priority
import com.dias.notesapp.ui.home.HomeFragmentDirections
import com.google.android.material.card.MaterialCardView

object BindingAdapters {
    // add custom attributes to the material card view
    @BindingAdapter("android:parsePriorityColor")
    @JvmStatic
    fun parsePriorityColor(priorityIndicator: MaterialCardView, priority: Priority) {
        priorityIndicator.setCardBackgroundColor(priorityIndicator.context.getColor(
            when (priority) {
                Priority.HIGH -> R.color.pink
                Priority.MEDIUM -> R.color.yellow
                Priority.LOW -> R.color.green
            }
        ))
    }

    @BindingAdapter("android:sendDataToDetail")
    @JvmStatic
    fun sendDataToDetail(view: ConstraintLayout, currentItem: Notes) {
        view.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment(currentItem)
            view.findNavController().navigate(action)
        }
    }

    @BindingAdapter("android:priorityToColor")
    @JvmStatic
    fun priorityToColor(priorityIndicator: CardView, priority: Priority) {
        priorityIndicator.setCardBackgroundColor(priorityIndicator.context.getColor(
            when (priority) {
                Priority.HIGH -> R.color.pink
                Priority.MEDIUM -> R.color.yellow
                Priority.LOW -> R.color.green
            }
        ))
    }

    @BindingAdapter("android:isEmpty")
    @JvmStatic
    fun isEmpty(view: View, emptyDatabase: MutableLiveData<Boolean>) {
        emptyDatabase.observe(view.context as FragmentActivity) {
            view.visibility = if (it) View.VISIBLE else View.GONE
        }
    }
}