package ru.lorderi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ClientCompany(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("field_of_activity")
    val activity: ActivityType,
)

@Serializable
data class ClientCompanies(
    @SerialName("listOfCompanies")
    val listOfCompanies: List<ClientCompany> = emptyList(),
) {
    companion object {
        fun toClientCompany(companyExtended: CompanyExtended): ClientCompany {
            val newCompany =
                with(companyExtended) {
                    ClientCompany(
                        id = id,
                        name = name,
                        activity = activity,
                    )
                }
            return newCompany
        }

        fun toListOfClientVacancy(companyExtended: CompanyExtended): List<Vacancy> {
            val newVacancies =
                companyExtended.vacancies.map { vacancy ->
                    with(vacancy) {
                        Vacancy(
                            id = id,
                            profession = profession,
                            level = level,
                            salary = salary,
                            company = companyExtended.name,
                            companyId = companyExtended.id,
                        )
                    }
                }
            return newVacancies
        }

        fun toClientCompanies(companiesExtended: CompaniesExtended): ClientCompanies =
            ClientCompanies(companiesExtended.listOfCompanies.map(::toClientCompany))


        fun toClientVacancies(companiesExtended: CompaniesExtended): Vacancies =
            Vacancies(companiesExtended.listOfCompanies.flatMap(::toListOfClientVacancy))
    }
}
