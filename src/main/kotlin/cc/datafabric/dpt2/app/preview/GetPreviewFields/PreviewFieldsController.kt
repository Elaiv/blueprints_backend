package cc.datafabric.dpt2.app.preview.GetPreviewFields

import org.bson.Document
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/preview/get-fields")
class PreviewFieldsController(
    val previewFieldsService: PreviewFieldsService
) {

    @GetMapping
    fun getPreviewFields(
        @RequestParam("id") id: String,
        @RequestParam("stages") stages: String
    ) = previewFieldsService.getPreviewFields(id, stages)
}