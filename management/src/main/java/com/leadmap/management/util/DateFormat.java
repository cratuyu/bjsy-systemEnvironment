package com.leadmap.management.util;

public enum DateFormat {

    DateFull("yyyy-MM-dd HH:mm:ss"),
    DateSignYMH("yyyy-MM-dd");

    private String formatString;

    private DateFormat(String formatString) {
        this.formatString = formatString;
    }


    public String getFormatString() {
        return formatString;
    }

    public void setFormatString(String formatString) {
        this.formatString = formatString;
    }
}
