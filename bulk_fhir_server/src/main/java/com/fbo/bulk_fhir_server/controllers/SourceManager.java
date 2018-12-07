package com.fbo.bulk_fhir_server.controllers;

import java.util.ArrayList;

import com.fbo.bulk_fhir_server.models.Source;
import com.fbo.bulk_fhir_server.models.SourceMeta;

import org.springframework.stereotype.Component;

/**
 * SourceManager
 */
@Component("SourceManager")
public class SourceManager {
    public ArrayList<Source> getSources() {
        ArrayList<Source> temp = new ArrayList<Source>();
        temp.add(new Source("Su Clinica Familiar", new SourceMeta(30101, 1349, 924)));
        temp.add(new Source("Valley Baptist Medical Centerr", new SourceMeta(120032, 2689, 2198)));
        temp.add(new Source("Brownsville Community Health Center", new SourceMeta(20265, 934, 739)));

        return temp;
    }
}
