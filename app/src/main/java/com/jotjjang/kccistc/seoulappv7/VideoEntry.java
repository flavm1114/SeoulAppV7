package com.jotjjang.kccistc.seoulappv7;

import java.io.Serializable;

public class VideoEntry implements Serializable{
    private final String title;
    private final String videoId;
    //private final String etag;
    //private final String channelId;
    private final String publishedDate;
    private final String description;
    private final String channelTitle;
    private final String thumbnailUrl_default;
    //private final String thumbnailUrl_medium;
    //private final String thumbnailUrl_high;
    //private final String liveBroadcastContent;

    public VideoEntry(String title, String videoId, String publishedDate,
                      String description, String channelTitle, String thumbnailUrl_default)
    {
        this.title = title;
        this.videoId = videoId;
        this.publishedDate = publishedDate;
        this.description = description;
        this.channelTitle = channelTitle;
        this.thumbnailUrl_default = thumbnailUrl_default;
    }

    public String getThumbnailUrl_default() {
        return thumbnailUrl_default;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public String getChannelTitle() {
        return channelTitle;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getTitle() {
        return title;
    }
}
