package com.dias.notesapp.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.dias.notesapp.R
import com.dias.notesapp.databinding.FragmentDetailBinding
import com.dias.notesapp.ui.NotesViewModel
import com.dias.notesapp.utils.ExtensionFunctions.setActionBar

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding

    private val navArgs by navArgs<DetailFragmentArgs>()

    // detailViewModel with NotesViewModel
    private val detailViewModel: NotesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
        binding.apply {
            safeArgs = navArgs
            mNotes = navArgs.currentItem
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbarDetail.setActionBar(this)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_detail, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> DetailFragmentDirections.actionDetailFragmentToUpdateFragment(
                navArgs.currentItem
            ).let {
                findNavController().navigate(it)
            }
            R.id.action_delete -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteNote() {
        AlertDialog.Builder(context as Context)
            .setTitle("Delete \"${navArgs.currentItem.title}\"?")
            .setMessage("Are you sure want to remove this note?")
            .setPositiveButton("Yes") { _, _ ->
                detailViewModel.deleteNote(navArgs.currentItem)
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                Toast.makeText(context,
                    "Successfully Delete: \"${navArgs.currentItem.title}\"",
                    Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .create()
            .show()
    }

    // onDestroyView
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}