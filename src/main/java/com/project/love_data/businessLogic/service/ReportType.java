package com.project.love_data.businessLogic.service;

public enum ReportType {
    ADVERTISE("광고성 게시물"),
    PORNOGRAPHY("청소년 유해물 혹은 음란물이 포함된 게시물"),
    ILLEGAL("불법 정보가 포함된 게시물"),
    INSULT("욕설 혹은 혐오발언 게시물"),
    PERSONAL_INFO("개인정보가 노출된 게시물"),
    ETC("기타");

    final private String name;
    ReportType(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public static ReportType nameof(String name) {
        for (ReportType value : ReportType.values()) {
            if (value.getName().equals(name)) {
                return value;
            }
        }
        return null;
    }
}
