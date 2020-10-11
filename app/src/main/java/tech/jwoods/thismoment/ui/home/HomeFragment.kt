package tech.jwoods.thismoment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
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

        momentsRecycler.layoutManager = GridLayoutManager(requireContext(), 2)

        val momentAdapter = MomentAdapter(onClick = ::onMomentClicked)
        momentsRecycler.adapter = momentAdapter

        viewModel.observeMoments().observe(viewLifecycleOwner, Observer { moments ->
            momentAdapter.submitList(moments)
        })

        createButton.setOnClickListener { onCreateClicked() }
    }

    private fun onMomentClicked(moment: Moment) {
        val action = HomeFragmentDirections.toDetail(moment.id)
        findNavController().navigate(action)
    }

    private fun onCreateClicked() {
        val action = HomeFragmentDirections.toCreate()
        findNavController().navigate(action)
    }
}