package com.jotjjang.kccistc.seoulappv7;

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

    public static TopicState getTopicState() {
        return topicState;
    }

    public static void setTopicState(TopicState topicState) {
        SearchOptionState.topicState = topicState;
    }

    public static String getTopicStateString() {
        if (topicState == TopicState.TOPIC_STATE_HOTCLIP) {
            return "서울+화제의+영상+인기";
        } else if (topicState == TopicState.TOPIC_STATE_NEWS) {
            return "서울+뉴스";
        } else if (topicState == TopicState.TOPIC_STATE_SPORTS) {
            return "서울+스포츠";
        } else if (topicState == TopicState.TOPIC_STATE_HUMOR) {
            return "서울+웃긴영상";
        } else {
            return "서울기술교육센터";
        }
    }

    public static DateState getDateState() {
        return dateState;
    }

    public static void setDateState(DateState dateState) {
        SearchOptionState.dateState = dateState;
    }

    public static void getBeforeDateStateString() {
        if(dateState == DateState.DATE_STATE_TODAY)
        {

        }
    }

    public static void getAfterDateStateString() {
        if(dateState == DateState.DATE_STATE_TODAY)
        {

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
}
