/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alibaba.cloud.ai.example.controller;

import com.alibaba.cloud.ai.request.SchemaInitRequest;
import com.alibaba.cloud.ai.request.SearchRequest;
import com.alibaba.cloud.ai.service.AnalyticDbVectorStoreManagementService;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AnalyticDbVectorManagementController {

    @Autowired
    private AnalyticDbVectorStoreManagementService vectorStoreManagementService;

    @PostMapping("/add/evidence")
    public Boolean addEvidence(@RequestBody List<com.alibaba.cloud.ai.request.EvidenceRequest> evidenceRequests) {
        return vectorStoreManagementService.addEvidence(evidenceRequests);
    }

    @PostMapping("/search")
    public List<Document> search(@RequestBody SearchRequest searchRequestDTO) throws Exception {
        return vectorStoreManagementService.search(searchRequestDTO);
    }

    @PostMapping("/delete")
    public Boolean deleteDocuments(@RequestBody com.alibaba.cloud.ai.request.DeleteRequest deleteRequest) throws Exception {
        return vectorStoreManagementService.deleteDocuments(deleteRequest);
    }

    @PostMapping("/init/schema")
    public Boolean schema(@RequestBody SchemaInitRequest schemaInitRequest) throws Exception {
        return vectorStoreManagementService.schema(schemaInitRequest);
    }

}
