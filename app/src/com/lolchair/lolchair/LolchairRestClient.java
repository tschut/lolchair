package com.lolchair.lolchair;

import org.androidannotations.annotations.rest.Get;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.json.GsonHttpMessageConverter;

@Rest(rootUrl = "http://www.lolchair.com/api", converters = GsonHttpMessageConverter.class)
public interface LolchairRestClient {
    @Get("/get_recent_posts/?count={count}&page={page}")
    PostsReply getRecentPosts(int count, int page);
}
