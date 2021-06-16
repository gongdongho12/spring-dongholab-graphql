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

import com.dongholab.graphql.model.Node
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

@Component
class RecursiveQuery : Query {

    private final val root = Node(id = 0, value = "root", parent = null, children = emptyList())
    private final val nodeA = Node(id = 1, value = "A", parent = root, children = emptyList())
    private final val nodeB = Node(id = 2, value = "B", parent = root, children = emptyList())
    private final val nodeC = Node(id = 3, value = "C", parent = nodeB, children = emptyList())

    init {
        root.children = listOf(nodeA, nodeB)
        nodeB.children = listOf(nodeC)
    }

    @GraphQLDescription("Returns the root of a node graph")
    fun nodeGraph(): Node = root
}
