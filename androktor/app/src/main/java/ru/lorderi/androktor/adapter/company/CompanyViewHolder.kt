package ru.lorderi.androktor.adapter.company

import androidx.recyclerview.widget.RecyclerView
import ru.lorderi.androktor.databinding.CardCompanyBinding
import ru.lorderi.androktor.model.ClientCompany

class CompanyViewHolder(
    private val binding: CardCompanyBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(clientCompany: ClientCompany) {
        binding.avatarInitials.text = clientCompany.name.take(1)
        binding.company.text = clientCompany.name
        binding.fieldOfActivity.text = clientCompany.activity.value
    }
}
