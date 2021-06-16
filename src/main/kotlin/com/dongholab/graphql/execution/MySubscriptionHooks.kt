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

package com.dongholab.graphql.execution

import com.dongholab.graphql.context.MySubscriptionGraphQLContext
import com.expediagroup.graphql.generator.execution.GraphQLContext
import com.expediagroup.graphql.server.spring.subscriptions.ApolloSubscriptionHooks
import org.springframework.web.reactive.socket.WebSocketSession

/**
 * A simple implementation of Apollo Subscription Lifecycle Events.
 */
class MySubscriptionHooks : ApolloSubscriptionHooks {

    override fun onConnect(
        connectionParams: Map<String, String>,
        session: WebSocketSession,
        graphQLContext: GraphQLContext?
    ): GraphQLContext? {
        if (graphQLContext != null && graphQLContext is MySubscriptionGraphQLContext) {
            graphQLContext.auth = connectionParams["Authorization"]
        }
        return graphQLContext
    }
}
