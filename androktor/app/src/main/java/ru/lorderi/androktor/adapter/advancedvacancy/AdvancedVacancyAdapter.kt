package ru.lorderi.androktor.adapter.advancedvacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.lorderi.androktor.databinding.CardVacancyAdvancedBinding
import ru.lorderi.androktor.model.Vacancy

class AdvancedVacancyAdapter(
    val onDetailClicked: (company: Vacancy) -> Unit,
) : ListAdapter<Vacancy, AdvancedVacancyViewHolder>(AdvancedVacancyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdvancedVacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardVacancyAdvancedBinding.inflate(layoutInflater, parent, false)

        val viewHolder = AdvancedVacancyViewHolder(binding)

        binding.root.setOnClickListener {
            onDetailClicked(getItem(viewHolder.adapterPosition))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: AdvancedVacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}