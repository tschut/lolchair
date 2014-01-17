package com.lolchair.lolchair;

import java.net.URL;

public class Post {
    class ImageResource {
        URL url;
    }

    class Attachment {
        ImageResource full;
    }

    String     title;
    Attachment thumbnail_images;

}
