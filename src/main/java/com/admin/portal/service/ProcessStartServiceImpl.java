package com.admin.portal.service;

import com.admin.portal.dto.StartProcessRequest;
import com.admin.portal.service.ProcessStartService;
import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class ProcessStartServiceImpl implements ProcessStartService {

    private final CamundaClient camundaClient;

    public ProcessStartServiceImpl(CamundaClient camundaClient) {
        this.camundaClient = camundaClient;
    }

    @Override
    public ProcessInstanceEvent startProcess(StartProcessRequest reqDto) {

        Map<String, Object> variables = new HashMap<>();

        if (reqDto.getProcessVariables() != null) {
            variables.putAll(reqDto.getProcessVariables());
        }

        // Add application source
        variables.put("applicationSource", reqDto.getApplicationSource());

        // ✅ Start process instance
        ProcessInstanceEvent processInstanceEvent =
                camundaClient
                        .newCreateInstanceCommand()
                        .bpmnProcessId(reqDto.getBpmnId())
                        .latestVersion()
                        .variables(variables)
                        .send()
                        .join();

        // ✅ Extract process instance key
        Long processId = processInstanceEvent.getProcessInstanceKey();

        // ✅ Set additional variables after creation
        Map<String, Object> additionalVariables = new HashMap<>();
        additionalVariables.put("processId", processId);

        camundaClient
                .newSetVariablesCommand(processId)
                .variables(additionalVariables)
                .send()
                .join();

        log.info("✅ Process instance created with key: {}", processId);

        return processInstanceEvent;
    }
}
