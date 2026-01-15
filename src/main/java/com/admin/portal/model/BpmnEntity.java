package com.admin.portal.model;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Admin_Bpmn")
public class BpmnEntity {

    @Id
    private String id;  // maps to _id

    private String workflowID;

    private String bankcode;

    private String content;  // stores BPMN XML

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getWorkflowID() { return workflowID; }
    public void setWorkflowID(String workflowID) { this.workflowID = workflowID; }

    public String getBankcode() { return bankcode; }
    public void setBankcode(String bankcode) { this.bankcode = bankcode; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}

