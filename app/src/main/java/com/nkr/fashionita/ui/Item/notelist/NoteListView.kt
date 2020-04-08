package com.nkr.fashionita.ui.Item.notelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.nkr.fashionita.R
import com.nkr.fashionita.base.BaseFragment
import com.nkr.fashionita.common.makeToast
import com.nkr.fashionita.glide.GlideApp
import com.nkr.fashionita.model.BannerItem
import com.nkr.fashionita.ui.Item.NoteListViewModel
import com.nkr.fashionita.ui.Item.notedetail.NoteDetailView
import com.wiseassblog.jetpacknotesmvvmkotlin.note.notelist.buildlogic.NoteListInjector
import kotlinx.android.synthetic.main.fragment_note_list.*
import technolifestyle.com.imageslider.FlipperView

class NoteListView : BaseFragment() {

    override fun setupListener() {


    }

    private lateinit var viewModel: NoteListViewModel
    private lateinit var adapter: NoteListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //THIS IS IMPORTANT!!!
        rec_list_fragment.adapter = null
        flipper_layout.removeAllViews()
    }

    override fun onStart() {
        super.onStart()
        viewModel = ViewModelProvider(
            this,
            NoteListInjector(requireActivity().application).provideNoteListViewModelFactory()
        ).get(
            NoteListViewModel::class.java
        )

        // (imv_space_background.drawable as AnimationDrawable).startWithFade()

        //       showLoadingState()

        setUpAdapter()
        observeViewModel()

        fab_create_new_item.setOnClickListener {

            val direction = NoteListViewDirections.actionNoteListViewToNoteDetailView("")

            findNavController().navigate(direction)
        }


        viewModel.handleEvent(
            NoteListEvent.OnStart

        )

        viewModel.handleEvent(
            NoteListEvent.OnBannerItemStart
        )

        showBannerItems(viewModel.listOfBannerItem)

    }


    private fun setUpAdapter() {
        adapter = NoteListAdapter()
        adapter.event.observe(
            viewLifecycleOwner,
            Observer {
                viewModel.handleEvent(it)
            }
        )

        rec_list_fragment.layoutManager = GridLayoutManager(context, 2)
        rec_list_fragment.adapter = adapter

    }

    private fun observeViewModel() {


        viewModel.error.observe(
            viewLifecycleOwner,
            Observer { errorMessage ->
                showErrorState(errorMessage)
            }
        )

        viewModel.noteList.observe(
            viewLifecycleOwner,
            Observer { noteList ->
                adapter.submitList(noteList)

                if (noteList.isNotEmpty()) {
                    //   (imv_satellite_animation.drawable as AnimationDrawable).stop()
                    //  imv_satellite_animation.visibility = View.INVISIBLE
                    rec_list_fragment.visibility = View.VISIBLE
                }
            }
        )

        viewModel.editNote.observe(
            viewLifecycleOwner,
            Observer { noteId ->
              //  startNoteDetailWithArgs(noteId)

                startItemDetailFragment()

                Log.d("note_id_fm",noteId)

                msgHelper.toastShort("We are here")


            }
        )
    }


    private fun showBannerItems(it:List<BannerItem>) {

            for (item in it) {

                val view = FlipperView(context!!)

                view.setImageUrl(item.image_url) { imageView, image ->

                    GlideApp.with(requireActivity())
                        .load(image)
                        .into(imageView)

                }



                view.setImageScaleType(ImageView.ScaleType.CENTER_INSIDE)
                flipper_layout.addFlipperView(view)

        }


    }

    private fun startNoteDetailWithArgs(noteId: String) = findNavController().navigate(
        NoteListViewDirections.actionNoteListViewToNoteDetailView(noteId)



    )

    fun startItemDetailFragment(){
        val itemDetailFragment = NoteDetailView.newInstance()
        fragmentHelper.loadFragment(childFragmentManager,itemDetailFragment,"",R.id.fragment_nav)

    }


    private fun showErrorState(errorMessage: String?) = makeToast(errorMessage!!)


//    private fun showLoadingState() = (imv_satellite_animation.drawable as AnimationDrawable).start()

}