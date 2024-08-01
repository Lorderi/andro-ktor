package ru.lorderi.androktor.adapter.advancedvacancy

import androidx.recyclerview.widget.DiffUtil
import ru.lorderi.androktor.model.Vacancy

class AdvancedVacancyDiffCallback : DiffUtil.ItemCallback<Vacancy>() {
    override fun areItemsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Vacancy, newItem: Vacancy): Boolean =
        oldItem == newItem
}