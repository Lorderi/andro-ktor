package ru.lorderi.androktor.adapter.company

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.lorderi.androktor.databinding.CardCompanyBinding
import ru.lorderi.androktor.model.ClientCompany

class CompanyAdapter(
    val onDetailClicked: (company: ClientCompany) -> Unit,
) : ListAdapter<ClientCompany, CompanyViewHolder>(CompanyDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompanyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardCompanyBinding.inflate(layoutInflater, parent, false)

        val viewHolder = CompanyViewHolder(binding)

        binding.root.setOnClickListener {
            onDetailClicked(getItem(viewHolder.adapterPosition))
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CompanyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}