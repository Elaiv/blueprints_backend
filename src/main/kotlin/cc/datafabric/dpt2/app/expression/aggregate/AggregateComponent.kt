package cc.datafabric.dpt2.app.expression.aggregate

import org.springframework.stereotype.Component

@Component
annotation class AggregateComponent(
    val name: String,
    val description: String
)
