package ru.lorderi.androktor.ui.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import ru.lorderi.androktor.R
import ru.lorderi.androktor.databinding.FragmentMyResumeBinding
import ru.lorderi.androktor.model.CandidateInfo
import ru.lorderi.androktor.model.Contacts
import ru.lorderi.androktor.model.Education
import ru.lorderi.androktor.model.JobExperience
import ru.lorderi.androktor.model.Resume
import ru.lorderi.androktor.ui.viewmodel.ToolbarViewModel
import ru.lorderi.androktor.ui.viewmodel.myresume.MyResumeViewModel

@AndroidEntryPoint
class MyResumeFragment : Fragment() {
    companion object {
        const val HARD_CORDED_RESUME_ID = 1L
        const val RESUME_IS_EDITED = "RESUME_IS_EDITED"
    }

    private lateinit var binding: FragmentMyResumeBinding
    private val myResumeViewModel by viewModels<MyResumeViewModel>()
    private val toolbarViewModel by activityViewModels<ToolbarViewModel>()
    private lateinit var preferences: SharedPreferences

    override fun onSaveInstanceState(outState: Bundle) {
        val isEditing = toolbarViewModel.cancellation.value
        outState.putBoolean(RESUME_IS_EDITED, isEditing)
        if (isEditing) {
            val resume = getResumeFromForm(binding, HARD_CORDED_RESUME_ID)
            myResumeViewModel.saveLocalResume(resume)
        }
        super.onSaveInstanceState(outState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMyResumeBinding.inflate(inflater, container, false)
        preferences = requireActivity().getSharedPreferences("resume", Context.MODE_PRIVATE)
        toolbarViewModel.setNullCancellation()

        val isEditing = savedInstanceState?.getBoolean(RESUME_IS_EDITED) ?: false

        val longTermIsEditing = preferences.getBoolean(RESUME_IS_EDITED, false)

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            if (toolbarViewModel.cancellation.value) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(getString(R.string.save_changes))
                    .setMessage(getString(R.string.save_to_resume_label))
                    .setNegativeButton(getString(R.string.decline_label)) { _, _ ->
                        if (toolbarViewModel.cancellation.value) {
                            toolbarViewModel.changeCancellation()
                        }
                        preferences.edit {
                            putBoolean(RESUME_IS_EDITED, toolbarViewModel.cancellation.value)
                        }
                        findNavController()
                            .navigateUp()
                    }
                    .setPositiveButton(getString(R.string.accept_label)) { _, _ ->
                        val resume = getResumeFromForm(binding, HARD_CORDED_RESUME_ID)
                        myResumeViewModel.saveLocalResume(resume)
                        if (!toolbarViewModel.cancellation.value) {
                            toolbarViewModel.changeCancellation()
                        }
                        preferences.edit {
                            putBoolean(RESUME_IS_EDITED, toolbarViewModel.cancellation.value)
                        }
                        findNavController()
                            .navigateUp()
                    }
                    .show()
            } else {
                findNavController()
                    .navigateUp()
            }
        }

        if (isEditing or longTermIsEditing) {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.save_changes))
                .setMessage(getString(R.string.resume_label))
                .setNegativeButton(getString(R.string.decline_label)) { _, _ ->
                    myResumeViewModel.deleteResume(HARD_CORDED_RESUME_ID)
                    myResumeViewModel.loadResumeById(HARD_CORDED_RESUME_ID)
                    preferences.edit {
                        putBoolean(RESUME_IS_EDITED, toolbarViewModel.cancellation.value)
                    }
                }
                .setPositiveButton(getString(R.string.accept_label)) { _, _ ->
                    toolbarViewModel.changeCancellation()
                    myResumeViewModel.loadLocalResumeById(HARD_CORDED_RESUME_ID)
                    preferences.edit {
                        putBoolean(RESUME_IS_EDITED, toolbarViewModel.cancellation.value)
                    }
                }
                .show()
        }

        myResumeViewModel.loadResumeById(HARD_CORDED_RESUME_ID)

        myResumeViewModel.uiState
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                val resume = it.resume
                bindResume(binding, resume)
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        toolbarViewModel.cancellation
            .flowWithLifecycle(viewLifecycleOwner.lifecycle)
            .onEach {
                setEnabled(binding, it)
                if (it) {
                    binding.actionButton.setIconResource(R.drawable.baseline_add_24)
                } else {
                    binding.actionButton.setIconResource(R.drawable.baseline_create_24)
                }
            }
            .launchIn(viewLifecycleOwner.lifecycleScope)

        binding.actionButton.setOnClickListener {
            toolbarViewModel.changeCancellation()
            if (!toolbarViewModel.cancellation.value) {
                val resume = getResumeFromForm(binding, HARD_CORDED_RESUME_ID)
                myResumeViewModel.saveResume(HARD_CORDED_RESUME_ID, resume)
            }
            preferences.edit {
                putBoolean(RESUME_IS_EDITED, toolbarViewModel.cancellation.value)
            }
        }

        return binding.root
    }


    private fun setEnabled(
        binding: FragmentMyResumeBinding,
        value: Boolean
    ) {
        binding.name.isEnabled = value
        binding.profession.isEnabled = value
        binding.sex.isEnabled = value
        binding.birthDate.isEnabled = value
        binding.phone.isEnabled = value
        binding.email.isEnabled = value
        binding.relocation.isEnabled = value

        binding.educationType.isEnabled = value
        binding.educationYearStart.isEnabled = value
        binding.educationYearEnd.isEnabled = value
        binding.educationDescription.isEnabled = value

        binding.jobExperienceDateStart.isEnabled = value
        binding.jobExperienceDateEnd.isEnabled = value
        binding.JobExperienceCompanyName.isEnabled = value
        binding.JobExperienceDescription.isEnabled = value

        binding.freeForm.isEnabled = value
    }

    private fun getResumeFromForm(binding: FragmentMyResumeBinding, id: Long): Resume {
        return Resume(
            id = id,
            CandidateInfo(
                name = binding.name.getText().toString(),
                profession = binding.profession.getText().toString(),
                sex = binding.sex.getText().toString(),
                birthDate = binding.birthDate.getText().toString(),
                contact = Contacts(
                    phone = binding.phone.getText().toString(),
                    email = binding.email.getText().toString(),
                ),
                relocation = binding.relocation.getText().toString(),
            ),
            listOf(
                Education(
                    id = id,
                    resumeId = id,
                    type = binding.educationType.getText().toString(),
                    yearStart = binding.educationYearStart.getText().toString(),
                    yearEnd = binding.educationYearEnd.getText().toString(),
                    description = binding.educationDescription.getText().toString(),
                )
            ),
            listOf(
                JobExperience(
                    id = id,
                    resumeId = id,
                    dateStart = binding.jobExperienceDateStart.getText().toString(),
                    dateEnd = binding.jobExperienceDateEnd.getText().toString(),
                    companyName = binding.JobExperienceCompanyName.getText().toString(),
                    description = binding.JobExperienceDescription.getText().toString(),
                )
            ),
            freeForm = binding.freeForm.getText().toString(),
            tags = emptyList(),
        )
    }

    private fun bindResume(
        binding: FragmentMyResumeBinding,
        resume: Resume?
    ) {
        if (resume != null) {
            val candidateInfo = resume.candidateInfo
            binding.name.setText(candidateInfo.name)
            binding.profession.setText(candidateInfo.profession)
            binding.sex.setText(candidateInfo.sex)
            binding.birthDate.setText(candidateInfo.birthDate)
            binding.phone.setText(candidateInfo.contact.phone)
            binding.email.setText(candidateInfo.contact.email)
            binding.relocation.setText(candidateInfo.relocation)

            if (resume.education.isNotEmpty()) {
                val education = resume.education[0]
                binding.educationType.setText(education.type)
                binding.educationYearStart.setText(education.yearStart)
                binding.educationYearEnd.setText(education.yearEnd)
                binding.educationDescription.setText(education.description)
            }

            if (resume.jobExperience.isNotEmpty()) {
                val jobExperience = resume.jobExperience[0]
                binding.jobExperienceDateStart.setText(jobExperience.dateStart)
                binding.jobExperienceDateEnd.setText(jobExperience.dateEnd)
                binding.JobExperienceCompanyName.setText(jobExperience.companyName)
                binding.JobExperienceDescription.setText(jobExperience.description)
            }

            binding.freeForm.setText(resume.freeForm)

            val tags = resume.tags.map {
                it.tag
            }
            binding.tags.text = tags.toString()
        }
    }

}
