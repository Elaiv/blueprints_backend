package cc.datafabric.dpt2.app.preview

import com.mongodb.client.MongoClient
import org.bson.Document
import org.springframework.stereotype.Service

@Service
class PreviewService(
        private val mongoClient: MongoClient
) {

    fun getPreview(id: String, limit: Int, pipeline: List<String>?): ArrayList<Document> {
        return mongoClient
            .getDatabase("dpt_data")
            .getCollection("collection_$id")
            .aggregate(listOf(
//                Document("\$unset", "_id"),
                Document("\$set", Document("_id", Document("\$toString", "\$_id"))),
                Document("\$limit", limit)
            ))
            .into(ArrayList())
    }
}