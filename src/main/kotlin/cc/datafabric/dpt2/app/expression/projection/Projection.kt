package cc.datafabric.dpt2.app.expression.projection

import kotlin.reflect.full.findAnnotation

interface Projection<T> {
    fun parameters(): Class<T>
    fun expression(parameters: T): Any
}

fun Projection<*>.name() = config()?.name ?: defaultName()
fun Projection<*>.defaultName() = this::class.simpleName
fun Projection<*>.description() = config()?.description ?: name()
fun Projection<*>.config() = this::class.findAnnotation<ProjectionComponent>()
