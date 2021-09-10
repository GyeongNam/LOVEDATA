package com.project.love_data.util;

import lombok.extern.log4j.Log4j2;

import javax.servlet.http.HttpServletRequest;

@Log4j2
public class ServerHostHandler {
    public static ServerDomain getServerHost(HttpServletRequest request) {
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();

        switch (serverName) {
            case "localhost":
                return ServerDomain.LOCALHOST;
            case "mon0mon.iptime.org":
                if (serverPort == 28080) {
                    return ServerDomain.MON_IPTIME_2;
                } else {
                    return ServerDomain.MON_IPTIME;
                }
            case "lovedata.ddns.net":
                return ServerDomain.LOVEDATA_DDNS;
            case "lovedata.duckdns.org":
                return ServerDomain.LOVEDATA_DUCK;
            case "lovedata.kr":
                return ServerDomain.LOVEDATA_KR;
            default:
                log.warn("Not Defined ServerName");
                log.warn("Plaese Add Current Domain Name");
                return null;
        }
    }
}
