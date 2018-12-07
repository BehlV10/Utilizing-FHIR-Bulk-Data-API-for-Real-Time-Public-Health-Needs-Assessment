package edu.gatech.curator.model;

import org.hl7.fhir.dstu3.model.Resource;

import java.util.ArrayList;
import java.util.List;

public class NdJson<T extends Resource> {
    private List<T> resources;

    public NdJson () {
        this.resources = new ArrayList<T>();
    }

    public List<T> getResources() {
        return resources;
    }

    public void setResources(List<T> resources) {
        this.resources = resources;
    }
}
