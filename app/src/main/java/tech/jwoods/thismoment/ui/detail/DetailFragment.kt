package tech.jwoods.thismoment.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_detail.momentDescription
import kotlinx.android.synthetic.main.fragment_detail.momentTitle
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.ui.edit.DetailViewModel

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        momentImageView.animatePolaroidFadeIn()

        viewModel.observeMoment(args.momentId).observe(viewLifecycleOwner, Observer { moment ->
            momentImageView.setPhoto(moment.photo)
            momentTitle.text = moment.title
            momentDescription.text = moment.description
            starButton.isChecked = moment.starred

            momentEditButton.setOnClickListener { onEditClicked() }

            starButton.setOnCheckedChangeListener { _, isChecked ->
                onStarClicked(moment, isChecked)
            }
        })
    }

    private fun onEditClicked() {
        val action = DetailFragmentDirections.toEdit(args.momentId)
        findNavController().navigate(action)
    }

    private fun onStarClicked(moment: Moment, isStarred: Boolean) {
        viewModel.save(moment.copy(starred = isStarred))
    }
}