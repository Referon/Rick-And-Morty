package ru.referon.rickandmorty

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.referon.rickandmorty.adapter.Adapter
import ru.referon.rickandmorty.adapter.OnInteractionListener
import ru.referon.rickandmorty.databinding.FragmentMainBinding
import ru.referon.rickandmorty.model.Result
import ru.referon.rickandmorty.viewmodel.ViewModel

class MainFragment : Fragment() {
    var page = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentMainBinding.inflate(layoutInflater)

        val viewModel: ViewModel by viewModels(ownerProducer = ::requireParentFragment)

        binding.countPages.text = page.toString()
        val adapter = Adapter(object : OnInteractionListener{
            override fun onPerson(info: Result) {
                findNavController().navigate(
                        R.id.action_mainFragment_to_personFragment,
                        Bundle().apply {
                            idArg = info.id
                        }
                    )
            }
        })
        binding.list.adapter = adapter

        viewModel.loadInfo(page)
        viewModel.data.observe(viewLifecycleOwner){
            binding.progress.isVisible = it.loading
            adapter.submitList(it.info?.results)
        }

        binding.pageBack.setOnClickListener {
            if (page < 2){
                return@setOnClickListener
            } else {
                viewModel.loadInfo(page - 1)
                page -= 1
                binding.countPages.text = page.toString()
            }
        }
        binding.pageNext.setOnClickListener {
            if (page == 42){
                return@setOnClickListener
            } else {
                viewModel.loadInfo(page + 1)
                page += 1
                binding.countPages.text = page.toString()
            }
        }
        return binding.root
    }
    companion object {
        var Bundle.idArg: Int by IntArg
    }
}