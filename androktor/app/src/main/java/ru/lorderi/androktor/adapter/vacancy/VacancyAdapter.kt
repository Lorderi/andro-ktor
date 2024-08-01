package ru.lorderi.androktor.adapter.vacancy

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.lorderi.androktor.databinding.CardVacancyBinding
import ru.lorderi.androktor.model.ClientVacancy

class VacancyAdapter(
    val onDetailClicked: (company: ClientVacancy) -> Unit,
) : ListAdapter<ClientVacancy, VacancyViewHolder>(VacancyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VacancyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardVacancyBinding.inflate(layoutInflater, parent, false)

        val viewHolder = VacancyViewHolder(binding)

        binding.root.setOnClickListener {
            onDetailClicked(getItem(viewHolder.adapterPosition))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: VacancyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}