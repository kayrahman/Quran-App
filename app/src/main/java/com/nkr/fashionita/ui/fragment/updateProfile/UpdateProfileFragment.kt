package com.nkr.fashionita.ui.fragment.updateProfile

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.karumi.dexter.listener.single.PermissionListener
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.databinding.UpdateProfileFragmentBinding
import com.nkr.fashionita.ui.fragment.updateProfile.buildLogic.EditProfileListInjector
import com.nkr.fashionita.util.REQUEST_PICK_FROM_GALLERY
import com.nkr.fashionita.util.REQUEST_TAKE_FROM_CAMERA
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import id.zelory.compressor.Compressor
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException

class UpdateProfileFragment : BaseFragment() {

    companion object {
        fun newInstance() = UpdateProfileFragment()
    }

    private lateinit var viewModel: UpdateProfileViewModel
    private  lateinit var binding: UpdateProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.update_profile_fragment,container,false)

        return binding.root
        // return inflater.inflate(R.layout.update_profile_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel = ViewModelProvider(
            this,
            EditProfileListInjector(requireActivity().application).provideEditProfileViewModelFactory()
        ).get(
            UpdateProfileViewModel::class.java
        )

        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        observeViewModel()
        setupListener()

        viewModel.handleEvent(EditProfileEvent.populateUserData)


      //  viewModel = ViewModelProviders.of(this).get(UpdateProfileViewModel::class.java)


    }

    override fun setupListener() {

        binding.ivEditProfFmDp.setOnClickListener {
            choosePhotoFromGallery()
        }

        binding.ivBack.setOnClickListener {
            //go back to settings fragment

            updateProfilefragmentCallBack.goBack()

        }

        binding.ivEditProfFmDone.setOnClickListener {
            // update user info
            viewModel.handleEvent(EditProfileEvent.updateUserInfo)
        }

    }

    private fun observeViewModel() {
        viewModel.userImg.observe(
            this,
            Observer { imgUrl ->

                Log.d("img_url",imgUrl)

                Glide.with(this)
                    .load(imgUrl)
                    .apply(
                        RequestOptions()
                            .fitCenter()
                            .placeholder(R.drawable.avatar_female)
                    )
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))

                    .into(binding.ivEditProfFmDp)


            }
        )


        viewModel.showLoading.observe(
            this,
            Observer {
                if(it){
                    showLoadingDialog(getString(R.string.text_acc_info_update), null)
                }else{
                    dismissLoadingDialog()
                }
            }
        )



    }




    /**
     * pick image from gallery
     */
    private fun choosePhotoFromGallery() {
        Dexter.withActivity(requireActivity())
            .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
            .withListener(object : PermissionListener {
                override fun onPermissionGranted(response: PermissionGrantedResponse) {/* ... */
                    var galleryIntent = Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

                    startActivityForResult(galleryIntent, REQUEST_PICK_FROM_GALLERY)
                }

                override fun onPermissionDenied(response: PermissionDeniedResponse) {/* ... */
                    msgHelper.toastShort(getString(R.string.msg_missing_read_storage_permission))
                }

                override fun onPermissionRationaleShouldBeShown(permission: PermissionRequest, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }


    /**
     * pick image from gallery
     */
    private fun takePhotoFromCamera() {
        Dexter.withActivity(requireActivity())
            .withPermissions(
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)
            .withListener(object : MultiplePermissionsListener {
                override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if (report.areAllPermissionsGranted()) {
                        //open camera activity
                        var intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(intent, REQUEST_TAKE_FROM_CAMERA)
                    } else {
                        var deniedList = report.deniedPermissionResponses
                        when {
                            deniedList.size == 2 -> msgHelper.toastLong(getString(R.string.error_request_permission_both))
                            deniedList[0].permissionName == Manifest.permission.CAMERA -> msgHelper.toastLong(getString(R.string.error_request_permission_camera))
                            else -> msgHelper.toastLong(getString(R.string.error_request_permission_storage))
                        }
                    }
                }

                override fun onPermissionRationaleShouldBeShown(permissions: MutableList<PermissionRequest>?, token: PermissionToken) {
                    token.continuePermissionRequest()
                }
            }).check()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode === Activity.RESULT_CANCELED) {
            return
        }

        if (requestCode === REQUEST_PICK_FROM_GALLERY && resultCode == RESULT_OK) {

            if (data != null) {
                val contentURI = data.data


                CropImage.activity(contentURI!!)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .setMinCropWindowSize(500, 500)
                    .start(requireContext(),this)


            }

        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            val result = CropImage.getActivityResult(data)

            if (resultCode == RESULT_OK) {
                val resultUri = result.uri

                Log.d("result_uri","result is ok")
                //mDisplayImageBtn.setImageURI(resultUri)
             //   binding.ivEditProfFmDp.setImageURI(resultUri)

                viewModel.userImg.value = resultUri.toString()

                val thumb_filePath = File(resultUri.path)


                try {
                    val thumb_bitmap: Bitmap = Compressor(requireContext())
                        .setMaxHeight(200)
                        .setMaxWidth(200)
                        .compressToBitmap(thumb_filePath)


                    val baos = ByteArrayOutputStream()
                    thumb_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                 //   thumb_byte = baos.toByteArray()

                } catch (e: IOException) {
                    e.printStackTrace()
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.d("result_uri",error.toString())
            }

        }else{
            Log.d("result_uri","result not found")

        }

         if (requestCode === REQUEST_TAKE_FROM_CAMERA) {
            val thumbnail = data?.extras?.get("data") as Bitmap
            //store in local
          //  var path = saveImage(activity!!.applicationContext, thumbnail, false)
           // val file = File(path)
           // viewModel.fileAvatar.value = file
           // binding.ivAvatar.setImageBitmap(thumbnail)
        }
    }



    /**
     * listener related
     */
    lateinit var updateProfilefragmentCallBack: UpdateProfileFragmentCallBack

    interface UpdateProfileFragmentCallBack {
        fun goTo(fragment: Fragment)
        fun goBack()


    }



}
