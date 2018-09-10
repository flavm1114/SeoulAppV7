package com.jotjjang.kccistc.seoulappv7;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class CommentEntry {
    private String commentId;
    private String authorName;
    private String authorProfileImageUrl;
    private String commentText;
    private String videoId;
    private int likeCount;
    private String publishedAt;
    private boolean canReply;
    private int totalReplyCount;
    private boolean isPublic;
    private ArrayList<CommentEntry> repliesList;

    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private final long curLong = System.currentTimeMillis();

    private String publishedDateString;

    public CommentEntry(String commentId, String authorName
            , String authorProfileImageUrl, String commentText
            , String videoId, int likeCount, String publishedAt
            , boolean canReply, int totalReplyCount
            , boolean isPublic) {
        this.commentId = commentId;
        this.authorName = authorName;
        this.authorProfileImageUrl = authorProfileImageUrl;
        this.commentText = commentText;
        this.videoId = videoId;
        this.likeCount = likeCount;
        this.publishedAt = publishedAt;
        this.canReply = canReply;
        this.totalReplyCount = totalReplyCount;
        this.isPublic = isPublic;
        this.repliesList = new ArrayList<>();
        try {
            this.publishedDateString = convertToStringPublishedDate(publishedAt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorProfileImageUrl() {
        return authorProfileImageUrl;
    }

    public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
        this.authorProfileImageUrl = authorProfileImageUrl;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public boolean isCanReply() {
        return canReply;
    }

    public void setCanReply(boolean canReply) {
        this.canReply = canReply;
    }

    public int getTotalReplyCount() {
        return totalReplyCount;
    }

    public void setTotalReplyCount(int totalReplyCount) {
        this.totalReplyCount = totalReplyCount;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public ArrayList<CommentEntry> getRepliesList() {
        return repliesList;
    }

    public String getPublishedDateString() {
        return publishedDateString;
    }

    private String convertToStringPublishedDate(String dateString) throws ParseException {
        Date registDate = simpleDateFormat.parse(dateString);
        long regTime = registDate.getTime();
        long diffTime = (curLong - regTime) / 1000;

        String msg = "x";
        if(diffTime < TIME_MAXIMUM_COMMENT.SEC) {
            // sec
            msg = diffTime + "초전";
        } else if ((diffTime /= TIME_MAXIMUM_COMMENT.SEC) < TIME_MAXIMUM_COMMENT.MIN) {
            // min
            msg = diffTime + "분전";
        } else if ((diffTime /= TIME_MAXIMUM_COMMENT.MIN) < TIME_MAXIMUM_COMMENT.HOUR) {
            // hour
            msg = (diffTime ) + "시간전";
        } else if ((diffTime /= TIME_MAXIMUM_COMMENT.HOUR) < TIME_MAXIMUM_COMMENT.DAY) {
            // day
            msg = (diffTime ) + "일전";
        } else if ((diffTime /= TIME_MAXIMUM_COMMENT.DAY) < TIME_MAXIMUM_COMMENT.MONTH) {
            // day
            msg = (diffTime ) + "달전";
        } else {
            diffTime /= TIME_MAXIMUM_COMMENT.MONTH;
            msg = (diffTime) + "년전";
        }
        return msg;
    }

    private static class TIME_MAXIMUM_COMMENT {
        public static final int SEC = 60;
        public static final int MIN = 60;
        public static final int HOUR = 24;
        public static final int DAY = 30;
        public static final int MONTH = 12;
    }
}
