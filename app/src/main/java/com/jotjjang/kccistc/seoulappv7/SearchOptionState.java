package com.jotjjang.kccistc.seoulappv7;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SearchOptionState {

    public enum TopicState {
        TOPIC_STATE_HOTCLIP,
        TOPIC_STATE_NEWS,
        TOPIC_STATE_SPORTS,
        TOPIC_STATE_HUMOR
    }

    public enum DateState {
        DATE_STATE_TODAY,
        DATE_STATE_WEEK,
        DATE_STATE_MONTH,
        DATE_STATE_YEAR,
        DATE_STATE_ALL
    }
    public enum OrderState {
        ORDER_STATE_RELEVANCE,
        ORDER_STATE_VIEWCOUNT,
        ORDER_STATE_RATING
    }

    static  private SearchOptionState.TopicState topicState = TopicState.TOPIC_STATE_HOTCLIP;
    static private SearchOptionState.DateState dateState = DateState.DATE_STATE_TODAY;
    static private SearchOptionState.OrderState orderState = OrderState.ORDER_STATE_RELEVANCE;

    private static Date currentDate;
    private static String currentDateString;
    private static String aDayAgoDateString;
    private static String aWeekAgoDateString;
    private static String aMonthAgoDateString;
    private static String aYearAgoDateString;
    private static String longTimeAgoDateString;

    private static String nextPageToken;

    public static void setDateTimeForRequest()
    {
        long now = System.currentTimeMillis();
        currentDate = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        currentDateString = sdf.format(currentDate)+"Z";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -1);
        aDayAgoDateString = sdf.format(calendar.getTime())+"Z";
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -7);
        aWeekAgoDateString = sdf.format(calendar.getTime())+"Z";
        calendar.setTime(currentDate);
        calendar.add(Calendar.MONTH, -1);
        aMonthAgoDateString = sdf.format(calendar.getTime())+"Z";
        calendar.setTime(currentDate);
        calendar.add(Calendar.YEAR, -1);
        aYearAgoDateString = sdf.format(calendar.getTime())+"Z";
        longTimeAgoDateString = "1970-01-01T00:00:00Z";
    }

    public static TopicState getTopicState() {
        return topicState;
    }

    public static void setTopicState(TopicState topicState) {
        SearchOptionState.topicState = topicState;
    }

    public static String getTopicStateString() {
        if (topicState == TopicState.TOPIC_STATE_HOTCLIP) {
            return "서울";
        } else if (topicState == TopicState.TOPIC_STATE_NEWS) {
            return "뉴스";
        } else if (topicState == TopicState.TOPIC_STATE_SPORTS) {
            return "스포츠";
        } else if (topicState == TopicState.TOPIC_STATE_HUMOR) {
            return "웃긴영상";
        } else {
            return "";
        }
    }

    public static DateState getDateState() {
        return dateState;
    }

    public static void setDateState(DateState dateState) {
        SearchOptionState.dateState = dateState;
    }

    public static String getBeforeDateStateString() {
        if(dateState == DateState.DATE_STATE_TODAY)
        {
            return currentDateString;
        } else if (dateState == DateState.DATE_STATE_WEEK)
        {
            return aDayAgoDateString;
        } else if (dateState == DateState.DATE_STATE_MONTH)
        {
            return aWeekAgoDateString;
        } else if (dateState == DateState.DATE_STATE_YEAR)
        {
            return aMonthAgoDateString;
        } else if (dateState == DateState.DATE_STATE_ALL)
        {
            return currentDateString;
        } else
        {
            return "";
        }
    }

    public static String getAfterDateStateString() {
        if(dateState == DateState.DATE_STATE_TODAY)
        {
            return aDayAgoDateString;
        } else if (dateState == DateState.DATE_STATE_WEEK)
        {
            return aWeekAgoDateString;
        } else if (dateState == DateState.DATE_STATE_MONTH)
        {
            return aMonthAgoDateString;
        } else if (dateState == DateState.DATE_STATE_YEAR)
        {
            return aYearAgoDateString;
        } else if (dateState == DateState.DATE_STATE_ALL)
        {
            return longTimeAgoDateString;
        } else
        {
            return "";
        }
    }

    public static OrderState getOrderState() {
        return orderState;
    }

    public static void setOrderState(OrderState orderState) {
        SearchOptionState.orderState = orderState;
    }

    public static String getOrderStateString() {
        if (orderState == OrderState.ORDER_STATE_RELEVANCE) {
            return "relevance";
        } else if (orderState == OrderState.ORDER_STATE_VIEWCOUNT) {
            return "viewCount";
        } else if (orderState == OrderState.ORDER_STATE_RATING) {
            return "rating";
        } else {
            return "";
        }
    }

    public static String getNextPageToken() {
        return nextPageToken;
    }

    public static void setNextPageToken(String nextPageToken) {
        SearchOptionState.nextPageToken = nextPageToken;
    }
}
