package ru.lorderi

import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import junit.framework.TestCase.assertEquals
import ru.lorderi.model.*
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testGetCompanies() = testApplication {
        val response = client.get("/api/companies")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"listOfCompanies\":[{\"id\":1,\"name\":\"OOO SuperPay\",\"field_of_activity\":\"banking\"},{\"id\":2,\"name\":\"MTM\",\"field_of_activity\":\"banking\"},{\"id\":3,\"name\":\"CryptoSuperGo\",\"field_of_activity\":\"banking\"},{\"id\":4,\"name\":\"PlatiNalogi\",\"field_of_activity\":\"public services\"},{\"id\":5,\"name\":\"NeftGazIkra\",\"field_of_activity\":\"public services\"},{\"id\":6,\"name\":\"OOO SoftForHomies\",\"field_of_activity\":\"IT\"},{\"id\":7,\"name\":\"MobileGamesPro\",\"field_of_activity\":\"IT\"},{\"id\":8,\"name\":\"FoodsAndGoods\",\"field_of_activity\":\"public services\"},{\"id\":9,\"name\":\"VseIgry\",\"field_of_activity\":\"IT\"},{\"id\":10,\"name\":\"ItBankingMax\",\"field_of_activity\":\"banking\"}]}",
            response.bodyAsText()
        )
    }

    @Test
    fun testGetCompany() = testApplication {
        val response = client.get("/api/companies/1")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"id\":1,\"name\":\"OOO SuperPay\",\"field_of_activity\":\"banking\",\"vacancies\":[{\"id\":1,\"company\":\"OOO SuperPay\",\"company_id\":1,\"profession\":\"qa\",\"level\":\"middle\",\"salary\":80000},{\"id\":2,\"company\":\"OOO SuperPay\",\"company_id\":1,\"profession\":\"pm\",\"level\":\"senior\",\"salary\":130000}],\"contacts\":\"79815354235\"}",
            response.bodyAsText()
        )
    }

    @Test
    fun testGetVacancies() = testApplication {
        val response = client.get("/api/vacancies")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"listOfVacancies\":[{\"id\":1,\"company\":\"OOO SuperPay\",\"company_id\":1,\"profession\":\"qa\",\"level\":\"middle\",\"salary\":80000},{\"id\":2,\"company\":\"OOO SuperPay\",\"company_id\":1,\"profession\":\"pm\",\"level\":\"senior\",\"salary\":130000},{\"id\":3,\"company\":\"MTM\",\"company_id\":2,\"profession\":\"designer\",\"level\":\"senior\",\"salary\":150000},{\"id\":4,\"company\":\"CryptoSuperGo\",\"company_id\":3,\"profession\":\"developer\",\"level\":\"junior\",\"salary\":90000},{\"id\":5,\"company\":\"PlatiNalogi\",\"company_id\":4,\"profession\":\"developer\",\"level\":\"middle\",\"salary\":150000},{\"id\":6,\"company\":\"PlatiNalogi\",\"company_id\":4,\"profession\":\"designer\",\"level\":\"middle\",\"salary\":100000},{\"id\":7,\"company\":\"PlatiNalogi\",\"company_id\":4,\"profession\":\"pm\",\"level\":\"senior\",\"salary\":160000},{\"id\":8,\"company\":\"NeftGazIkra\",\"company_id\":5,\"profession\":\"analyst\",\"level\":\"junior\",\"salary\":70000},{\"id\":9,\"company\":\"OOO SoftForHomies\",\"company_id\":6,\"profession\":\"PM\",\"level\":\"middle\",\"salary\":100000},{\"id\":10,\"company\":\"OOO SoftForHomies\",\"company_id\":6,\"profession\":\"qa\",\"level\":\"junior\",\"salary\":60000},{\"id\":11,\"company\":\"MobileGamesPro\",\"company_id\":7,\"profession\":\"qa\",\"level\":\"senior\",\"salary\":130000},{\"id\":12,\"company\":\"FoodsAndGoods\",\"company_id\":8,\"profession\":\"designer\",\"level\":\"middle\",\"salary\":130000},{\"id\":13,\"company\":\"FoodsAndGoods\",\"company_id\":8,\"profession\":\"developer\",\"level\":\"junior\",\"salary\":100000},{\"id\":14,\"company\":\"FoodsAndGoods\",\"company_id\":8,\"profession\":\"analyst\",\"level\":\"senior\",\"salary\":150000},{\"id\":15,\"company\":\"VseIgry\",\"company_id\":9,\"profession\":\"developer\",\"level\":\"senior\",\"salary\":200000},{\"id\":16,\"company\":\"VseIgry\",\"company_id\":9,\"profession\":\"designer\",\"level\":\"senior\",\"salary\":180000},{\"id\":17,\"company\":\"ItBankingMax\",\"company_id\":10,\"profession\":\"qa\",\"level\":\"senior\",\"salary\":140000}]}",
            response.bodyAsText()
        )
    }

    @Test
    fun testGetVacancy() = testApplication {
        val response = client.get("/api/vacancies/1")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"id\":1,\"company\":\"OOO SuperPay\",\"company_id\":1,\"profession\":\"qa\",\"level\":\"middle\",\"salary\":80000}",
            response.bodyAsText()
        )
    }

    @Test
    fun testGetResume() = testApplication {
        val response = client.get("/api/resumes/1")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"id\":1,\"candidate_info\":{\"name\":\"Vasiliev Sergei Petrovich\",\"profession\":\"QA\",\"sex\":\"male\",\"birth_date\":\"30.09.1998\",\"contacts\":{\"phone\":\"72938572843\",\"email\":\"vspetrovich@pochta.ru\"},\"relocation\":\"possible\"},\"education\":[{\"id\":1,\"resume_id\":1,\"type\":\"secondary\",\"year_start\":\"2005\",\"year_end\":\"2013\",\"description\":\"Lyceum 156\"},{\"id\":2,\"resume_id\":1,\"type\":\"secondary\",\"year_start\":\"2005\",\"year_end\":\"2013\",\"description\":\"Lyceum 156\"},{\"id\":3,\"resume_id\":1,\"type\":\"secondary\",\"year_start\":\"2005\",\"year_end\":\"2013\",\"description\":\"Lyceum 156\"}],\"job_experience\":[{\"id\":1,\"resume_id\":1,\"date_start\":\"05.2022\",\"date_end\":\"01.2023\",\"company_name\":\"SoftProm\",\"description\":\"Typical galley\"},{\"id\":2,\"resume_id\":1,\"date_start\":\"05.2022\",\"date_end\":\"01.2023\",\"company_name\":\"SoftProm\",\"description\":\"Typical galley\"}],\"free_form\":\"I'm a QA specialist from head to heels. ...\",\"tags\":[{\"id\":1,\"resumeId\":1,\"tag\":\"QA\"},{\"id\":2,\"resumeId\":1,\"tag\":\"IT\"},{\"id\":3,\"resumeId\":1,\"tag\":\"Mathematics\"}]}",
            response.bodyAsText()
        )
    }

    @Test
    fun testPostResume() = testApplication {
        val client = createClient {
            install(ContentNegotiation) {
                json()
            }
        }
        val response = client.post("/api/resumes/1") {
            contentType(ContentType.Application.Json)
            setBody(
                ClientResume(
                    id = 1,
                    candidateInfo = CandidateInfo(
                        name = "Vasiliev Sergei Petrovich",
                        profession = "QA",
                        sex = "male",
                        birthDate = "30.09.1998",
                        contact = Contacts(
                            phone = "72938572843",
                            email = "vspetrovich@pochta.ru"
                        ),
                        relocation = "possible"
                    ),
                    education = listOf(
                        Education(
                            id = 1,
                            resumeId = 1,
                            type = "higher",
                            yearStart = "2017",
                            yearEnd = "2021",
                            description = "Mathematics in state university"
                        ),
                        Education(
                            id = 2,
                            resumeId = 1,
                            type = "secondary special",
                            yearStart = "2013",
                            yearEnd = "2017",
                            description = "College of informatics"
                        ),
                        Education(
                            id = 3,
                            resumeId = 1,
                            type = "secondary",
                            yearStart = "2005",
                            yearEnd = "2013",
                            description = "Lyceum 156"
                        )
                    ),
                    jobExperience = listOf(
                        JobExperience(
                            id = 1,
                            resumeId = 1,
                            dateStart = "08.2021",
                            dateEnd = "04.2022",
                            companyName = "FinTech",
                            description = "Some fintech company creating a business platform"
                        ),
                        JobExperience(
                            id = 2,
                            resumeId = 1,
                            dateStart = "05.2022",
                            dateEnd = "01.2023",
                            companyName = "SoftProm",
                            description = "Typical galley"
                        )
                    ),
                    freeForm = "I'm a QA specialist from head to heels. ...",
                    tags = emptyList()
                )
            )
        }
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "{\"id\":1,\"candidate_info\":{\"name\":\"Vasiliev Sergei Petrovich\",\"profession\":\"QA\",\"sex\":\"male\",\"birth_date\":\"30.09.1998\",\"contacts\":{\"phone\":\"72938572843\",\"email\":\"vspetrovich@pochta.ru\"},\"relocation\":\"possible\"},\"education\":[{\"id\":1,\"resume_id\":1,\"type\":\"secondary\",\"year_start\":\"2005\",\"year_end\":\"2013\",\"description\":\"Lyceum 156\"},{\"id\":2,\"resume_id\":1,\"type\":\"secondary\",\"year_start\":\"2005\",\"year_end\":\"2013\",\"description\":\"Lyceum 156\"},{\"id\":3,\"resume_id\":1,\"type\":\"secondary\",\"year_start\":\"2005\",\"year_end\":\"2013\",\"description\":\"Lyceum 156\"}],\"job_experience\":[{\"id\":1,\"resume_id\":1,\"date_start\":\"05.2022\",\"date_end\":\"01.2023\",\"company_name\":\"SoftProm\",\"description\":\"Typical galley\"},{\"id\":2,\"resume_id\":1,\"date_start\":\"05.2022\",\"date_end\":\"01.2023\",\"company_name\":\"SoftProm\",\"description\":\"Typical galley\"}],\"free_form\":\"I'm a QA specialist from head to heels. ...\",\"tags\":[{\"id\":1,\"resumeId\":1,\"tag\":\"QA\"},{\"id\":2,\"resumeId\":1,\"tag\":\"IT\"},{\"id\":3,\"resumeId\":1,\"tag\":\"Mathematics\"}]}",
            response.bodyAsText()
        )
    }

    @Test
    fun testGetTags() = testApplication {
        val response = client.get("/api/resumes/1/tags")
        assertEquals(HttpStatusCode.OK, response.status)
        assertEquals(
            "[{\"id\":1,\"resumeId\":1,\"tag\":\"QA\"},{\"id\":2,\"resumeId\":1,\"tag\":\"IT\"},{\"id\":3,\"resumeId\":1,\"tag\":\"Mathematics\"}]",
            response.bodyAsText()
        )
    }
}
