//package cc.datafabric.dpt2.app.data
//
//import cc.datafabric.dpt2.app.connections.MongoConnectionProps
//import cc.datafabric.dpt2.app.connections.MongoConnectionPropsRepository
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.node.ObjectNode
//import org.bson.Document
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//import java.util.TreeSet
//
//@RestController
//@RequestMapping("/api/data")
//class DataController(
//    private val mongoClientPool: MongoClientPool
//) {
//    @GetMapping("/databases")
//    fun listDatabases(@RequestParam connectionId: String): List<DatabaseResponseItem> {
//        return mongoClient(connectionId).listDatabaseNames()
//            .into(ArrayList())
//            .map { DatabaseResponseItem(it) }
//    }
//
//    @GetMapping("/collections")
//    fun listCollections(
//        @RequestParam connectionId: String,
//        @RequestParam databaseName: String
//    ): List<CollectionResponseItem> {
//        return mongoClient(connectionId)
//            .getDatabase(databaseName)
//            .listCollectionNames()
//            .into(ArrayList())
//            .map { CollectionResponseItem(it) }
//    }
//
//    @PostMapping("/query")
//    fun query(
//        @RequestBody query: AggregateQuery
//    ): List<Document> {
//        return with(query) {
//            mongoClient(connectionId)
//                .getDatabase(databaseName)
//                .getCollection(collectionName)
//                .aggregate(pipeline.toDocumentList())
//                .into(ArrayList())
//        }
//    }
//
//    @PostMapping("/query-with-fields")
//    fun queryWithFields(
//        @RequestBody query: AggregateQuery
//    ): QueryWithFieldsResponse {
//        val result = with(query) {
//            mongoClient(connectionId)
//                .getDatabase(databaseName)
//                .getCollection(collectionName)
//                .aggregate(pipeline.toDocumentList())
//                .into(ArrayList())
//        }
//
//        val fields = LinkedHashSet<String>()
//
//        for (row in result) {
//            for (key in row.keys) {
//                fields.add(key)
//            }
//        }
//
//        return QueryWithFieldsResponse(
//            result = result,
//            fields = fields
//        )
//    }
//}
//
//data class QueryWithFieldsResponse (
//    val result: List<Document>,
//    val fields: Set<String>
//)
//private fun List<ObjectNode>.toDocumentList(): List<Document> {
//    val objectMapper = ObjectMapper()
//    return map { it.toDocument(objectMapper) }
//}
//
//private fun ObjectNode.toDocument(objectMapper: ObjectMapper): Document {
//    val string = objectMapper.writeValueAsString(this)
//    return Document.parse(string)
//}
//
//data class DatabaseResponseItem(
//    val name: String
//)
//
//data class CollectionResponseItem(
//    val name: String
//)
//
//data class AggregateQuery(
//    val connectionId: String,
//    val databaseName: String,
//    val collectionName: String,
//    val pipeline: List<ObjectNode>
//)