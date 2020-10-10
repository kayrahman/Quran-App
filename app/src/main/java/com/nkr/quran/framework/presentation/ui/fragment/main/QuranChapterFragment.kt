package com.nkr.quran.framework.presentation.ui.fragment.main

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.AppBarLayout
import com.nkr.quran.R
import com.nkr.quran.business.domain.models.Chapter
import com.nkr.quran.databinding.QuranChapterFragmentBinding
import com.nkr.quran.framework.presentation.ui.adapter.QuranChapterListAdapter
import com.nkr.quran.framework.presentation.ui.viewModel.QuranChapterViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_surah_detail.view.*
import kotlinx.android.synthetic.main.layout_header_quran_chapter_fragment.*
import kotlinx.android.synthetic.main.layout_header_quran_chapter_fragment.view.*
import kotlinx.android.synthetic.main.quran_chapter_fragment.*

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
        binding.chapterList.chapterList.adapter = adapter
        subscribeToUi(adapter)
        setHasOptionsMenu(true)

        viewModel.getQuranChapters()

        coordinateMotion()


        bannerCardAnimator()

        setUpListener()
    }

    private fun bannerCardAnimator() {

        //enter
        val enter = ObjectAnimator.ofFloat(binding.layoutHeader.tvSurahName,View.TRANSLATION_X,-300f,0f)
        enter.duration = 1000
        enter.interpolator = AccelerateInterpolator(1f)

        //shaker
        val translator = ObjectAnimator.ofFloat(binding.layoutHeader.hcLastRead,View.TRANSLATION_X,0f, 25f, -25f, 25f, -25f,15f, -15f, 6f, -6f, 0f)
        translator.duration = 1500
        translator.repeatCount = 1
        translator.interpolator = AccelerateInterpolator(1f)


        //fader
        val fader = ObjectAnimator.ofFloat(binding.layoutHeader.ivQuran,View.ALPHA,0f)
        fader.duration = 2000
        fader.repeatCount = ObjectAnimator.INFINITE
        fader.repeatMode =ObjectAnimator.REVERSE


        val set = AnimatorSet()
        set.playSequentially(translator,fader)
        set.start()



    }

    private fun setUpListener() {
        binding.layoutHeader.cvLastRead.setOnClickListener {

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


    private fun coordinateMotion() {
       // val appBarLayout: AppBarLayout = findViewById(R.id.app_bar)
       // val motionLayout: MotionLayout = R.id.motion_header

        val listener = AppBarLayout.OnOffsetChangedListener { unused, verticalOffset ->
            val seekPosition = -verticalOffset / binding.appBar.totalScrollRange.toFloat()
          binding.layoutHeader.motionHeader.progress = seekPosition
        }

        binding.appBar.addOnOffsetChangedListener(listener)
    }

}