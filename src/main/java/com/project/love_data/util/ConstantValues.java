package com.project.love_data.util;

import java.util.*;

public class ConstantValues {
    public final static int MAX_COR_LIST_SIZE = 4;
    public final static int MAX_LOC_LIST_SIZE = 4;
    public final static int MAX_COM_COUNT = 5;
//    public final static int MAX_REV_COUNT = 1;
    public final static int MAX_REV_COUNT = 5;
    public final static int MAX_UPLOAD_COUNT = 10;
    public final static int MIN_UPLOAD_COUNT = 3;
    public final static int REV_MIN_UPLOAD_COUNT = 0;
    public final static int REV_MAX_UPLOAD_COUNT = 3;
    public final static int MAX_LOCATION_HISTORY_COUNT = 10;
    public final static String Linux_Image_Upload_Path = "/opt/apache-tomcat-9.0.46/webapps/love_data/WEB-INF/classes/static/image/";
    public final static Map<String, String> reportTypeMapEN2KR = new HashMap<String, String>() {{
        put("ADVERTISE", "광고성 게시물");
        put("PORNOGRAPHY", "청소년 유해물 혹은 음란물이 포함된 게시물");
        put("ILLEGAL", "불법 정보가 포함된 게시물");
        put("INSULT", "욕설 혹은 혐오발언 게시물");
        put("PERSONAL_INFO", "개인정보가 노출된 게시물");
        put("ETC", "기타");
    }};
    public final static Map<String, String> reportTypeMapKR2EN = new HashMap<String, String>() {{
        put("광고성 게시물", "ADVERTISE");
        put("청소년 유해물 혹은 음란물이 포함된 게시물", "PORNOGRAPHY");
        put("불법 정보가 포함된 게시물", "ILLEGAL");
        put("욕설 혹은 혐오발언", "INSULT");
        put("개인정보가 노출된 게시물", "PERSONAL_INFO");
        put("기타", "ETC");
    }};
}
