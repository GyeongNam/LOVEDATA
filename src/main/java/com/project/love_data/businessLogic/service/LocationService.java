package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationDTO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class LocationService {

    public Location dtoToEntity(LocationDTO dto) {
        Location entity = Location.builder()
                .loc_name(dto.getLoc_name())
                .user_no(dto.getUser_no())
                .roadAddr(dto.getRoadAddr())
                .addrDetail(dto.getAddrDetail())
                .siDo(dto.getSiDo())
                .siGunGu(dto.getSiGunGu())
                .info(dto.getInfo())
                .loc_uuid(dto.getLoc_uuid())
                .tel(dto.getTel())
                .tagSet(dto.getTagSet())
                .build();

        return entity;
    }

    public LocationDTO getLocationDTO(Map<String, String> reqParam, List<String> tagList) {

        LocationDTO loc = LocationDTO.builder()
                .loc_name(reqParam.get("name"))
                .user_no(Long.valueOf(reqParam.get("user_no")))
                .roadAddr(reqParam.get("roadAddr"))
                .addrDetail(reqParam.get("addrDetail"))
                .siDo(reqParam.get("siDoName"))
                .siGunGu(reqParam.get("siGunGuName"))
                .info(reqParam.get("info"))
                .tel(reqParam.get("tel"))
                .build();

        for (String item : tagList) {
            loc.addLocTag(item);
        }

        System.out.println("loc = " + loc);

        return loc;
    }
}
