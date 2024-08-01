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
import ru.lorderi.androktor.databinding.FragmentVacancyDetailBinding
import ru.lorderi.androktor.ui.fragment.CompanyDetailFragment.Companion.DETAILED_COMPANY_ID
import ru.lorderi.androktor.ui.viewmodel.job.JobUiState
import ru.lorderi.androktor.ui.viewmodel.job.JobViewModel
import java.util.Locale

@AndroidEntryPoint
class VacancyDetailFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentVacancyDetailBinding.inflate(inflater, container, false)

        val jobViewModel by activityViewModels<JobViewModel>()

        val id = arguments?.getLong(DETAILED_COMPANY_ID)

        if (id != null) {
            jobViewModel.loadVacancyById(id)
        } else {
            Toast.makeText(requireContext(), "ID not found", Toast.LENGTH_SHORT).show()
        }

        jobViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                bindDetailedVacancy(it, binding)
                binding.favorite.setOnClickListener {
                    jobViewModel.changeFavorite()
                }
            }
            .launchIn(lifecycleScope)

        return binding.root
    }

    private fun bindDetailedVacancy(
        state: JobUiState,
        binding: FragmentVacancyDetailBinding
    ) {
        val vacancy = state.detailedVacancy
        binding.avatarInitials.text = vacancy?.company?.take(1)
        binding.company.text = vacancy?.company
        binding.salary.text = vacancy?.salary.toString()
        binding.level.text = vacancy?.level
        vacancy?.profession?.uppercase(Locale.ROOT).also { binding.profession.text = it }
        binding.avatar.setOnClickListener {
            navigateToCompany(state)
        }
        binding.company.setOnClickListener {
            navigateToCompany(state)
        }
        vacancy?.let {
            val favorite =
                vacancy.isFavourite == null || vacancy.isFavourite
            if (favorite) {
                binding.favorite.setImageResource(R.drawable.baseline_bookmark_24)
            } else {
                binding.favorite.setImageResource(R.drawable.baseline_bookmark_border_24)
            }
        }
    }

    private fun navigateToCompany(state: JobUiState) {
        requireParentFragment()
            .requireParentFragment()
        findNavController()
            .navigate(
                R.id.action_vacancyDetailFragment_to_companyDetailFragment,
                bundleOf(DETAILED_COMPANY_ID to state.detailedVacancy?.companyId)
            )
    }
}