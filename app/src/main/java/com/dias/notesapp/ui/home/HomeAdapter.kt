package com.dias.notesapp.ui.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dias.notesapp.R
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.entity.Priority
import com.dias.notesapp.databinding.RowItemNotesBinding

class HomeAdapter : RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    private var _listNotes = arrayListOf<Notes>()
    val listNotes: List<Notes>
        get() = _listNotes

    class MyViewHolder(val binding: RowItemNotesBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemNotesBinding.inflate(LayoutInflater.from(parent.context))
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val data = _listNotes[position]
        holder.binding.root.apply {
            Log.i("HomeAdapter", "Margin top: $marginTop")
            Log.i("HomeAdapter", "Margin end: $marginEnd")
            Log.i("HomeAdapter", "Margin bottom: $marginBottom")
            Log.i("HomeAdapter", "Margin start: $marginStart")
        }
        holder.binding.apply {
            mNotes = data
        }
    }

    fun setData(list: List<Notes>?) {
        if (list == null) return
        val diffCallback = DiffCallback(_listNotes, list)
        val diffCallbackResult = DiffUtil.calculateDiff(diffCallback)
        _listNotes.clear()
        _listNotes.addAll(list)
        diffCallbackResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = _listNotes.size

}