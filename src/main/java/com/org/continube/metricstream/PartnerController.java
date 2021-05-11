package com.org.continube.metricstream;

import java.util.List;

import com.org.continube.metricstream.models.partner.app.App;
import com.org.continube.metricstream.models.partner.app.AppResponse;
import com.org.continube.metricstream.models.partner.controlgroup.ControlGroup;
import com.org.continube.metricstream.models.partner.controlgroup.ControlGroupResponse;
import com.org.continube.metricstream.models.partner.plan.FileReponse;
import com.org.continube.metricstream.models.partner.plan.Plan;
import com.org.continube.metricstream.models.partner.plan.PlanBasicInfo;
import com.org.continube.metricstream.models.partner.run.Run;
import com.org.continube.metricstream.models.partner.run.RunResponse;
import com.org.continube.metricstream.services.CnClient;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PartnerController {

    // supported plan
    @GetMapping(value = "/api/v1/framework/plans")
    public ResponseEntity getAllSupportedPlans() throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        cnClient.setCustomerInfo("customer_1", "customer_security_token");
        List<PlanBasicInfo> planList = cnClient.getAllSupportedPlans();
        return ResponseEntity.ok().body(planList);
    }

    @GetMapping(value = "/api/v1/framework/plans/{planRef}")
    public ResponseEntity getPlanInfo(@PathVariable String planRef) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        Plan planInfo = cnClient.getPlanInfo(planRef);
        return ResponseEntity.ok().body(planInfo);
    }

    @GetMapping(value = "/api/v1/framework/plans/{planRef}/templates/{templateID}")
    public ResponseEntity downloadInputTemplate(@PathVariable String planRef, @PathVariable String templateID)
            throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        FileReponse planTemplateContent = cnClient.downloadInputTemplate(planRef, templateID);
        return ResponseEntity.ok().body(planTemplateContent);
    }

    // Control Groups
    @PostMapping(value = "/api/v1/customer/controlgroups")
    public ResponseEntity createControlGroup(@RequestBody ControlGroup controlGroup) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        ControlGroupResponse controlGroupResp = cnClient.createControlGroup(controlGroup);
        return ResponseEntity.status(HttpStatus.CREATED).body(controlGroupResp);
    }

    @PutMapping(value = "/api/v1/customer/controlgroups/{controlGroupRef}")
    public ResponseEntity updateControlGroup(@PathVariable String controlGroupRef,
            @RequestBody ControlGroup controlGroup) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        ControlGroupResponse controlGroupResp = cnClient.updateControlGroup(controlGroupRef, controlGroup);
        return ResponseEntity.status(HttpStatus.OK).body(controlGroupResp);
    }

    @GetMapping(value = "/api/v1/customer/controlgroups/{controlGroupRef}")
    public ResponseEntity getControlGroupInfo(@PathVariable String controlGroupRef) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        ControlGroup controlGroupInfo = cnClient.getControlGroupInfo(controlGroupRef);
        return ResponseEntity.ok().body(controlGroupInfo);
    }

    @GetMapping(value = "/api/v1/customer/controlgroups")
    public ResponseEntity downloadInputTemplate() throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        List<ControlGroup> controlGroupList = cnClient.getAllControlGroupsOfCustomer();
        return ResponseEntity.ok().body(controlGroupList);
    }

    // App
    @PostMapping(value = "/api/v1/customer/apps")
    public ResponseEntity createApp(@RequestBody App app) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        AppResponse appResp = cnClient.createApp(app);
        return ResponseEntity.status(HttpStatus.CREATED).body(appResp);
    }

    @GetMapping(value = "/api/v1/customer/apps/{customerAppRef}")
    public ResponseEntity getAppInfo(@PathVariable String customerAppRef) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        App appInfo = cnClient.getAppInfo(customerAppRef);
        return ResponseEntity.ok().body(appInfo);
    }

    @GetMapping(value = "/api/v1/customer/apps")
    public ResponseEntity getAllAppsOfCustomer(@RequestParam(defaultValue = "") String type) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        List<App> appsOfCustomer = cnClient.getAllAppsOfCustomer(type);
        return ResponseEntity.ok().body(appsOfCustomer);
    }

    // Run
    @PostMapping(value = "/api/v1/customer/controlgroups/{controlGroupRef}/runs")
    public ResponseEntity runControlGroup(@PathVariable String controlGroupRef, @RequestBody Run run) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        RunResponse runResp = cnClient.runControlGroup(controlGroupRef, run);
        return ResponseEntity.status(HttpStatus.CREATED).body(runResp);
    }

    @GetMapping(value = "/api/v1/customer/controlgroups/{controlGroupRef}/runs/{runID}")
    public ResponseEntity getRunInfo(@PathVariable String controlGroupRef, @PathVariable String runID)
            throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        Run runInfo = cnClient.getRunInfo(controlGroupRef, runID);
        return ResponseEntity.ok().body(runInfo);
    }

    @GetMapping(value = "/api/v1/customer/controlgroups/{controlGroupRef}/runs/{runID}/downloads/{downloadHash}")
    public ResponseEntity downloadOutputFile(@PathVariable String controlGroupRef, @PathVariable String runID,
            @PathVariable String downloadHash) throws Exception {
        CnClient cnClient = CnClient.createInstance("84b7c864-c79c-4e77-8f49-f9c9a3c94881",
                "ba#5SfUgOntp7R$Eag1T1I7xGEG@7y5X");
        FileReponse downloadOutputFile = cnClient.downloadOutputFile(controlGroupRef, runID, downloadHash);
        return ResponseEntity.ok().body(downloadOutputFile);
    }
}
