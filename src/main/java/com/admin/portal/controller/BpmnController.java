package com.admin.portal.controller;

import com.admin.portal.dto.BpmnDTO;
import com.admin.portal.service.BpmnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bpmn")
public class BpmnController {

    private final BpmnService bpmnService;

    @Autowired
    public BpmnController(BpmnService bpmnService) {
        this.bpmnService = bpmnService;
    }

    /**
     * Fetch BPMN by _id and deploy it to Camunda.
     */
    @GetMapping("/deploy/{id}")
    public Map<String, Object> fetchAndDeployById(@PathVariable("id") String id) {
        BpmnDTO bpmn = bpmnService.getById(id);
        if (bpmn == null) {
            return Map.of("message", "❌ No BPMN found with id: " + id);
        }

        String deploymentResult = bpmnService.deployById(id);

        return Map.of(
                "bpmnId", bpmn.getId(),
                "workflowID", bpmn.getWorkflowID(),
                "bpmnContent", bpmn.getContent(),
                "deploymentResult", deploymentResult
        );
    }

    @PostMapping("/deploy/workflow/{workflowID}/{bankcode}")
    public Map<String, Object> deployByWorkflow(
            @PathVariable("workflowID") String workflowID,
            @PathVariable("bankcode") String bankcode
    ) {
        try {
            String deploymentResult = bpmnService.deployByWorkflow(workflowID, bankcode);

            BpmnDTO bpmn = bpmnService.getByWorkflowIDAndBankcode(workflowID, bankcode);

            return Map.of(
                    "bpmnId", bpmn.getId(),
                    "workflowID", workflowID,
                    "bankcode", bankcode,
                    "bpmnContent", bpmn.getContent(),
                    "deploymentResult", deploymentResult
            );
        } catch (Exception e) {
            return Map.of("message", "❌ " + e.getMessage());
        }
    }
}
