package cc.datafabric.dpt2.app.preview.GetPreviewFields

import com.mongodb.client.MongoClient
import org.bson.Document
import org.bson.conversions.Bson
import org.springframework.stereotype.Service

@Service
class PreviewFieldsService(
    val mongoClient: MongoClient
) {
    fun getPreviewFields(id: String, stages: String): ArrayList<Document> {
        var str = "{\"data\":[$stages, " +
                "{\"\$unset\": \"_id\"}, " +
                "{\"\$project\":{\"arrayofkeyvalue\":{\"\$objectToArray\":\"\$\$ROOT\"}}}, " +
                "{\"\$project\":{\"keys\":\"\$arrayofkeyvalue.kы\"}}]}"
        var str2 = Document.parse(str).get("data")
        return mongoClient
            .getDatabase("dpt_data")
            .getCollection("collection_$id")
            .aggregate(str2 as MutableList<out Bson>).into(ArrayList())
    }
}