package com.fbo.bulk_fhir_server.models;

/**
 * SourceMeta
 */
public class SourceMeta {
    private final int patients_count;
    private final int potential_duplicates_count;
    private final int resolved_duplicates_count;

    public SourceMeta(int patients_count, int potential_duplicates_count, int resolved_duplicates_count) {
        this.patients_count = patients_count;
        this.potential_duplicates_count = potential_duplicates_count;
        this.resolved_duplicates_count = resolved_duplicates_count;
    }

    /**
     * @return the resolved_duplicates_count
     */
    public int getResolved_duplicates_count() {
        return resolved_duplicates_count;
    }

    /**
     * @return the potential_duplicates_count
     */
    public int getPotential_duplicates_count() {
        return potential_duplicates_count;
    }

    /**
     * @return the patients_count
     */
    public int getPatients_count() {
        return patients_count;
    }
}