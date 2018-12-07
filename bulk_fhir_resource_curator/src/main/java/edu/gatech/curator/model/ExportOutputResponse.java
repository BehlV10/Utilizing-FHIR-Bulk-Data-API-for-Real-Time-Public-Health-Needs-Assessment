package edu.gatech.curator.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ExportOutputResponse {
    @JsonProperty("transactionTime")
    private Date transactionTime;

    @JsonProperty("request")
    private String request;

    @JsonProperty("requiresAccessToken")
    private boolean requiresAccessToken;

    @JsonProperty("output")
    private List<ExportOutput> output;

    @JsonProperty("errors")
    @JsonIgnore
    private List<Object> errors;

    protected ExportOutputResponse() {}

    public ExportOutputResponse(Date transactionTime, String request, boolean requiresAccessToken, List<ExportOutput> output, List<Object> errors) {
        this.transactionTime = transactionTime;
        this.request = request;
        this.requiresAccessToken = requiresAccessToken;
        this.output = output;
        this.errors = errors;
    }

    public Date getTransactionTime() {
        return transactionTime;
    }

    public String getRequest() {
        return request;
    }

    public boolean isRequiresAccessToken() {
        return requiresAccessToken;
    }

    public List<ExportOutput> getOutput() {
        return output;
    }

    public List<Object> getErrors() {
        return errors;
    }

    public static class ExportOutput {
        private String type;

        private int count;

        private String url;

        protected ExportOutput() {}

        public ExportOutput(String type, int count, String url) {
            this.type = type;
            this.count = count;
            this.url = url;
        }

        public String getType() {
            return type;
        }

        public int getCount() {
            return count;
        }

        public String getUrl() {
            return url;
        }
    }
}
