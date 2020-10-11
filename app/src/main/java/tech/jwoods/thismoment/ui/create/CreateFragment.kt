package tech.jwoods.thismoment.ui.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_edit.*
import tech.jwoods.thismoment.R

@AndroidEntryPoint
class CreateFragment : Fragment() {
    private val viewModel: CreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.save()

        momentTitle.setText(viewModel.moment.title)
        momentDescription.setText(viewModel.moment.description)

        momentTitle.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && momentTitle.error == null) {
                viewModel.updateTitle(momentTitle.text.toString())
            }
        }

        momentDescription.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && momentDescription.error == null) {
                viewModel.updateDescription(momentDescription.text.toString())
            }
        }
    }
}