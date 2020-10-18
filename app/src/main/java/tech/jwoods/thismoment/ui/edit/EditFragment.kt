package tech.jwoods.thismoment.ui.edit

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.qifan.powerpermission.askPermissions
import com.qifan.powerpermission.data.hasAllGranted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_create_edit.*
import tech.jwoods.thismoment.R
import tech.jwoods.thismoment.data.Moment
import tech.jwoods.thismoment.ui.shared.Dialog
import java.io.File


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
                Dialog.showZonedDatePickerDialog(requireContext(), moment.date) { date ->
                    viewModel.save(moment.copy(date = date))
                }
            }

            momentImageView.setOnMomentPhotoClickListener { onImageClicked() }

            momentTitle.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && momentTitle.error == null) {
                    viewModel.save(moment.copy(title = momentTitle.text.toString()))
                }
            }

            momentDescription.setOnFocusChangeListener { _, hasFocus ->
                if (!hasFocus && momentDescription.error == null) {
                    viewModel.save(moment.copy(description = momentDescription.text.toString()))
                }
            }
        })
    }

    private fun onImageClicked() {
        Dialog.showOptionsDialog(
            requireContext(),
            "Set Moment Image",
            arrayOf("Take a Photo", "Pick from Gallery")
        ) { which ->
            when(which) {
                0 -> takePhoto()
                1 -> pickFromGallery()
            }
        }

    }

    private fun takePhoto() {
        askPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) { permission ->
            if (permission.hasAllGranted()) {
                val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

                val appName = requireContext().getString(R.string.app_name)

                photoURI = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val photoDir = Environment.DIRECTORY_PICTURES + File.separator + appName

                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                        put(MediaStore.MediaColumns.RELATIVE_PATH, "$photoDir${File.separator}")
                    }
                    val resolver = requireContext().contentResolver
                    resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
                } else {
                    val photoDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
                    val thisMomentPhotoDir = File(photoDir, appName)
                    thisMomentPhotoDir.mkdir()
                    val photoFile = File.createTempFile("Moment", ".jpg", thisMomentPhotoDir)
                    FileProvider.getUriForFile(
                        requireContext(),
                        "tech.jwoods.thismoment",
                        photoFile
                    )
                }

                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)

                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            } else {
                Log.d(TAG, "No permission granted")
            }
        }
    }

    private fun pickFromGallery() {
        val pickPhoto = Intent(Intent.ACTION_OPEN_DOCUMENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(pickPhoto, REQUEST_GALLERY_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            viewModel.updatePhoto(args.momentId, photoURI)
        } else if (requestCode == REQUEST_GALLERY_IMAGE && resultCode == Activity.RESULT_OK) {
            viewModel.updatePhoto(args.momentId, data?.data)
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
        const val TAG = "EditFragment"

        const val REQUEST_IMAGE_CAPTURE = 1
        const val REQUEST_GALLERY_IMAGE = 2
    }
}