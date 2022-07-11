package cc.datafabric.dpt2.app.expression.projection

import com.fasterxml.jackson.annotation.JsonProperty
import org.bson.Document
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/projection")
class ProjectionController(
    val projectionService: ProjectionService
) {

    @GetMapping
    fun projections() = projectionService.projections()

    @GetMapping("/{name}/declaration")
    fun getParameterDeclaration(@PathVariable name: String) =
        projectionService.declaration(name)


    @PostMapping("/expression")
    fun getFullExpression(
        @RequestBody body: ExecuteProjectionRequest
    ): List<Document> {
        return projectionService.pipeline(body.fields)
    }

    @GetMapping("/fields")
    fun fields(
        @RequestParam connection: String,
        @RequestParam db: String,
        @RequestParam collection: String,
        @RequestParam limit: Int
    ): Set<String> {
        return projectionService.fields(connection, db, collection, limit)
    }

//    @PostMapping("/preview")
//    fun preview(
//        @RequestBody body: ExecuteProjectionRequest,
//        @RequestParam connectionId: String,
//        @RequestParam db: String,
//        @RequestParam collection: String,
//        @RequestParam limit: Int = 10
//    ): List<Document> {
//        return projectionService.preview(body.fields, connection(connectionId), db, collection, limit)
//    }

//    @PostMapping("/execute")
//    fun execute(
//        @RequestBody body: ExecuteProjectionRequest,
//        @RequestParam connection: String,
//        @RequestParam db: String,
//        @RequestParam collection: String,
//        @RequestParam output: String,
//        user: Principal
//    ): Map<String, String>{
//        projectionService.execute(body.fields, connection, db, collection, output, user)
//        return mapOf("result" to "success")
//    }
//
//    fun connection(connectionId: String): String {
//        return mongoConnectionPropsRepository
//            .findById(connectionId)
//            .map { it.url }.get()
//    }
}

data class ExecuteProjectionRequest(
    @JsonProperty("fields")
    val fields: List<ProjectionField>
)