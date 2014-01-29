package com.lolchair.lolchair;

import java.net.URL;

public class Post {
    class ImageResource {
        URL url;
    }

    class Attachment {
        ImageResource full;
    }

    class CustomFields {
        String[] rating;
        String[] votes;
    }

    String       title;
    Attachment   thumbnail_images;
    URL          url;
    CustomFields custom_fields;
}
