package com.dias.notesapp.ui.home

import android.content.Context
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.dias.notesapp.R
import com.dias.notesapp.data.entity.Notes
import com.dias.notesapp.databinding.FragmentHomeBinding
import com.dias.notesapp.ui.NotesViewModel
import com.dias.notesapp.utils.ExtensionFunctions.setActionBar
import com.dias.notesapp.utils.HelperFunctions
import com.dias.notesapp.utils.HelperFunctions.checkIfDataEmpty
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment(), SearchView.OnQueryTextListener {

    // binding object
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding as FragmentHomeBinding

    private val homeViewModel: NotesViewModel by viewModels()

    private val homeAdapter by lazy { HomeAdapter() }

    private var _currentData: List<Notes>? = null
    private val currentData get() = _currentData as List<Notes>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            mHelperFunction = HelperFunctions
            toolbarHome.setActionBar(this@HomeFragment)
            // when fab add clicked, it navigate to addFragment
            fabAdd.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)
            }
        }

        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.rvNotes.apply {
            homeViewModel.getAllData().observe(viewLifecycleOwner) {
                checkIfDataEmpty(it)
                homeAdapter.setData(it)
                _currentData = it
            }
            adapter = homeAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            swipeToDelete(this)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_home, menu)

        val search = menu.findItem(R.id.menu_search).actionView as SearchView
        search.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_priority_high -> homeViewModel.sortByHighPriority()
                .observe(viewLifecycleOwner) {
                    homeAdapter.setData(it)
                }
            R.id.menu_priority_low -> homeViewModel.sortByLowPriority()
                .observe(viewLifecycleOwner) {
                    homeAdapter.setData(it)
                }
            R.id.menu_delete_all -> confirmDeleteAll()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmDeleteAll() {
        if (currentData.isNotEmpty())
            AlertDialog.Builder(context as Context)
                .setTitle("Delete All Notes?")
                .setMessage("Your action is irreversible")
                .setPositiveButton("Yes") { _, _ ->
                    homeViewModel.deleteAllData()
                    Toast.makeText(context, "All Note have been deleted", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("No") { _, _ -> }
                .show()
        else AlertDialog.Builder(context as Context)
            .setTitle("Notes Not Found")
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        // apit query dengan %query% dan masukkan ke variable querySearch
        val querySearch = "%$query%"

        // check query is not null then search by query
        query?.let {
            homeViewModel.searchByQuery(querySearch).observe(this) { searchedList ->
                homeAdapter.setData(searchedList)
            }
        }
        return true
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val note = homeAdapter.listNotes[position]

                homeViewModel.deleteNote(note)
                restoreDeletedData(viewHolder.itemView, note)
            }
        })
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeletedData(itemView: View, note: Notes) {
        Snackbar.make(itemView, "Deleted: ${note.title}", Snackbar.LENGTH_LONG)
            .setAction("Undo") {
                homeViewModel.insertData(note)
            }
            .setTextColor(ContextCompat.getColor(itemView.context, R.color.black))
            .show()
    }


    override fun onQueryTextChange(query: String?): Boolean {
        // apit query dengan %query% dan masukkan ke querySearch
        val querySearch = "%$query%"

        // check query is not null then search by query
        query?.let {
            homeViewModel.searchByQuery(querySearch).observe(this) { searchedList ->
                homeAdapter.setData(searchedList)
            }
        }
        return true
    }

    // prevent memory leak when fragment destroyed
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}