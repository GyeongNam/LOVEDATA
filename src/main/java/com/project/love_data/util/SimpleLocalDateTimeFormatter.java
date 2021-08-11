package com.project.love_data.util;

import java.time.format.DateTimeFormatter;

public class SimpleLocalDateTimeFormatter {
    private DateTimeFormatter dateTimeFormatter;

    public SimpleLocalDateTimeFormatter() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    }

    public DateTimeFormatter getDateTimeFormatter() {
        return dateTimeFormatter;
    }

    public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
        this.dateTimeFormatter = dateTimeFormatter;
    }
}
