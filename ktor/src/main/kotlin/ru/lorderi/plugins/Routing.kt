package ru.lorderi.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.lorderi.dao.facade.dao
import ru.lorderi.model.*

fun Application.configureRouting() {
    routing {
        companies()
        vacancies()
        resumes()
    }
}

fun Route.companies() {
    route("/api/companies") {
        get {
            val companies = dao.allCompanies()
            if (companies.isNotEmpty()) {
                val companyListItems = ClientCompanies.toClientCompanies(CompaniesExtended(companies))
                call.respond(companyListItems)
            } else {
                call.respondText("No companies found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val company = dao.company(id) ?: return@get call.respondText(
                "No companies with id $id",
                status = HttpStatusCode.NotFound
            )

            call.respond(company)
        }
    }
}

fun Route.vacancies() {
    route("/api/vacancies") {
        get {
            val vacancies = dao.allVacancies()
            if (vacancies.isNotEmpty()) {
                call.respond(Vacancies(vacancies))
            } else {
                call.respondText("No vacancies found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )
            val vacancy = dao.vacancy(id) ?: return@get call.respondText(
                "No vacancies with id $id",
                status = HttpStatusCode.NotFound
            )
            call.respond(vacancy)
        }
    }
}


fun Route.resumes() {
    route("/api/resumes") {
        get("{id?}") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val resume = dao.resume(id)

            if (resume == null) {
                val emptyResume = Resume(
                    id = id,
                    candidateInfo = CandidateInfo(
                        name = "",
                        profession = "",
                        sex = "",
                        birthDate = "",
                        contact = Contacts(
                            phone = "",
                            email = "",
                        ),
                        relocation = ""
                    ),
                    education = emptyList(),
                    jobExperience = emptyList(),
                    ""
                )
                dao.addNewResume(emptyResume) ?: return@get call.respondText(
                    "Error while adding",
                    status = HttpStatusCode.InternalServerError
                )

                val savedResume = dao.resume(id) ?: return@get call.respondText(
                    "Internal error find id $id",
                    status = HttpStatusCode.NotFound
                )
                call.respond(ClientResume.toClientResume(savedResume, emptyList()))
            } else {
                val tags = dao.tags(id)

//                if (tags.isEmpty()) {
//                    return@get call.respondText(
//                        "Internal error get tag $id",
//                        status = HttpStatusCode.NotFound
//                    )
//                }
                call.respond(ClientResume.toClientResume(resume, tags))
            }
        }

        post("{id?}") {
            val clientResume = call.receive<ClientResume>()
            val id = call.parameters["id"]?.toLong() ?: return@post call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val resume = clientResume.toResume()

            val tags = analyzeResume(resume)

            println("\n\nCreated tags for resume #$id: $tags")
            println("Get resume:\nid=$id\nresume=$resume\n")

            dao.addNewResume(resume) ?: return@post call.respondText(
                "Error while adding",
                status = HttpStatusCode.InternalServerError
            )

            val savedResume = dao.resume(id) ?: return@post call.respondText(
                "Internal error find id $id",
                status = HttpStatusCode.NotFound
            )

            tags.forEach { tag ->
                dao.addNewTag(savedResume.id, tag)
                    ?: return@post call.respondText(
                        "Internal error add tag $id $tag",
                        status = HttpStatusCode.NotFound
                    )
            }

            val savedTags = dao.tags(savedResume.id)

//            if (savedTags.isEmpty()) {
//                return@post call.respondText(
//                    "Internal error get tag $id",
//                    status = HttpStatusCode.NotFound
//                )
//            }

            call.respond(ClientResume.toClientResume(savedResume, savedTags))
        }

        get("{id?}/tags") {
            val id = call.parameters["id"]?.toLong() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val tags = dao.tags(id)

//            if (tags.isEmpty()) {
//                return@get call.respondText(
//                    "Internal error get tag $id",
//                    status = HttpStatusCode.NotFound
//                )
//            }

            call.respond(tags)
        }
    }
}
