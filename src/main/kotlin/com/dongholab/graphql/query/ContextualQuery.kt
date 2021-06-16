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

package com.dongholab.graphql.query

import com.dongholab.graphql.context.MyGraphQLContext
import com.dongholab.graphql.model.ContextualResponse
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

/**
 * Example usage of GraphQLContext. Since the argument [ContextualQuery.contextualQuery] implements
 * the GraphQLContext interface it will not appear in the schema and be populated at runtime.
 */
@Component
class ContextualQuery : Query {

    @GraphQLDescription("query that uses GraphQLContext context")
    fun contextualQuery(
        @GraphQLDescription("some value that will be returned to the user")
        value: Int,
        context: MyGraphQLContext
    ): ContextualResponse = ContextualResponse(value, context.myCustomValue)
}
