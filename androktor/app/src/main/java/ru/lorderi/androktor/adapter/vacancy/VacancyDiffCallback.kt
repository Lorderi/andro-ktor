package ru.lorderi.androktor.adapter.vacancy

import androidx.recyclerview.widget.DiffUtil
import ru.lorderi.androktor.model.ClientVacancy

class VacancyDiffCallback : DiffUtil.ItemCallback<ClientVacancy>() {
    override fun areItemsTheSame(oldItem: ClientVacancy, newItem: ClientVacancy): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ClientVacancy, newItem: ClientVacancy): Boolean =
        oldItem == newItem
}