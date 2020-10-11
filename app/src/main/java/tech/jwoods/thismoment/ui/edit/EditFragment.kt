package tech.jwoods.thismoment.ui.edit

import android.app.Dialog
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
import kotlinx.android.synthetic.main.fragment_create_edit.*
import kotlinx.android.synthetic.main.fragment_create_edit.momentDescription
import kotlinx.android.synthetic.main.fragment_create_edit.momentTitle
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.ui.detail.DetailFragmentDirections

@AndroidEntryPoint
class EditFragment : Fragment() {
    private val viewModel: EditViewModel by viewModels()
    private val args: EditFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.observeMoment(args.momentId).observe(viewLifecycleOwner, Observer { moment ->
            momentDeleteButton.setOnClickListener { onDeleteClicked(moment) }

            momentTitle.setText(moment?.title)
            momentDescription.setText(moment?.description)

            momentTitle.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus && momentTitle.error == null) {
                    viewModel.save(moment.copy(title = momentTitle.text.toString()))
                }
            }

            momentDescription.setOnFocusChangeListener { v, hasFocus ->
                if (!hasFocus && momentDescription.error == null) {
                    viewModel.save(moment.copy(description = momentDescription.text.toString()))
                }
            }
        })
    }

    private fun onDeleteClicked(moment: Moment) {
        viewModel.delete(moment)
        val action = EditFragmentDirections.toHome()
        findNavController().navigate(action)
    }

}