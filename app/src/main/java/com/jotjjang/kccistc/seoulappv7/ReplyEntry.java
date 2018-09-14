package com.jotjjang.kccistc.seoulappv7;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ReplyEntry {
    private String replyId;
    private String authorName;
    private String authorProfileImageUrl;
    private String commentText;
    private String parentId;
    private String publishedAt;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final long curLong = System.currentTimeMillis();

    private String publishedDateString;

    public ReplyEntry(String replyId, String authorName
            , String authorProfileImageUrl, String commentText
            , String parentId, String publishedAt) {
        this.replyId = replyId;
        this.authorName = authorName;
        this.authorProfileImageUrl = authorProfileImageUrl;
        this.commentText = commentText;
        this.parentId = parentId;
        this.publishedAt = publishedAt;
        try {
            this.publishedDateString = convertToStringPublishedDate(publishedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getReplyId() {
        return replyId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public String getCommentText() {
        return commentText;
    }

    public String getParentId() {
        return parentId;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public String getPublishedDateString() {
        return publishedDateString;
    }

    private String convertToStringPublishedDate(String dateString) throws ParseException {
        Date registDate = simpleDateFormat.parse(dateString);
        long regTime = registDate.getTime();
        long diffTime = (curLong - regTime) / 1000;

        String msg = "x";
        if(diffTime < TimeMaximum.SEC) {
            // sec
            msg = diffTime + "초전";
        } else if ((diffTime /= TimeMaximum.SEC) < TimeMaximum.MIN) {
            // min
            msg = diffTime + "분전";
        } else if ((diffTime /= TimeMaximum.MIN) < TimeMaximum.HOUR) {
            // hour
            msg = (diffTime ) + "시간전";
        } else if ((diffTime /= TimeMaximum.HOUR) < TimeMaximum.DAY) {
            // day
            msg = (diffTime ) + "일전";
        } else if ((diffTime /= TimeMaximum.DAY) < TimeMaximum.MONTH) {
            // day
            msg = (diffTime ) + "달전";
        } else {
            diffTime /= TimeMaximum.MONTH;
            msg = (diffTime) + "년전";
        }
        return msg;
    }
}
