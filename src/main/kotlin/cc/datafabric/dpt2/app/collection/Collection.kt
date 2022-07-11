package cc.datafabric.dpt2.app.collection

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.repository.MongoRepository

@Document
data class Collection (
    var id: String? = null,
    var alias: String,
    var fields: MutableList<FieldMeta>? = mutableListOf()
)

data class FieldMeta(
    var name: String,
    var type: String
)

interface CollectionRepository : MongoRepository<Collection, String>