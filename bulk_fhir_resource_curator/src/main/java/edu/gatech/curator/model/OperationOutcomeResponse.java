package edu.gatech.curator.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OperationOutcomeResponse {

    private String resourceType;
    private Text text;
    private List<Issue> issue;

    @JsonProperty("resourceType")
    public String getResourceType() {
        return resourceType;
    }

    @JsonProperty("text")
    public Text getText() {
        return text;
    }

    @JsonProperty("issue")
    public List<Issue> getIssue() {
        return issue;
    }

    public static class Text {

        private String status;
        private String div;

        @JsonProperty("div")
        public String getDiv() {
            return div;
        }

        @JsonProperty("status")
        public String getStatus() {
            return status;
        }
    }

    public static class Issue {
        private String severity;
        private String code;
        private String diagnostics;

        @JsonProperty("severity")
        public String getSeverity() {
            return severity;
        }

        @JsonProperty("code")
        public String getCode() {
            return code;
        }

        @JsonProperty("diagnostics")
        public String getDiagnostics() {
            return diagnostics;
        }
    }
}
