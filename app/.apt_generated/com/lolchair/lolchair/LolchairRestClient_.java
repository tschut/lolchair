//
// DO NOT EDIT THIS FILE, IT HAS BEEN GENERATED USING AndroidAnnotations 3.0.1.
//


package com.lolchair.lolchair;

import java.util.HashMap;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public final class LolchairRestClient_
    implements LolchairRestClient
{

    private String rootUrl;
    private RestTemplate restTemplate;
    private RestErrorHandler restErrorHandler;

    public LolchairRestClient_() {
        rootUrl = "http://www.lolchair.com/api";
        restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());
    }

    @Override
    public PostsReply getRecentPosts(int count, int page) {
        HashMap<String, Object> urlVariables = new HashMap<String, Object>();
        urlVariables.put("count", count);
        urlVariables.put("page", page);
        try {
            return restTemplate.exchange(rootUrl.concat("/get_recent_posts/?count={count}&page={page}"), HttpMethod.GET, null, PostsReply.class, urlVariables).getBody();
        } catch (RestClientException e) {
            if (restErrorHandler!= null) {
                restErrorHandler.onRestClientExceptionThrown(e);
                return null;
            } else {
                throw e;
            }
        }
    }

}
