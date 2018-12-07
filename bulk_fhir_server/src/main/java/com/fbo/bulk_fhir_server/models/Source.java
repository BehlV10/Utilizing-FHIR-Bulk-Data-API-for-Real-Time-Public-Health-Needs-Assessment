package com.fbo.bulk_fhir_server.models;

public class Source {
    private final String source;
    private final SourceMeta meta;

    public Source(String source, SourceMeta sourceMeta) {
        this.source = source;
        this.meta = sourceMeta;
    }

    /**
     * @return the meta
     */
    public SourceMeta getMeta() {
        return meta;
    }

    public String getSource() {
        return source;
    }
}
