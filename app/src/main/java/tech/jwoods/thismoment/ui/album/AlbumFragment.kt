package tech.jwoods.thismoment.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_album.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Album

@AndroidEntryPoint
class AlbumFragment : Fragment() {

    private val viewModel: AlbumViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
/*
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
*/

        val layoutManager = GridLayoutManager(requireContext(), 2)
        albumsRecycler.layoutManager = layoutManager

        val albumAdapter = AlbumAdapter(
            onClick = ::onAlbumClicked,
            onLongClick = ::onAlbumLongClicked,
            onTitleChanged = ::onAlbumTitleChanged
        )
        albumsRecycler.adapter = albumAdapter

        viewModel.observeAlbums().observe(viewLifecycleOwner, Observer { albums ->
            albumAdapter.submitList(albums)
        })

        albumsRecycler.addItemDecoration(
            ShelfItemDecoration(requireContext(), R.drawable.bg_bookcase_shelf)
        )

/*        viewModel.observeStarFilter().observe(viewLifecycleOwner, Observer { useStarFilter ->
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
        })*/

/*        searchButton.setOnClickListener { onSearchClicked() }*/
        createButton.setOnClickListener { onCreateClicked() }
/*        starButton.setOnClickListener { viewModel.toggleStarFilter() }*/
    }

    private fun onAlbumClicked(album: Album) {
        // open moments page
        val action = AlbumFragmentDirections.toMoment(album.id)
        findNavController().navigate(action)
    }

    private fun onAlbumLongClicked(album: Album): Boolean {
        Toast.makeText(context,
            "You have long clicked "+ album.title + "!",
            Toast.LENGTH_SHORT).show()

        return true
    }

    private fun onAlbumTitleChanged(album: Album) {
        viewModel.save(album)
    }

    private fun onSearchClicked() {
        //viewModel.toggleSearchBar()
    }

    private fun onCreateClicked() {
        viewModel.createNewAlbum {}
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
