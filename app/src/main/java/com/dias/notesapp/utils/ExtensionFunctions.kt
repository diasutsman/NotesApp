package com.dias.notesapp.utils

import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.dias.notesapp.ui.MainActivity
import com.dias.notesapp.R
import com.google.android.material.appbar.MaterialToolbar

object ExtensionFunctions {
    fun MaterialToolbar.setActionBar(fragment: Fragment) {
        // tell the fragment that it has options menu
        fragment.setHasOptionsMenu(true)

        // setup toolbar with nav controller
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)

        // set MainActivity's supportActionBar with toolBarHome from this fragment
        (fragment.requireActivity() as MainActivity).setSupportActionBar(this)
        setupWithNavController(navController, appBarConfiguration)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.addFragment,
                R.id.detailFragment,
                R.id.updateFragment,
                -> setNavigationIcon(R.drawable.ic_left_arrow)
            }
        }
    }
}