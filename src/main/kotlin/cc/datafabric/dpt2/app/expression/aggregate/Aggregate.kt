package cc.datafabric.dpt2.app.expression.aggregate

import org.bson.Document

interface Aggregate<T: Any> {
    fun parameters(): Class<T>
    fun pipeline(parameters: T, collection: String, filter: Document): List<Document>
}