package com.dias.notesapp.ui.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dias.notesapp.R
import com.dias.notesapp.databinding.FragmentUpdateBinding
import com.dias.notesapp.ui.NotesViewModel
import com.dias.notesapp.utils.ExtensionFunctions.setActionBar
import com.dias.notesapp.utils.HelperFunctions.parseToPriority
import com.dias.notesapp.utils.HelperFunctions.spinnerListener
import java.text.SimpleDateFormat
import java.util.*

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding as FragmentUpdateBinding
    private val args: UpdateFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(layoutInflater)
        return binding.root
    }

    private val updateViewModel: NotesViewModel by viewModels<NotesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            toolbarUpdate.setActionBar(this@UpdateFragment)
            safeArgs = args
            spinnerPrioritiesUpdate.onItemSelectedListener =
                spinnerListener(context, binding.priorityIndicator)
        }
    }

    private fun updateNote() {
        binding.apply {
            val title = edtTitleUpdate.text.toString()
            val priority = spinnerPrioritiesUpdate.selectedItem.toString()
            val desc = edtDescriptionUpdate.text.toString()

            val calendar = Calendar.getInstance().time
            val date = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(calendar)
            val updatedNote = args.currentItem.copy(
                title = title,
                priority = parseToPriority(priority),
                description = desc,
                date = getString(R.string.txt_edited_on, date)
            )
            if (title.isEmpty() || desc.isEmpty()) {
                if (title.isEmpty()) edtTitleUpdate.error = "Please fill this field"
                if (desc.isEmpty()) edtDescriptionUpdate.error = "Please fill this field"
                Toast.makeText(context, "Please fill out the fields", Toast.LENGTH_SHORT).show()
            } else {
                val isNotSame =
                    updatedNote.title != args.currentItem.title || updatedNote.priority != args.currentItem.priority || updatedNote.description != args.currentItem.description
                if (isNotSame) {
                    updateViewModel.updateNote(updatedNote)
                    Toast.makeText(context, "Note has been updated", Toast.LENGTH_SHORT).show()
                }
                findNavController().navigate(
                    UpdateFragmentDirections.actionUpdateFragmentToDetailFragment(if (isNotSame) updatedNote else args.currentItem
                    ))
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_save, menu)
        val action = menu.findItem(R.id.action_save)
        action.actionView.findViewById<AppCompatImageButton>(R.id.btn_save).setOnClickListener {
//            findNavController().navigate(R.id.action_updateFragment_to_detailFragment)
            updateNote()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
