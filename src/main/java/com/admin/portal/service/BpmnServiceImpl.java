package com.admin.portal.service;

import com.admin.portal.mapper.BpmnMapper;
import com.admin.portal.repository.BpmnRepository;
import com.admin.portal.dto.BpmnDTO;
import com.admin.portal.model.BpmnEntity;
import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.DeploymentEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

@Service
public class BpmnServiceImpl implements BpmnService {

    private final BpmnRepository bpmnRepository;
    private final CamundaClient camundaClient;

    @Autowired
    public BpmnServiceImpl(BpmnRepository bpmnRepository, CamundaClient camundaClient) {
        this.bpmnRepository = bpmnRepository;
        this.camundaClient = camundaClient;
    }

    // -----------------------
    // Fetch BPMN
    // -----------------------
    @Override
    public BpmnDTO getById(String id) {
        return bpmnRepository.findById(id)
                .map(BpmnMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("BPMN not found with ID: " + id));
    }


    @Override
    public BpmnDTO getByWorkflowIDAndBankcode(String workflowID, String bankcode) {
        BpmnEntity entity = bpmnRepository.findByWorkflowIDAndBankcode(workflowID, bankcode)
                .orElseThrow(() -> new RuntimeException(
                        "BPMN not found for workflowID: " + workflowID + " and bankcode: " + bankcode
                ));
        return BpmnMapper.toDTO(entity);
    }

    // -----------------------
    // Deploy BPMN
    // -----------------------
    @Override
    public String deployById(String id) {
        BpmnDTO bpmn = getById(id);
        return deployBpmn(bpmn);
    }

    @Override
    public String deployByWorkflow(String workflowID, String bankcode) {
        BpmnEntity entity = bpmnRepository.findByWorkflowIDAndBankcode(workflowID, bankcode)
                .orElseThrow(() -> new RuntimeException(
                        "BPMN not found for workflowID: " + workflowID + " and bankcode: " + bankcode
                ));
        BpmnDTO bpmn = BpmnMapper.toDTO(entity);
        return deployBpmn(bpmn);
    }

    // -----------------------
    // Internal deploy helper
    // -----------------------
    private String deployBpmn(BpmnDTO bpmn) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(
                    bpmn.getContent().getBytes(StandardCharsets.UTF_8)
            );

            DeploymentEvent deployment = camundaClient
                    .newDeployResourceCommand()
                    .addResourceStream(inputStream, bpmn.getWorkflowID() + ".bpmn") // use workflowID for filename
                    .send()
                    .join();

            return "✅ BPMN Deployed successfully: workflowID=" + bpmn.getWorkflowID()
                    + " | Deployment key: " + deployment.getKey();
        } catch (Exception e) {
            return "❌ Failed to deploy BPMN: " + e.getMessage();
        }
    }
}
