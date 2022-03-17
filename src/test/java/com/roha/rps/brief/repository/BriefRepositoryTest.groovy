package com.roha.rps.brief.repository


import com.roha.brief.model.BriefType
import com.roha.brief.model.BriefWorkflow
import com.roha.brief.repository.BriefRepository
import spock.lang.Specification

class BriefRepositoryTest extends Specification {

    private BriefRepository briefRepository = new BriefRepository();
    def "should do complete workflow"(){
        when: "starting to work on an approved version"
        def work = briefRepository.startWork(BriefType.BKH)
        then: "the version should be still the same but the workflow should be updated"
        briefRepository.brieven.size() == 3
        work.briefWorkflow == BriefWorkflow.DOWNLOADED
        work.version == 1;
        when: "someone now wants to generate a brief, and uses the locate"
        def tobeGenerated = briefRepository.locateBrief(BriefType.BKH,BriefWorkflow.APPROVED)
        then: "we should have the approved version"
        tobeGenerated.version ==1;
        tobeGenerated.briefWorkflow ==BriefWorkflow.APPROVED;
        when: "the user uploads a newer version"
        def uploaded = briefRepository.upload(BriefType.BKH, "template".getBytes())
        then:
        briefRepository.brieven.size() == 4
        uploaded.version ==1
        uploaded.getContent() == "template".getBytes()
        when: "we generate the brief while we just uploaded a new version"
        def generated = briefRepository.getActive(BriefType.BKH)
        then:"the template needed for the generation is still the same approved one"
        generated.version ==1
        generated.getContent() == "old-1 content".getBytes()
        when: "finally we approve the new template"
        briefRepository.approve(BriefType.BKH)
        generated = briefRepository.getActive(BriefType.BKH)
        then: "the template used for generating is the newer one"
        generated.getContent() == "template".getBytes()

    }
    def "should locate correct briefTemplate"(){
        when:
        def briefTemplate = briefRepository.locateBrief(BriefType.BKH, BriefWorkflow.APPROVED)
        then:
        briefTemplate.id == 1L
        briefTemplate.version == 1L
        briefTemplate.briefWorkflow == BriefWorkflow.APPROVED
        briefTemplate.briefType == BriefType.BKH
    }
}
