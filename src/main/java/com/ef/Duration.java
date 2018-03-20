package com.ef;

enum Duration {

    HOURLY("hourly", "HOUR"),
    DAILY("daily", "DAY");

    public final String desc;
    public final String interval;

    Duration(String desc, String interval) {
        this.desc = desc;
        this.interval = interval;
    }

}