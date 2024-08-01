package ru.lorderi.model

import kotlinx.serialization.json.Json
import java.io.File

fun readCompanies(): CompaniesExtended {
    val file = File("data-samples/listOfCompanies.json")

    if (!file.exists()) {
        throw Exception("File doesn't exists")
    }

    val listOfCompanies = file.bufferedReader()
        .use {
            it.readText()
        }

    return if (listOfCompanies.isNotBlank()) {
        Json.decodeFromString(listOfCompanies)
    } else {
        CompaniesExtended()
    }
}

fun readResume(): Resume? {
    val file = File("data-samples/resume.json")

    if (!file.exists()) {
        throw Exception("File doesn't exists")
    }

    val resume = file.bufferedReader()
        .use {
            it.readText()
        }

    return Json.decodeFromString(resume)
}
