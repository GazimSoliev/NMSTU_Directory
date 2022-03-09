package com.gazim.directory.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.gazim.directory.R
import com.gazim.directory.databinding.FragmentGalleryBinding

class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        glidResize(R.drawable.college, binding.imageViewCollege)
        glidResize(R.drawable.college0, binding.imageViewCollege0)
        glidResize(R.drawable.college1, binding.imageViewCollege1)
        glidResize(R.drawable.college2, binding.imageViewCollege2)
        glidResize(R.drawable.college3, binding.imageViewCollege3)
        glidResize(R.drawable.college4, binding.imageViewCollege4)
        glidResize(R.drawable.college5, binding.imageViewCollege5)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun glidResize(file: Int, imageView: ImageView) {
        Glide.with(this).load(file).override(binding.cardView.width).into(imageView)
    }
}