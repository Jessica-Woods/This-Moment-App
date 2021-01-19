package tech.jwoods.thismoment.ui.moment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_moment.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment

@AndroidEntryPoint
class MomentFragment : Fragment() {
    private val viewModel: MomentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_moment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchMoments(newText.toString())
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                viewModel.searchMoments(query.toString())
                return false
            }
        })

        momentsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val momentAdapter = MomentAdapter(onClick = ::onMomentClicked)
        momentsRecycler.adapter = momentAdapter

        viewModel.observeMoments().observe(viewLifecycleOwner, Observer { moments ->
            momentAdapter.submitList(moments)
        })

        viewModel.observeStarFilter().observe(viewLifecycleOwner, Observer { useStarFilter ->
            if(useStarFilter) {
                starButton.setBackgroundResource(R.drawable.ic_star_selected)
            } else {
                starButton.setBackgroundResource(R.drawable.ic_star)
            }
        })

        viewModel.observeShowSearchBar().observe(viewLifecycleOwner, Observer { showSearchBar ->
            if (showSearchBar) {
                showSearchBar()
            } else {
                hideSearchBar()
            }
        })

        searchButton.setOnClickListener { onSearchClicked() }
        createButton.setOnClickListener { onCreateClicked() }
        starButton.setOnClickListener { viewModel.toggleStarFilter() }
    }

    private fun onMomentClicked(moment: Moment) {
        val action = MomentFragmentDirections.toDetail(moment.id)
        findNavController().navigate(action)
    }

    private fun onSearchClicked() {
        viewModel.toggleSearchBar()
    }

    private fun onCreateClicked() {
        viewModel.createNewMoment { moment ->
            val action = MomentFragmentDirections.toEdit(moment.id)
            findNavController().navigate(action)
        }
    }

    private fun showSearchBar() {
        searchButton.setBackgroundResource(R.drawable.ic_search_selected)
        searchBar.visibility = View.VISIBLE
    }

    private fun hideSearchBar() {
        searchButton.setBackgroundResource(R.drawable.ic_search)
        searchBar.visibility = View.GONE
        searchBar.setQuery("", false)
    }
}