package com.dongholab.graphql.service

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service

@Service
class ApplicationFinder {
    @Autowired
    private val context: ApplicationContext? = null

    fun findBootClass(): String? {
        val candidates: Map<String, Any>? = context?.getBeansWithAnnotation(
            SpringBootApplication::class.java
        ) ?: null
        return candidates?.let { it.values.toTypedArray()[0].javaClass.`package`.name } ?: null
    }
}