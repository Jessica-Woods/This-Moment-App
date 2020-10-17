package tech.jwoods.thismoment.ui.edit

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_edit.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.extensions.DatePickerDialogExtensions


@AndroidEntryPoint
class EditFragment : Fragment() {
    private val viewModel: EditViewModel by viewModels()
    private val args: EditFragmentArgs by navArgs()

    private var photoURI: Uri? = null

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

            momentImageView.animatePolaroidFadeIn()
            momentImageView.setPhoto(moment.photo)
            momentImageView.setDate(moment.date)
            momentTitle.setText(moment.title)
            momentDescription.setText(moment.description)

            momentImageView.setOnMomentDateClickListener {
                DatePickerDialogExtensions.show(requireContext(), moment.date) { date ->
                    viewModel.save(moment.copy(date = date))
                }
            }

            momentImageView.setOnMomentPhotoClickListener { onImageClicked() }

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

    private fun onImageClicked() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/ThisMoment/")
        }
        val resolver = requireContext().contentResolver
        photoURI = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

        startActivityForResult(takePictureIntent, EditFragment.REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == EditFragment.REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            viewModel.updatePhoto(args.momentId, photoURI)
        }
    }

    private fun onDeleteClicked(moment: Moment) {
        viewModel.delete(moment)
        val action = EditFragmentDirections.toHome()
        findNavController().navigate(action)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelable("photoURI", photoURI)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        photoURI = savedInstanceState?.getParcelable("photoURI")
    }

    companion object {
        const val REQUEST_IMAGE_CAPTURE = 1
    }
}