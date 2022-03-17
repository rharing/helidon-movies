package com.roha.brief.model;


import java.io.ByteArrayInputStream;
import java.util.Date;

public class BriefTemplate {
    private Long id;
    private BriefType briefType;
    private BriefWorkflow briefWorkflow;
    private Integer version;
    private Date updatedAt;
    private String updatedBy;
    private byte[] content;

    private BriefTemplate(Builder builder) {
        this.id = builder.id;
        this.briefType = builder.briefType;
        this.briefWorkflow = builder.briefWorkflow;
        this.version = builder.version;
        this.updatedAt = builder.updatedAt;
        this.updatedBy = builder.updatedBy;
        this.content = builder.content;
    }

    public Long getId() {
        return id;
    }

    public BriefType getBriefType() {
        return briefType;
    }

    public BriefWorkflow getBriefWorkflow() {
        return briefWorkflow;
    }

    public Integer getVersion() {
        return version;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public byte[] getContent() {
        return content;
    }

    public BriefTemplate nextPhase() {
        Builder builder = new Builder(this).briefWorkflow(this.briefWorkflow.next());
        if (builder.briefWorkflow == BriefWorkflow.APPROVED) {
            builder.version++;
        }
        return builder.build();
    }

    public static final class Builder {
        private Long id;
        private BriefType briefType;
        private BriefWorkflow briefWorkflow = BriefWorkflow.APPROVED;
        private Integer version = 1;
        private Date updatedAt = new Date();
        private String updatedBy = "system";
        private byte[] content;


        public Builder(BriefType val) {
            briefType = val;
        }

        public Builder(BriefTemplate briefTemplate) {
            this.id = briefTemplate.getId();
            this.briefType = briefTemplate.getBriefType();
            this.briefWorkflow = briefTemplate.getBriefWorkflow();
            this.version = briefTemplate.getVersion();
            this.updatedAt = briefTemplate.getUpdatedAt();
            this.updatedBy = briefTemplate.getUpdatedBy();
            this.content = briefTemplate.getContent();
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder briefType(BriefType val) {
            briefType = val;
            return this;
        }

        public Builder briefWorkflow(BriefWorkflow val) {
            briefWorkflow = val;
            return this;
        }

        public Builder version(Integer val) {
            version = val;
            return this;
        }

        public Builder updatedAt(Date val) {
            updatedAt = val;
            return this;
        }

        public Builder updatedBy(String val) {
            updatedBy = val;
            return this;
        }

        public Builder content(byte[] val) {
            content = val;
            return this;
        }

        public BriefTemplate build() {
            return new BriefTemplate(this);
        }
    }
}
