package ru.lorderi.androktor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.androktor.R
import ru.lorderi.androktor.adapter.company.CompanyAdapter
import ru.lorderi.androktor.databinding.FragmentCompaniesBinding
import ru.lorderi.androktor.ui.fragment.CompanyDetailFragment.Companion.DETAILED_COMPANY_ID
import ru.lorderi.androktor.ui.itemdecoration.OffsetDecoration
import ru.lorderi.androktor.ui.viewmodel.job.JobViewModel
import ru.lorderi.androktor.util.getText

@AndroidEntryPoint
class CompaniesFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCompaniesBinding.inflate(inflater, container, false)

        val jobViewModel by activityViewModels<JobViewModel>()

        val adapter = CompanyAdapter {
            requireParentFragment()
                .requireParentFragment()
                .findNavController()
                .navigate(
                    R.id.action_bottomFragment_to_companyDetailFragment,
                    bundleOf(DETAILED_COMPANY_ID to it.id)
                )
        }

        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(16))

        binding.swipeRefresh.setOnRefreshListener {
            jobViewModel.loadCompanies()
        }

        binding.retryButton.setOnClickListener {
            jobViewModel.loadCompanies()
        }

        jobViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach { state ->

                binding.swipeRefresh.isRefreshing = state.isRefreshing(state.companies)

                val emptyError = state.emptyError(state.companies)

                binding.errorGroup.isVisible = emptyError != null

                binding.errorText.text = emptyError?.getText(requireContext())

                binding.progress.isVisible = state.isEmptyLoading(state.companies)

                state.refreshingError(state.companies)?.let {
                    Toast.makeText(
                        requireContext(),
                        it.getText(requireContext()),
                        Toast.LENGTH_SHORT
                    ).show()
                    jobViewModel.consumeError()
                }


                adapter.submitList(state.companies?.listOfCompanies)
            }
            .launchIn(lifecycleScope)

        jobViewModel.loadCompanies()

        return binding.root
    }

}