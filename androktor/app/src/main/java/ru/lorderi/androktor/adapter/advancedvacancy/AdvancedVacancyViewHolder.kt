package ru.lorderi.androktor.adapter.advancedvacancy

import androidx.recyclerview.widget.RecyclerView
import ru.lorderi.androktor.databinding.CardVacancyAdvancedBinding
import ru.lorderi.androktor.model.Vacancy
import java.util.Locale

class AdvancedVacancyViewHolder(
    private val binding: CardVacancyAdvancedBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(vacancy: Vacancy) {
        binding.level.text = vacancy.level
        vacancy.profession.uppercase(Locale.ROOT).also { binding.profession.text = it }
        binding.salary.text = vacancy.salary.toString()
    }
}
