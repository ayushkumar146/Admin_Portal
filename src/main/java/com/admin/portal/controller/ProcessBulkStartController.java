package com.admin.portal.controller;

import com.admin.portal.dto.StartProcessRequest;
import com.admin.portal.service.ProcessStartService;
import io.camunda.client.api.response.ProcessInstanceEvent;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/process")
public class ProcessBulkStartController {

    private final ProcessStartService processStartService;

    public ProcessBulkStartController(ProcessStartService processStartService) {
        this.processStartService = processStartService;
    }

    @PostMapping("/BulkStart")
    public ResponseEntity<List<Long>> startProcessTenTimes(
            @RequestBody StartProcessRequest request) {

        List<Long> processInstanceIds = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {

            ProcessInstanceEvent event =
                    processStartService.startProcess(request);

            processInstanceIds.add(event.getProcessInstanceKey());
        }

        return ResponseEntity.ok(processInstanceIds);
    }
}
