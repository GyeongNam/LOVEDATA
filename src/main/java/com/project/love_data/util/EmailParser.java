package com.project.love_data.util;

import java.util.*;

public class EmailParser {
    public List<String> emailParser(String email) {
        StringTokenizer strToken = null;
        List<String> strHoler = new ArrayList<>();

        if (email == null) {
            return null;
        }

        strToken = new StringTokenizer(email, "@");

        while (strToken.hasMoreElements()){
            strHoler.add((String) strToken.nextElement());
        }

        return strHoler;
    }
}
