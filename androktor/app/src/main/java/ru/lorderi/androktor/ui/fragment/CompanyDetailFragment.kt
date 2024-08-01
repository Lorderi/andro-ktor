package ru.lorderi.androktor.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.androktor.R
import ru.lorderi.androktor.adapter.advancedvacancy.AdvancedVacancyAdapter
import ru.lorderi.androktor.databinding.FragmentCompanyDetailBinding
import ru.lorderi.androktor.ui.itemdecoration.OffsetDecoration
import ru.lorderi.androktor.ui.viewmodel.job.JobUiState
import ru.lorderi.androktor.ui.viewmodel.job.JobViewModel
import ru.lorderi.androktor.util.handlePhone

@AndroidEntryPoint
class CompanyDetailFragment : Fragment() {
    companion object {
        const val DETAILED_COMPANY_ID = "DETAILED_COMPANY_ID"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentCompanyDetailBinding.inflate(inflater, container, false)


        val jobViewModel by activityViewModels<JobViewModel>()

        val id = arguments?.getLong(DETAILED_COMPANY_ID)

        if (id != null) {
            jobViewModel.loadCompanyById(id)
        } else {
            Toast.makeText(requireContext(), "ID not found", Toast.LENGTH_SHORT).show()
        }

        val adapter = AdvancedVacancyAdapter {
            requireParentFragment()
                .requireParentFragment()
            findNavController()
                .navigate(
                    R.id.action_companyDetailFragment_to_vacancyDetailFragment,
                    bundleOf(DETAILED_COMPANY_ID to it.id)
                )
        }

        binding.list.adapter = adapter

        binding.list.addItemDecoration(OffsetDecoration(16))

        jobViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                adapter.submitList(it.detailedCompany?.vacancies)

                bindDetailedCompany(it, binding)
            }
            .launchIn(lifecycleScope)


        return binding.root
    }

    private fun bindDetailedCompany(
        it: JobUiState,
        binding: FragmentCompanyDetailBinding
    ) {
        val clientCompany = it.detailedCompany
        binding.avatarInitials.text = clientCompany?.name?.take(1)
        binding.contacts.text = clientCompany?.contacts?.handlePhone()
        binding.fieldOfActivity.text = clientCompany?.activity?.value
        binding.name.text = clientCompany?.name
    }
}