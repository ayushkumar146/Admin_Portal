package com.admin.portal.mapper;

import com.admin.portal.dto.BpmnDTO;
import com.admin.portal.model.BpmnEntity;

public class BpmnMapper {


    public static BpmnDTO toDTO(BpmnEntity model) {
        BpmnDTO dto = new BpmnDTO();
        dto.setId(model.getId());
        dto.setWorkflowID(model.getWorkflowID());
        dto.setBankcode(model.getBankcode());
        dto.setContent(model.getContent());
        return dto;
    }


    public static BpmnEntity toEntity(BpmnDTO dto) {
        BpmnEntity model = new BpmnEntity();
        model.setId(dto.getId());
        model.setWorkflowID(dto.getWorkflowID());
        model.setBankcode(dto.getBankcode());
        model.setContent(dto.getContent());
        return model;
    }
}