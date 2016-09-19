package com.stormpath.sdk.servlet.mvc;

import com.stormpath.sdk.servlet.filter.ContentNegotiationResolver;
import com.stormpath.sdk.servlet.http.MediaType;
import com.stormpath.sdk.servlet.http.UnresolvedMediaTypeException;
import com.stormpath.sdk.servlet.http.UserAgent;
import com.stormpath.sdk.servlet.http.UserAgents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 1.0.RC8
 */
public class ContentNegotiatingFieldValueResolver implements RequestFieldValueResolver {

    private static final Logger log = LoggerFactory.getLogger(ContentNegotiatingFieldValueResolver.class);

    private final RequestFieldValueResolver JSON_BODY_RESOLVER = new JacksonFieldValueResolver();
    private final RequestFieldValueResolver REQ_PARAM_RESOLVER = new RequestParameterFieldValueResolver();

    protected List<MediaType> produces;
    private ContentNegotiationResolver contentNegotiationResolver = ContentNegotiationResolver.INSTANCE;

    public void setProduces(List<MediaType> produces) {
        this.produces = produces;
    }

    @Override
    public String getValue(HttpServletRequest request, String fieldName) {
        if (isJsonPreferred(request)) {
            return JSON_BODY_RESOLVER.getValue(request, fieldName);
        }

        return REQ_PARAM_RESOLVER.getValue(request, fieldName);
    }

    @Override
    public Map<String, Object> getAllFields(HttpServletRequest request) {
        if (isJsonPreferred(request)) {
            return JSON_BODY_RESOLVER.getAllFields(request);
        }

        return REQ_PARAM_RESOLVER.getAllFields(request);
    }

    protected boolean isJsonPreferred(HttpServletRequest request) {
        try {
            return MediaType.APPLICATION_JSON.equals(contentNegotiationResolver.getContentType(request, null, produces));
        } catch (UnresolvedMediaTypeException e) {
            log.debug("isJsonPreferred: Couldn't resolve content type: {}", e.getMessage());
            return false;
        }
    }
}
