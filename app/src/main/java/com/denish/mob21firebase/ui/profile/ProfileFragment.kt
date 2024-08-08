package com.denish.mob21firebase.ui.profile

import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.denish.mob21firebase.R
import com.denish.mob21firebase.core.service.StorageService
import com.denish.mob21firebase.databinding.FragmentProfileBinding
import com.denish.mob21firebase.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    override val viewModel: ProfileViewModel by viewModels()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<String>
    private var imageName: String? = null

    @Inject
    lateinit var storageService: StorageService

    override fun getLayoutResource() = R.layout.fragment_profile

    override fun onBindView(view: View) {
        super.onBindView(view)
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) {
            it?.let { uri ->
                binding?.ivProfile?.setImageURI(uri)
                storageService.uploadImage(uri = it, imageName) { name ->
                    if (name != null) {
                        viewModel.updateProfile(name)
                        imageName = name
                    }
                }
            }
        }

        binding?.ivProfileEdit?.setOnClickListener{
            imagePickerLauncher.launch("image/*")
        }
    }

    override fun onBindData(view: View) {
        super.onBindData(view)
        viewModel.user.observe(viewLifecycleOwner) {
            imageName = it.profilePic
            binding?.run {
                tvName.text = it.firstName
                tvEmail.text = it.email
                showProfile(ivProfile, imageName)
            }
        }
    }

    private fun showProfile(imageView: ImageView, name: String?) {
        if (name.isNullOrEmpty()) return
        storageService.getImageUri(name) {
            Log.d("debugging", it.toString())
            Glide.with(this)
                .load(it)
                .placeholder(R.drawable.ic_profile)
                .centerCrop()
                .into(imageView)
        }
    }

}

//Profile picture, icon button to change the profile picture
//name, email