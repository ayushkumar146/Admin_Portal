package com.admin.portal.service;

import com.admin.portal.dto.StartProcessRequest;
import io.camunda.client.api.response.ProcessInstanceEvent;

public interface ProcessStartService {

    ProcessInstanceEvent startProcess(StartProcessRequest request);
}
