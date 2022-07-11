package cc.datafabric.dpt2.app.expression.projection.projections

import cc.datafabric.dpt2.app.expression.FieldLink
import cc.datafabric.dpt2.app.expression.ParameterDescription
import cc.datafabric.dpt2.app.expression.projection.Projection
import cc.datafabric.dpt2.app.expression.projection.ProjectionComponent
import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.Document

@ProjectionComponent(name = "toLower", description = "Нижний регистр")
class ToLowerProjection : Projection<ToLowerParameters> {
    override fun parameters() = ToLowerParameters::class.java

    override fun expression(parameters: ToLowerParameters) = with(parameters) {
        Document("\$toLower", "\$${field.field}")
    }
}

data class ToLowerParameters(
    @ParameterDescription("поле")
    @JsonProperty("field")
    val field: FieldLink
)