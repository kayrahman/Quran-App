package com.nkr.quran.framework.presentation.ui.fragment.main

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.nkr.quran.databinding.QuranChapterFragmentBinding
import com.nkr.quran.framework.presentation.ui.adapter.QuranChapterListAdapter
import com.nkr.quran.framework.presentation.ui.viewModel.QuranChapterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuranChapterFragment : Fragment(), LifecycleOwner {


    private lateinit var binding:QuranChapterFragmentBinding
    private val viewModel: QuranChapterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = QuranChapterFragmentBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = QuranChapterListAdapter()
        binding.chapterList.adapter = adapter
        subscribeToUi(adapter)
        setHasOptionsMenu(true)

        viewModel.getQuranChapters()
    }

    private fun subscribeToUi(adapter: QuranChapterListAdapter) {
            viewModel.chapterList.observe(
                this, Observer {
                    adapter.submitList(it)
                })
    }

}