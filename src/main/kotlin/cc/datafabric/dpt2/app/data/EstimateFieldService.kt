package cc.datafabric.dpt2.app.data

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoCollection
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.util.*

@Service
class EstimateFieldService(
    private val mongoClient: MongoClient
) {
    fun estimateFields(connectionId: String, databaseName: String, collectionName: String): List<FieldMeta> {
        return mongoClient
            .getDatabase(databaseName)
            .getCollection(collectionName)
            .let { estimateFields(it) }
    }


    private fun estimateFields(collection: MongoCollection<Document>): List<FieldMeta> {
        val fields = LinkedHashSet<String>()
        val types = mutableMapOf<String, FieldType>()

        collection.find().limit(1000).forEach {
            fields.addAll(it.keys)
            types.extractTypeInfo(it)
        }
        return fields.map { FieldMeta(it, types[it] ?: FieldType.STRING) }
    }
}

private fun MutableMap<String, FieldType>.extractTypeInfo(document: Document) {
    for (entry in document.entries) {
        if (get(entry.key) == null && entry.value != null) {
            put(entry.key, entry.value.type())
        }
    }
}

private fun Any.type() = when (this) {
    is ObjectId -> FieldType.OBJECT_ID
    is Number -> FieldType.NUMBER
    is Map<*, *> -> FieldType.DOCUMENT
    is List<*> -> FieldType.LIST
    is Date -> FieldType.DATE
    else -> FieldType.STRING
}

data class FieldMeta(
    val name: String,
    val type: FieldType
)

enum class FieldType {
    STRING, NUMBER, DOCUMENT, LIST, OBJECT_ID, DATE
}