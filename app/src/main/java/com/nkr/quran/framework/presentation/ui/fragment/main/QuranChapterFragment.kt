package com.nkr.quran.framework.presentation.ui.fragment.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.nkr.quran.R
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.databinding.QuranChapterFragmentBinding
import com.nkr.quran.framework.presentation.ui.adapter.QuranChapterListAdapter
import com.nkr.quran.framework.presentation.ui.viewModel.QuranChapterViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuranChapterFragment : Fragment(), LifecycleOwner {


    private lateinit var binding: QuranChapterFragmentBinding
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

        bannerCardAnimator()

        setUpListener()
    }

    private fun bannerCardAnimator() {

        //enter
        val enter = ObjectAnimator.ofFloat(binding.tvSurahName,View.TRANSLATION_X,-300f,0f)
        enter.duration = 1000
        enter.interpolator = AccelerateInterpolator(1f)

        //shaker
        val translator = ObjectAnimator.ofFloat(binding.hcLastRead,View.TRANSLATION_X,0f, 25f, -25f, 25f, -25f,15f, -15f, 6f, -6f, 0f)
        translator.duration = 1500
        translator.repeatCount = 1
        translator.interpolator = AccelerateInterpolator(1f)


        //fader
        val fader = ObjectAnimator.ofFloat(binding.ivQuran,View.ALPHA,0f)
        fader.duration = 2000
        fader.repeatCount = ObjectAnimator.INFINITE
        fader.repeatMode =ObjectAnimator.REVERSE


        val set = AnimatorSet()
        set.playSequentially(translator,fader)
        set.start()



    }

    private fun setUpListener() {
        binding.cvLastRead.setOnClickListener {

            val chapter_info = Chapter(
                1, 1, true,
                1, "Mecca", "Fatiha",
                "arabic_name", "Fatiha", 7
            )

            val action =
                QuranChapterFragmentDirections.actionQuranChapterFragmentToSurahDetailFragment(
                    chapter_info
                )

            findNavController().navigate(action)


        }

    }


    private fun subscribeToUi(adapter: QuranChapterListAdapter) {
        viewModel.chapterList.observe(
            this, Observer {
                adapter.submitList(it)
            })
    }

}