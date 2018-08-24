package com.jotjjang.kccistc.seoulappv7;

public class OptionSettingState {
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

    static private DateState dateState = DateState.DATE_STATE_TODAY;
    static private OrderState orderState = OrderState.ORDER_STATE_RELEVANCE;

    public static DateState getDateState() {
        return dateState;
    }

    public static void setDateState(DateState dateState) {
        OptionSettingState.dateState = dateState;
    }

    public static OrderState getOrderState() {
        return orderState;
    }

    public static void setOrderState(OrderState orderState) {
        OptionSettingState.orderState = orderState;
    }
}
