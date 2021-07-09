package com.dongholab.graphql

import com.dongholab.graphql.config.CustomBeanNameGenerator
import com.dongholab.graphql.directives.CustomDirectiveWiringFactory
import com.dongholab.graphql.exceptions.CustomDataFetcherExceptionHandler
import com.dongholab.graphql.execution.CustomDataFetcherFactoryProvider
import com.dongholab.graphql.execution.MySubscriptionHooks
import com.dongholab.graphql.execution.SpringDataFetcherFactory
import com.dongholab.graphql.hooks.CustomSchemaGeneratorHooks
import com.expediagroup.graphql.generator.directives.KotlinDirectiveWiringFactory
import com.expediagroup.graphql.server.spring.subscriptions.ApolloSubscriptionHooks
import com.fasterxml.jackson.databind.ObjectMapper
import graphql.execution.DataFetcherExceptionHandler
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@SpringBootApplication
@EnableR2dbcRepositories(basePackages = ["com.dongholab.graphql.repository.reactive"])
@EnableJpaRepositories(basePackages = ["com.dongholab.graphql.repository.jpa"])
@ComponentScan(nameGenerator = CustomBeanNameGenerator::class)
class Application {

    @Bean
    fun wiringFactory() = CustomDirectiveWiringFactory()

    @Bean
    fun hooks(wiringFactory: KotlinDirectiveWiringFactory) = CustomSchemaGeneratorHooks(wiringFactory)

    @Bean
    fun dataFetcherFactoryProvider(
        springDataFetcherFactory: SpringDataFetcherFactory,
        objectMapper: ObjectMapper,
        applicationContext: ApplicationContext
    ) = CustomDataFetcherFactoryProvider(springDataFetcherFactory, objectMapper, applicationContext)

    @Bean
    fun dataFetcherExceptionHandler(): DataFetcherExceptionHandler = CustomDataFetcherExceptionHandler()

    @Bean
    fun apolloSubscriptionHooks(): ApolloSubscriptionHooks = MySubscriptionHooks()
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
