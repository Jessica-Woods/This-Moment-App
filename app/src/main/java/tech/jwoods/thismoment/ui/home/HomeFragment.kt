package tech.jwoods.thismoment.ui.home

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
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
        val action = HomeFragmentDirections.toDetail(moment.id)
        findNavController().navigate(action)
    }

    private fun onSearchClicked() {
        viewModel.toggleSearchBar()
    }

    private fun onCreateClicked() {
        val action = HomeFragmentDirections.toCreate()
        findNavController().navigate(action)
    }

    private fun showSearchBar() {
        searchButton.setBackgroundResource(R.drawable.ic_search_selected)

        TransitionManager.beginDelayedTransition(homeFragment, AutoTransition())
        searchBar.visibility = View.VISIBLE
    }

    private fun hideSearchBar() {
        searchButton.setBackgroundResource(R.drawable.ic_search)

        TransitionManager.beginDelayedTransition(homeFragment, AutoTransition())
        searchBar.visibility = View.GONE

        // We use a handler here to prevent `setQuery` from being included in the delayed
        // transaction. This avoids a UI bug where the RecyclerView double-animates the cards
        // when clearing the search text.
        //
        // This is a bit of a hack since I couldn't figure out how to run some code after the
        // `TransitionManager` has ended, so we assume the transition won't take more then 500ms
        // which seems to work in practice.
        Handler().postDelayed({
            searchBar.setQuery("", false)
        }, 500)
    }
}