package com.admin.portal.service;

import com.admin.portal.dto.BpmnDTO;

public interface BpmnService {

    // Fetch BPMN content by MongoDB _id
    BpmnDTO getById(String id);

    // Deploy BPMN content by MongoDB _id
    String deployById(String id);

    // Deploy BPMN content by workflowID + bankcode
    String deployByWorkflow(String workflowID, String bankcode);
    // Fetch BPMN by workflowID and bankcode
    BpmnDTO getByWorkflowIDAndBankcode(String workflowID, String bankcode);
}
