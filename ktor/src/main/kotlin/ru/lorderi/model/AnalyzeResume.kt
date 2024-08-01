package ru.lorderi.model

val ALL_TAGS = listOf("QA", "IT", "Mathematics", "Android")

fun analyzeResume(resume: Resume): List<String> {
    val strResume = resume.toString()
    println(strResume)
    val tags = mutableListOf<String>()
    ALL_TAGS.forEach {
        if (strResume.contains(it, ignoreCase = true)) {
            println(it)
            tags.add(it)
        }
    }
    return tags
}
