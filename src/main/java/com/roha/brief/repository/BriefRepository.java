package com.roha.brief.repository;

import com.roha.brief.model.BriefTemplate;
import com.roha.brief.model.BriefType;
import com.roha.brief.model.BriefWorkflow;

import java.util.ArrayList;
import java.util.List;


public class BriefRepository {

    public List<BriefTemplate> brieven = new ArrayList<>();

    public BriefRepository(List<BriefTemplate> brieven) {
        if(brieven != null) {
            this.brieven = brieven;
        }
        init();
    }

    private void init() {
        if(this.brieven.isEmpty()){
            this.brieven.add(new BriefTemplate.Builder(BriefType.BKH).id(1L).version(1).content("old-1 content".getBytes()).build());
            this.brieven.add(new BriefTemplate.Builder(BriefType.BKV).id(2L).version(1).content("old-1 content".getBytes()).build());
        }
    }

    public BriefTemplate startWork(BriefType briefType) {
        BriefTemplate briefTemplate = locateBrief(briefType, BriefWorkflow.APPROVED);
        BriefTemplate briefTemplateForEdit = briefTemplate.nextPhase();
        this.brieven.add(briefTemplateForEdit);
        return briefTemplateForEdit;
    }

    public BriefTemplate upload(BriefType briefType, byte[] bytes) {
        BriefTemplate briefTemplate = locateBrief(briefType, BriefWorkflow.DOWNLOADED);
        BriefTemplate briefTemplateForEdit = new BriefTemplate.Builder(briefTemplate).content(bytes).build();
        BriefTemplate uploaded = briefTemplateForEdit.nextPhase();
        this.brieven.add(uploaded);
        return uploaded;
    }

    public BriefTemplate locateBrief(BriefType briefType, BriefWorkflow briefWorkflow){
        return this.brieven.stream().filter(briefTemplate-> briefTemplate.getBriefType() == briefType && briefTemplate.getBriefWorkflow()== briefWorkflow).findFirst().get();

    }

    public BriefTemplate getActive(BriefType briefType) {
        return locateBrief(briefType, BriefWorkflow.APPROVED);
    }

    public void approve(BriefType briefType) {
        locateBrief(briefType, BriefWorkflow.UPLOADED );
    }
}
