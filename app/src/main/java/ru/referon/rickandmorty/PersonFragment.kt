package ru.referon.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import ru.referon.rickandmorty.MainFragment.Companion.idArg
import ru.referon.rickandmorty.databinding.FragmentPersonBinding
import ru.referon.rickandmorty.viewmodel.ViewModel

class PersonFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentPersonBinding.inflate(inflater, container, false)
        val viewModel: ViewModel by viewModels(ownerProducer = ::requireParentFragment)
        val id = arguments?.idArg ?: 1

        viewModel.loadPersonInfo(id)
        viewModel.data.observe(viewLifecycleOwner) { info ->
            if (info.personInfo == null && info.error == true) {
                binding.errorGroup.isVisible = true

            } else {
                binding.errorGroup.isVisible = false
                binding.name.text = info.personInfo?.name
                binding.species.text = info.personInfo?.species
                binding.gender.text = info.personInfo?.gender
                binding.status.text = info.personInfo?.status
                binding.location.text = info.personInfo?.location?.name
                binding.episodes.text = info.personInfo?.episode?.size.toString()
                Glide.with(this)
                    .load(info.personInfo?.image)
                    .placeholder(R.drawable.ic_loading)
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(20)))
                    .apply(RequestOptions().override(1000, 1000))
                    .into(binding.image)
            }

        }
        binding.buttonError.setOnClickListener {
            viewModel.loadPersonInfo(id)
        }
        return binding.root
    }
}