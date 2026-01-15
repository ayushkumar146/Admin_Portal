package com.admin.portal.controller;

import com.admin.portal.dto.StartProcessRequest;
import com.admin.portal.service.ProcessStartService;
import io.camunda.client.api.response.ProcessInstanceEvent;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process")
public class ProcessController {

    private final ProcessStartService processStartService;

    public ProcessController(ProcessStartService processStartService) {
        this.processStartService = processStartService;
    }

    @PostMapping("/start")
    public ProcessInstanceEvent startProcess(
            @RequestBody StartProcessRequest request) {

        return processStartService.startProcess(request);
    }
}
