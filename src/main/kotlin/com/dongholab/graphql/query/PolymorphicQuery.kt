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

import com.dongholab.graphql.model.Animal
import com.dongholab.graphql.model.AnimalType
import com.dongholab.graphql.model.BodyPart
import com.dongholab.graphql.model.Cat
import com.dongholab.graphql.model.Dog
import com.dongholab.graphql.model.Fruit
import com.dongholab.graphql.model.LeftHand
import com.dongholab.graphql.model.RightHand
import com.expediagroup.graphql.generator.annotations.GraphQLDescription
import com.expediagroup.graphql.server.operations.Query
import org.springframework.stereotype.Component

/**
 * Example query that displays the usage of interfaces and polymorphism.
 */
@Component
class PolymorphicQuery : Query {

    @GraphQLDescription("this query returns specific animal type")
    fun animal(type: AnimalType): Animal? = when (type) {
        AnimalType.CAT -> Cat()
        AnimalType.DOG -> Dog()
    }

    fun dog(): Dog = Dog()

    @GraphQLDescription("this query can return either a RightHand or a LeftHand as part of the union of both type")
    fun whichHand(whichHand: String): BodyPart = when (whichHand) {
        "right" -> RightHand(12)
        else -> LeftHand("hello world")
    }

    @GraphQLDescription("Example of interfaces with sealed classes")
    fun getFruit(orange: Boolean) = if (orange) Fruit.Orange() else Fruit.Apple("granny smith")
}
