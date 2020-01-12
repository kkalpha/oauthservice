package com.tech.oauth.service.util;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JSONResponseUtil {

    private static final Logger logger = LoggerFactory.getLogger(JSONResponseUtil.class);

    protected static JsonFactory factory = new ObjectMapper().getFactory();

    public static void writeJsonData(Object data, HttpServletResponse resp) throws IOException {

        JsonGenerator responseJsonGenerator = factory.createGenerator(resp.getOutputStream(), JsonEncoding.UTF8);
        responseJsonGenerator.writeObject(data);

    }




}
