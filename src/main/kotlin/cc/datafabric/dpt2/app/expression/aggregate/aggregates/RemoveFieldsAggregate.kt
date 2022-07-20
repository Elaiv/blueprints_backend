package cc.datafabric.dpt2.app.expression.aggregate.aggregates

import cc.datafabric.dpt2.app.expression.aggregate.Aggregate
import cc.datafabric.dpt2.app.expression.aggregate.AggregateComponent
import org.bson.Document
import org.springframework.data.mongodb.core.aggregation.Fields

@AggregateComponent(name = "RemoveField", description = "Удаление полей(RemoveField)")
class RemoveFieldsAggregate: Aggregate<RemoveFieldsParameter> {
    override fun parameters() = RemoveFieldsParameter::class.java

    override fun pipeline(parameters: RemoveFieldsParameter, collection: String, filter: Document) = with(parameters) {
        listOf(
            Document()
        )
    }
}

data class RemoveFieldsParameter (val fields: ArrayList<Fields>)