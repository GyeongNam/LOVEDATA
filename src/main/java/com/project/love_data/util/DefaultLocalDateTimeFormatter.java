package com.project.love_data.util;

import java.time.format.DateTimeFormatter;

public class DefaultLocalDateTimeFormatter {
    private DateTimeFormatter dateTimeFormatter;

    public DefaultLocalDateTimeFormatter() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
}
