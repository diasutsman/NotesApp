package com.dias.notesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dias.notesapp.R
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.entity.Priority
import com.dias.notesapp.databinding.RowItemNotesBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var listNotes = arrayListOf<Notes>()

    class MyViewHolder(val binding: RowItemNotesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemNotesBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = listNotes[position]
        holder.binding.apply {
            tvTitle.text = data.title
            tvDate.text = data.date
            tvDescription.text = data.description
            val pink = ContextCompat.getColor(priorityIndicator.context,
                R.color.pink)
            when(data.priority) {
                Priority.HIGH -> priorityIndicator.setCardBackgroundColor(pink)
                Priority.MEDIUM -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(priorityIndicator.context,
                    R.color.yellow))
                Priority.LOW -> priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(priorityIndicator.context,
                    R.color.green))
            }
        }
    }

    fun setData(list: List<Notes>?) {
        if (list == null) return
        val diffCallback = DiffCallback(listNotes, list)
        val diffCallbackResult = DiffUtil.calculateDiff(diffCallback)
        listNotes.clear()
        listNotes.addAll(list)
        diffCallbackResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = listNotes.size

}