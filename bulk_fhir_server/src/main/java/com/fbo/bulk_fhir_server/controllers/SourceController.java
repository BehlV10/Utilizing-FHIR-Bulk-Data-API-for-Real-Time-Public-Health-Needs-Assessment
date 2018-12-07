package com.fbo.bulk_fhir_server.controllers;

import com.fbo.bulk_fhir_server.models.ApiResponse;
import com.fbo.bulk_fhir_server.models.Source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
public class SourceController {

    @Autowired
    public SourceManager sourceManager;

    @RequestMapping(method=GET, path="/api/sources")
    public ApiResponse<ArrayList<Source>> sources() {
        ArrayList<Source> sources = sourceManager.getSources();
        return new ApiResponse<ArrayList<Source>>(sources);
    }

}
