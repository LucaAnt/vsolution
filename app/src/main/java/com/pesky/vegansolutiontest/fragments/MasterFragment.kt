package com.pesky.vegansolutiontest.fragments

import SwipeToDeleteCallback
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.pesky.vegansolutiontest.R
import com.pesky.vegansolutiontest.adapters.DocumentListAdapter
import com.pesky.vegansolutiontest.adapters.IMonclerDocumentsListener
import com.pesky.vegansolutiontest.viewmodels.SharedViewModel
import kotlinx.android.synthetic.main.fragment_master.*


class MasterFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()

    private val adapter = DocumentListAdapter()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedViewModel.fetchDocuments()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_master, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        sharedViewModel.documents.observe(viewLifecycleOwner, Observer {
            adapter.documentList = it
            adapter.notifyDataSetChanged()
            fragment_master_pull_to_refresh.setRefreshing(false);
        })

        sharedViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it)
            {
                sharedViewModel.clearError()
                val sb = Snackbar.make(requireView(),"Something went wrong",Snackbar.LENGTH_LONG)
                sb.view.setBackgroundColor(getResources().getColor(R.color.red_error))
                sb.show()
                fragment_master_pull_to_refresh.setRefreshing(false);
            }
        })

    }

    private fun initRecycler()
    {
        fragment_master_recycler.adapter = adapter
        // Item Click Handler
        adapter.listener = object : IMonclerDocumentsListener {
            override fun onItemClick(position: Int) {
                Snackbar.make(requireView(),"Item clicked $position",Snackbar.LENGTH_LONG).show()
                sharedViewModel.selectDocumentAtPosition(position)
                findNavController().navigate(MasterFragmentDirections.toDetail())
            }
        }

        //Swipe Handler
        val swipeHandler = object : SwipeToDeleteCallback(requireContext()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.d("item swiped at:", "${viewHolder.adapterPosition} ")
                //sharedViewModel.documents.value?.removeAt(viewHolder.adapterPosition)
                //adapter.notifyItemRemoved(viewHolder.adapterPosition)
                sharedViewModel.deleteDocumentAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(fragment_master_recycler)

        //Pull to refresh handler
        fragment_master_pull_to_refresh.setOnRefreshListener { sharedViewModel.fetchDocuments()}

        //Layout
        fragment_master_recycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        val dividerItemDecoration = DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL)
        fragment_master_recycler.addItemDecoration(dividerItemDecoration)


    }

}