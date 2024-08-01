package ru.lorderi.androktor.adapter.company

import androidx.recyclerview.widget.DiffUtil
import ru.lorderi.androktor.model.ClientCompany

class CompanyDiffCallback : DiffUtil.ItemCallback<ClientCompany>() {
    override fun areItemsTheSame(oldItem: ClientCompany, newItem: ClientCompany): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: ClientCompany, newItem: ClientCompany): Boolean =
        oldItem == newItem
}