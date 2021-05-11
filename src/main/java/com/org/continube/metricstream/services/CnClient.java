package com.org.continube.metricstream.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Client;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.client.ClientBuilder;

import com.org.continube.metricstream.PartnerConstants;
import com.org.continube.metricstream.Exception.PartnerException;
import com.org.continube.metricstream.Utils.LogFilter;
import com.org.continube.metricstream.Utils.PartnerWsUtils;
import com.org.continube.metricstream.models.partner.app.App;
import com.org.continube.metricstream.models.partner.app.AppResponse;
import com.org.continube.metricstream.models.partner.controlgroup.ControlGroup;
import com.org.continube.metricstream.models.partner.controlgroup.ControlGroupResponse;
import com.org.continube.metricstream.models.partner.oauth.TokenResponse;
import com.org.continube.metricstream.models.partner.plan.FileReponse;
import com.org.continube.metricstream.models.partner.plan.Plan;
import com.org.continube.metricstream.models.partner.plan.PlanBasicInfo;
import com.org.continube.metricstream.models.partner.run.Run;
import com.org.continube.metricstream.models.partner.run.RunResponse;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.annotate.JsonMethod;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.oauth2.OAuth2ClientSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class CnClient {

    private String clientId;
    private String clientSecret;
    private String customerRef;
    private String customerSecurityToken;
    private static Client client;
    private static ClientConfig clientConfig;
    private static Logger logger = LoggerFactory.getLogger(CnClient.class);
    private static PropertiesConfiguration config = null;
    private final static String CONFIG_FILE = "partnerconfig/partner.properties";

    public CnClient(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    public void setCustomerInfo(String customerRef, String customerSecurityToken) {
        this.customerRef = customerRef;
        this.customerSecurityToken = customerSecurityToken;
    }

    public static CnClient createInstance(String clientID, String clientSecret) {
        try {
            File f = null;
            f = new File(new File("."), CONFIG_FILE);
            if (f.exists()) {
                logger.info("Using Configuration " + f.getAbsolutePath());
                config = new PropertiesConfiguration(f);
            } else {
                logger.info("Using default Configuration");
                config = new PropertiesConfiguration(CONFIG_FILE);
            }
            clientConfig = new ClientConfig(config);
            clientConfig.register(JacksonFeature.class);
            clientConfig.register(LogFilter.class);
            clientConfig.register(MultiPartFeature.class);
            client = ClientBuilder.newClient(clientConfig);
            String oauthToken = fetchToken(clientID, clientSecret);
            Feature feature = OAuth2ClientSupport.feature(oauthToken);
            client.register(feature);
            return new CnClient(clientID, clientSecret);
        } catch (ConfigurationException e) {
            throw new PartnerException("Configuration file not found");
        }
    }

    private static void setResourceUrl(String url) {
        if (config.getString("partner.service.url") == null) {
            throw new PartnerException("missing partner url");
        }
        PartnerWsUtils.setResourceName(config.getString("partner.service.url") + url, client);
    }

    private MultivaluedMap<String, Object> setHeaders() {
        MultivaluedMap<String, Object> headers = new MultivaluedHashMap<String, Object>();
        if (customerRef != null && customerSecurityToken != null) {
            headers.add("CustomerSecurityToken", customerSecurityToken);
        }
        return headers;
    }

    // fetching auth token
    private static String fetchToken(String clientID, String clientSecret) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Form credientialFrm = new Form();
            credientialFrm.param("client_id", clientID);
            credientialFrm.param("client_secret", clientSecret);
            credientialFrm.param("grant_type", "client_credentials");
            setResourceUrl(PartnerConstants.PARTNER_OAUTH_URL);
            Response oauthTokenResp = PartnerWsUtils.post(credientialFrm);
            String content = oauthTokenResp.readEntity(String.class);
            System.out.println("Content ::: " + content);
            if (oauthTokenResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on fetching oauth token :" + oauthTokenResp.getStatus());
            }
            TypeReference<TokenResponse> ref = new TypeReference<TokenResponse>() {
            };
            TokenResponse tokenResp = mapper.readValue(content, ref);
            return tokenResp.getAccess_token();
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    // Supported plans
    public List<PlanBasicInfo> getAllSupportedPlans() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.PLAN_URL);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response planListResp = PartnerWsUtils.get(headers);
            String content = planListResp.readEntity(String.class);
            System.out.println("PlanBasicInfo ::: " + content);
            if (planListResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                setResourceUrl(PartnerConstants.PLAN_URL);
                planListResp = PartnerWsUtils.get(headers);
            }
            if (planListResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on get all plans response status :" + planListResp.getStatus());
            }
            TypeReference<ArrayList<PlanBasicInfo>> ref = new TypeReference<ArrayList<PlanBasicInfo>>() {};
            ArrayList<PlanBasicInfo> planList = mapper.readValue(content, ref);
            return planList;
        } catch (IOException e) {
            throw new PartnerException(e);
        }

    }

    public Plan getPlanInfo(String planRef) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.PLAN_URL + "/" + planRef);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response planInfoResp = PartnerWsUtils.get(headers);
            String content = planInfoResp.readEntity(String.class);
            System.out.println("planInfo ::: " + content);
            if (planInfoResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                planInfoResp = PartnerWsUtils.get(headers);
            }
            if (planInfoResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on get planinfo response status :" + planInfoResp.getStatus());
            }
            TypeReference<Plan> ref = new TypeReference<Plan>() {};
            Plan planInfo = mapper.readValue(content, ref);
            return planInfo;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public FileReponse downloadInputTemplate(String planRef, String templateID) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.PLAN_URL + "/" + planRef + "/templates/" + templateID);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response fileResp = PartnerWsUtils.get(headers);
            String content = fileResp.readEntity(String.class);
            System.out.println("fileResp ::: " + content);
            if (fileResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                fileResp = PartnerWsUtils.get(headers);
            }
            if (fileResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on download input template response status :" + fileResp.getStatus());
            }
            TypeReference<FileReponse> ref = new TypeReference<FileReponse>() {};
            FileReponse templateFile = mapper.readValue(content, ref);
            return templateFile;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    // Control groups
    public ControlGroupResponse createControlGroup(ControlGroup controlGroup) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            String controlGroupStr = mapper.writeValueAsString(controlGroup);
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response controlGroupResp = PartnerWsUtils.post(controlGroupStr, headers);
            String content = controlGroupResp.readEntity(String.class);
            System.out.println("controlGroupResp ::: " + content);
            if (controlGroupResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                controlGroupResp = PartnerWsUtils.post(controlGroupStr, headers);
            }
            if (controlGroupResp.getStatus() != HttpStatus.CREATED.value()) {
                throw new PartnerException(
                        "Error on create control group response status:" + controlGroupResp.getStatus());
            }
            TypeReference<ControlGroupResponse> ref = new TypeReference<ControlGroupResponse>() {};
            ControlGroupResponse controlGroupRes = mapper.readValue(content, ref);
            return controlGroupRes;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public ControlGroupResponse updateControlGroup(String controlGroupRef, ControlGroup controlGroup) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            String controlGroupStr = mapper.writeValueAsString(controlGroup);
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL + "/" + controlGroupRef);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response controlGroupResp = PartnerWsUtils.put(controlGroupStr, headers);
            String content = controlGroupResp.readEntity(String.class);
            System.out.println("controlGroupResp ::: " + content);
            if (controlGroupResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                controlGroupResp = PartnerWsUtils.put(controlGroupStr, headers);
            }
            if (controlGroupResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException(
                        "Error on update control group response status:" + controlGroupResp.getStatus());
            }
            TypeReference<ControlGroupResponse> ref = new TypeReference<ControlGroupResponse>() {};
            ControlGroupResponse controlGroupRes = mapper.readValue(content, ref);
            return controlGroupRes;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public ControlGroup getControlGroupInfo(String controlGroupRef) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL + "/" + controlGroupRef);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response controlGroupInfoResp = PartnerWsUtils.get(headers);
            String content = controlGroupInfoResp.readEntity(String.class);
            System.out.println("controlGroupInfoResp ::: " + content);
            if (controlGroupInfoResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                controlGroupInfoResp = PartnerWsUtils.get(headers);
            }
            if (controlGroupInfoResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException(
                        "Error on get control group info response status:" + controlGroupInfoResp.getStatus());
            }
            TypeReference<ControlGroup> ref = new TypeReference<ControlGroup>() {};
            ControlGroup controlGroupInfo = mapper.readValue(content, ref);
            return controlGroupInfo;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public List<ControlGroup> getAllControlGroupsOfCustomer() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response controlGroupListResp = PartnerWsUtils.get(headers);
            String content = controlGroupListResp.readEntity(String.class);
            System.out.println("controlGroupListResp ::: " + content);
            if (controlGroupListResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                controlGroupListResp = PartnerWsUtils.get(headers);
            }
            if (controlGroupListResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on get all control group of customer response status:"
                        + controlGroupListResp.getStatus());
            }
            TypeReference<List<ControlGroup>> ref = new TypeReference<List<ControlGroup>>() {};
            List<ControlGroup> controlGroupList = mapper.readValue(content, ref);
            return controlGroupList;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    // Apps
    public AppResponse createApp(App app) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            String appStr = mapper.writeValueAsString(app);
            setResourceUrl(PartnerConstants.APP_URL);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response appResp = PartnerWsUtils.post(appStr, headers);
            String content = appResp.readEntity(String.class);
            System.out.println("appResp ::: " + content);
            if (appResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                appResp = PartnerWsUtils.post(appStr, headers);
            }
            if (appResp.getStatus() != HttpStatus.CREATED.value()) {
                throw new PartnerException("Error on create app response status:" + appResp.getStatus());
            }
            TypeReference<AppResponse> ref = new TypeReference<AppResponse>() {};
            AppResponse appResponse = mapper.readValue(content, ref);
            return appResponse;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public App getAppInfo(String customerAppRef) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.APP_URL + "/" + customerAppRef);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response appInfoResp = PartnerWsUtils.get(headers);
            String content = appInfoResp.readEntity(String.class);
            System.out.println("appInfoResp ::: " + content);
            if (appInfoResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                appInfoResp = PartnerWsUtils.get(headers);
            }
            if (appInfoResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on get appInfo response status:" + appInfoResp.getStatus());
            }
            TypeReference<App> ref = new TypeReference<App>() {};
            App appResponse = mapper.readValue(content, ref);
            return appResponse;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public List<App> getAllAppsOfCustomer(String type) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (type.isEmpty()) {
                setResourceUrl(PartnerConstants.APP_URL);
            } else {
                setResourceUrl(PartnerConstants.APP_URL + "?type=" + type);
            }
            MultivaluedMap<String, Object> headers = setHeaders();
            Response appsResp = PartnerWsUtils.get(headers);
            String content = appsResp.readEntity(String.class);
            System.out.println("appsResp ::: " + content);
            if (appsResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                appsResp = PartnerWsUtils.get(headers);
            }
            if (appsResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on get app of customer response status:" + appsResp.getStatus());
            }
            TypeReference<List<App>> ref = new TypeReference<List<App>>() {};
            List<App> apps = mapper.readValue(content, ref);
            return apps;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    // Runs
    public RunResponse runControlGroup(String controlGroupRef, Run run) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(JsonMethod.FIELD, Visibility.ANY);
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, true);
            mapper.getSerializationConfig().withSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);
            String appStr = mapper.writeValueAsString(run);
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL + "/" + controlGroupRef + "/runs");
            MultivaluedMap<String, Object> headers = setHeaders();
            Response runResp = PartnerWsUtils.post(appStr, headers);
            String content = runResp.readEntity(String.class);
            System.out.println("runResp ::: " + content);
            if (runResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                runResp = PartnerWsUtils.post(appStr, headers);
            }
            if (runResp.getStatus() != HttpStatus.CREATED.value()) {
                throw new PartnerException("Error on run control group response status:" + runResp.getStatus());
            }
            TypeReference<RunResponse> ref = new TypeReference<RunResponse>() {};
            RunResponse runResponse = mapper.readValue(content, ref);
            return runResponse;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public Run getRunInfo(String controlGroupRef, String runID) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL + "/" + controlGroupRef + "/runs/" + runID);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response runResp = PartnerWsUtils.get(headers);
            String content = runResp.readEntity(String.class);
            System.out.println("runResp ::: " + content);
            if (runResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                runResp = PartnerWsUtils.get(headers);
            }
            if (runResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException("Error on get run info response status :" + runResp.getStatus());
            }
            TypeReference<Run> ref = new TypeReference<Run>() {};
            Run run = mapper.readValue(content, ref);
            return run;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }

    public FileReponse downloadOutputFile(String controlGroupRef, String runID, String downloadHash) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            setResourceUrl(PartnerConstants.CONTROL_GROUP_URL + "/" + controlGroupRef + "/runs/" + runID + "/downloads/"
                    + downloadHash);
            MultivaluedMap<String, Object> headers = setHeaders();
            Response outputFileResp = PartnerWsUtils.get(headers);
            String content = outputFileResp.readEntity(String.class);
            System.out.println("outputFileResp ::: " + content);
            if (outputFileResp.getStatus() == HttpStatus.UNAUTHORIZED.value()) {
                createInstance(clientId, clientSecret);
                outputFileResp = PartnerWsUtils.get(headers);
            }
            if (outputFileResp.getStatus() != HttpStatus.OK.value()) {
                throw new PartnerException(
                        "Error on download output file response status :" + outputFileResp.getStatus());
            }
            TypeReference<FileReponse> ref = new TypeReference<FileReponse>() {};
            FileReponse outputFile = mapper.readValue(content, ref);
            return outputFile;
        } catch (IOException e) {
            throw new PartnerException(e);
        }
    }
}
