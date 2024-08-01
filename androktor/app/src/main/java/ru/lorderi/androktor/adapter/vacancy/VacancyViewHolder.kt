package ru.lorderi.androktor.adapter.vacancy

import androidx.recyclerview.widget.RecyclerView
import ru.lorderi.androktor.databinding.CardVacancyBinding
import ru.lorderi.androktor.model.ClientVacancy
import java.util.Locale

class VacancyViewHolder(
    private val binding: CardVacancyBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(clientVacancy: ClientVacancy) {
        binding.company.text = clientVacancy.company
        binding.level.text = clientVacancy.level
        clientVacancy.profession.uppercase(Locale.ROOT).also { binding.profession.text = it }
        binding.salary.text = clientVacancy.salary.toString()
    }
}
