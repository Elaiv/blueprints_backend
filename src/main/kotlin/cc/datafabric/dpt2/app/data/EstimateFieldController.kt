package cc.datafabric.dpt2.app.data

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/data/collections/estimate-fields")
class EstimateFieldController(
    private val estimateFieldService: EstimateFieldService
) {

    @GetMapping
    fun estimateFields(
        @RequestParam connectionId: String,
        @RequestParam databaseName: String,
        @RequestParam collectionName: String
    ): List<FieldMeta> {
        return estimateFieldService.estimateFields(connectionId, databaseName, collectionName)
    }
}