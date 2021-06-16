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

package com.dongholab.graphql.context

import com.expediagroup.graphql.server.execution.GraphQLContextFactory
import com.expediagroup.graphql.server.spring.execution.SpringGraphQLContextFactory
import com.expediagroup.graphql.server.spring.subscriptions.SpringSubscriptionGraphQLContextFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.socket.WebSocketSession

/**
 * [GraphQLContextFactory] that generates [MyGraphQLContext] that will be available when processing GraphQL requests.
 */
@Component
class MyGraphQLContextFactory : SpringGraphQLContextFactory<MyGraphQLContext>() {

    override suspend fun generateContext(request: ServerRequest): MyGraphQLContext {
        val customValue = request.headers().firstHeader("MyHeader") ?: "defaultContext"

        return MyGraphQLContext(
            request = request,
            myCustomValue = customValue
        )
    }
}

/**
 * [GraphQLContextFactory] that generates [MySubscriptionGraphQLContext] that will be available when processing subscription operations.
 */
@Component
class MySubscriptionGraphQLContextFactory : SpringSubscriptionGraphQLContextFactory<MySubscriptionGraphQLContext>() {

    override suspend fun generateContext(request: WebSocketSession): MySubscriptionGraphQLContext = MySubscriptionGraphQLContext(
        request = request,
        auth = null
    )
}
