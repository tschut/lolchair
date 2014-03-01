package com.lolchair.lolchair;

import org.androidannotations.annotations.rest.Post;
import org.androidannotations.annotations.rest.Rest;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;

@Rest(rootUrl = "http://nas.tiemenschut.com:7070/lolchair-server/lolchair", converters = { StringHttpMessageConverter.class,
        ByteArrayHttpMessageConverter.class, })
public interface LolchairServerClient {
    @Post("/submit?message={message}")
    String submit(String message, byte[] file);
}
