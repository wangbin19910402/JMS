package com.wangbin.project.web.controller;

import com.wangbin.project.web.Response;
import org.codehaus.jackson.Version;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.module.SimpleModule;

/**
 * @author Chen Wei
 */
public abstract class AbstractController {

    protected static final ObjectMapper JSON_MAPPER;

    static {
        JSON_MAPPER = new ObjectMapper();
        Version version = new Version(1, 0, 0, null);
        SimpleModule module = new SimpleModule("ChushouApiModule", version);
    }

    protected String toJsonResponse(Response response) {
        try {
            return JSON_MAPPER.writeValueAsString(response);
        } catch (Exception e) {
            Response<Object> errorRes = new Response<>();
            errorRes.setCode(500);
            errorRes.setMessage("data error");
            try {
                return JSON_MAPPER.writeValueAsString(errorRes);
            } catch (Exception e1) {
                throw new IllegalStateException("Check the jackson ObjectMapper configuration.", e1);
            }
        }
    }
}
