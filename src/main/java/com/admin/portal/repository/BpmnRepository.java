package com.admin.portal.repository;

import com.admin.portal.model.BpmnEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BpmnRepository extends MongoRepository<BpmnEntity, String> {

    Optional<BpmnEntity> findById(String id);

    Optional<BpmnEntity> findByWorkflowIDAndBankcode(String workflowID, String bankcode);
}

