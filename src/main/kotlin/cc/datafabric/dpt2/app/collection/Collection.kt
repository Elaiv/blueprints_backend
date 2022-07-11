package cc.datafabric.dpt2.app.collection

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document
data class Collection (
    var id: String? = null,
    var alias: String,
//    var fields: MutableList<FieldMeta>
)

data class FieldMeta(
    var name: String,
    var type: FieldType
)

enum class FieldType {
    STRING, NUMBER, DOCUMENT, LIST, OBJECT_ID, DATE
}

interface CollectionRepository : MongoRepository<Collection, String> {}