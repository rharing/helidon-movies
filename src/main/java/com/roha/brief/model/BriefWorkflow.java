package com.roha.brief.model;

public enum BriefWorkflow {
    DOWNLOADED{
        @Override
        public BriefWorkflow next() {
            return UPLOADED;
        }
    }, UPLOADED{
        @Override
        public BriefWorkflow next() {
            return GENERATED;
        }
    }, GENERATED{
        @Override
        public BriefWorkflow next() {
            return APPROVED;
        }
    }, APPROVED{
        @Override
        public BriefWorkflow next() {
            return DOWNLOADED;
        }
    };

    public abstract BriefWorkflow next();
}
