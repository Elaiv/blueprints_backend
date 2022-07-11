package cc.datafabric.dpt2.app.expression

import com.fasterxml.jackson.annotation.JsonProperty
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.*

sealed class ParameterDeclaration

data class SimpleParameterDeclaration(
    val description: String,
    val type: String
) : ParameterDeclaration()

data class IntegerParameterDeclaration(
    val description: String,
    val defaultValue: Int,
    val type: String = "int"
) : ParameterDeclaration()

data class OperatorsParameterDeclaration(
    val description: String,
    val options: List<String>,
    val type: String = "operators"
) : ParameterDeclaration()


data class FieldLink(
    @JsonProperty("collection")
    val collection: String?,
    @JsonProperty("field")
    val field: String?
)

data class Operator(
    @JsonProperty("operand")
    val operand: String?,
    @JsonProperty("operator")
    val operator: String?
)

fun Class<*>.toParameterDeclarations(): Map<String, ParameterDeclaration> {
    val kClass = Reflection.createKotlinClass(this)
    val declarations = mutableMapOf<String, ParameterDeclaration>()

    for (prop in kClass.memberProperties) {
        val declaration = prop.toParameterDeclaration()

        if (declaration != null) {
            declarations[prop.name] = declaration
        }
    }

    return declarations
}

@Target(AnnotationTarget.PROPERTY)
annotation class ParameterDescription(
    val value: String
)

@Target(AnnotationTarget.PROPERTY)
annotation class Options(
    val value: Array<String>
)

@Target(AnnotationTarget.PROPERTY)
annotation class DefaultIntValue(
    val value: Int
)

fun <T, V> KProperty1<T, V>.toParameterDeclaration(): ParameterDeclaration? {

    val description =  findAnnotation<ParameterDescription>()?.value ?: name

    return when(returnType.classifier) {
        FieldLink::class -> SimpleParameterDeclaration(description, "fieldLink")
        Boolean::class -> SimpleParameterDeclaration(description, "bool")
        Int::class -> {
            val defaultValue = findAnnotation<DefaultIntValue>()
            IntegerParameterDeclaration(description, defaultValue?.value ?: 0)
        }
        String::class -> SimpleParameterDeclaration(description, "string")
        List::class -> when(returnType.arguments[0].type?.classifier as KClass<*>) {
            Operator::class -> {
                val options = findAnnotation<Options>()
                OperatorsParameterDeclaration(description, options?.value?.toList() ?: emptyList())
            }
            FieldLink::class -> SimpleParameterDeclaration(description,"fieldList")
            else -> null
        }
        else -> null
    }
}
