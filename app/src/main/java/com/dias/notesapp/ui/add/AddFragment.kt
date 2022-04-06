package com.dias.notesapp.ui.add

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dias.notesapp.R
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.data.entity.Priority
import com.dias.notesapp.databinding.FragmentAddBinding
import com.dias.notesapp.ui.NotesViewModel
import com.dias.notesapp.utils.ExtensionFunctions.setActionBar
import com.dias.notesapp.utils.HelperFunctions
import com.dias.notesapp.utils.HelperFunctions.parseToPriority
import java.text.SimpleDateFormat
import java.util.*

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding as FragmentAddBinding

    private val addViewModel by viewModels<NotesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbarAdd.setActionBar(this)

        binding.spinnerPriorities.onItemSelectedListener =
            HelperFunctions.spinnerListener(context, binding.priorityIndicator)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.action_save) as MenuItem
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
            insertNote()
        }
    }

    private fun insertNote() {
        binding.apply {
            val title = edtTitle.text.toString()
            val priority = spinnerPriorities.selectedItem.toString()
            val desc = edtDescription.text.toString()

            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar)
            val note = Notes(
                0,
                title,
                parseToPriority(priority),
                desc,
                date
            )
            if (title.isEmpty() || desc.isEmpty()) {
                edtDescription.error = "Please fill this field"
                edtTitle.error = "Please fill this field"
                Toast.makeText(context, "Please fill out the fields", Toast.LENGTH_SHORT).show()
            }else {
                addViewModel.insertData(note)
                findNavController().navigate(R.id.action_addFragment_to_homeFragment)
                Toast.makeText(context, "Successfully add note", Toast.LENGTH_SHORT).show()
                Log.i("AddFragment", "inserted notes: $note")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}