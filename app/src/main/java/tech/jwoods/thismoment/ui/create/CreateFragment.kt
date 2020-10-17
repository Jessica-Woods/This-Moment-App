package tech.jwoods.thismoment.ui.create

import android.app.Activity.RESULT_OK
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
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_edit.*
import tech.jwoods.thismoment.R


@AndroidEntryPoint
class CreateFragment : Fragment() {
    private val viewModel: CreateViewModel by viewModels()

    private var photoURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_create_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.save()

        momentDeleteButton.setOnClickListener { onDeleteClicked() }

        momentImageView.animatePolaroidFadeIn()
        momentImageView.setOnMomentPhotoClickListener { onImageClicked() }

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

    private fun onDeleteClicked() {
        viewModel.delete()
        val action = CreateFragmentDirections.toHome()
        findNavController().navigate(action)
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

        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            momentImageView.setPhoto(photoURI)
            viewModel.updatePhoto(photoURI)
        }
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