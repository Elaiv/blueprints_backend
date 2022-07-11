package cc.datafabric.dpt2.app.workflow

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/workflow")
class WorkflowController(
    private val workflowService: WorkflowService
) {
    @PostMapping
    fun saveWorkflow(
        @RequestBody workflow: Workflow,
    ) = workflowService.saveWorkflow(workflow)

    @GetMapping("/all")
    fun getAllWorkflows() = workflowService.getAllWorkflows()
}

