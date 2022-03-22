package com.dias.notesapp.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dias.notesapp.R
import com.dias.notesapp.databinding.FragmentDetailBinding
import com.dias.notesapp.utils.ExtensionFunctions.setActionBar

class DetailFragment : Fragment() {
    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding as FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentDetailBinding.inflate(layoutInflater)
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
            R.id.action_edit -> findNavController().navigate(R.id.action_detailFragment_to_updateFragment)
            R.id.action_delete -> confirmDeleteNote()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteNote() {
        AlertDialog.Builder(context as Context)
            .setTitle("Delete Note?")
            .setMessage("Are you sure want to remove this note?")
            .setPositiveButton("Yes") { _, _ ->
                findNavController().navigate(R.id.action_detailFragment_to_homeFragment)
                Toast.makeText(context, "Note has been deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { _, _ -> }
            .setNeutralButton("Cancel") { _, _ -> }
            .show()
    }
}