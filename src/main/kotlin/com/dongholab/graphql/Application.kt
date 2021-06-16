/*
 * Copyright 2021 Expedia, Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.dongholab.graphql

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

@SpringBootApplication
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
