package com.project.love_data;

import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.businessLogic.service.SearchOption;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.repository.ImageRepository;
import com.project.love_data.repository.LocationRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
public class LocationTest {
    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    LocationService locService;

    @Test
    public void InsertLocation() {
        List<LocationTag> tagSet = new ArrayList<>();
        tagSet.add(LocationTag.TYPE_ODD);
        tagSet.add(LocationTag.TYPE_EVEN);
        tagSet.add(LocationTag.ACTION_A);
        tagSet.add(LocationTag.ACTION_B);

        for (int i = 0; i < 10; i++) {
            String pad = StringUtils.leftPad(Integer.toString(i), 4, '0');
            Location loc = Location.builder()
                    .loc_name("Location_" + pad)
                    .user_no((long) new Random().nextInt(4))
                    .roadAddr("Addr_" + pad)
                    .addrDetail("AddrDetail_" + pad)
                    .siDo("siDo_" + pad)
                    .siGunGu(pad)
                    .info("This locaiton is Generated randomly " + pad)
                    .loc_uuid("UUID_" + i)
                    .tel("010-0000-00" + StringUtils.leftPad(Integer.toString(i), 2, '0'))
                    .zipNo(String.valueOf(i))
                    .build();

            if (i % 2 == 0) {
                loc.addLocTag(tagSet.get(0).toString());
                loc.addLocTag(tagSet.get(2).toString());
            } else {
                loc.addLocTag(tagSet.get(1).toString());
                loc.addLocTag(tagSet.get(3).toString());
            }

            locationRepository.save(loc);
        }

        Location loc = Location.builder()
                .loc_name("Location_0001")
                .user_no((long) new Random().nextInt(4))
                .roadAddr("Addr_0001")
                .addrDetail("AddrDetail_0001")
                .siDo("siDo_0001")
                .siGunGu("0001")
                .info("This locaiton is Generated randomly duplicated")
                .loc_uuid("UUID_0010")
                .tel("010-0000-0001")
                .zipNo("00000")
                .build();

        loc.addLocTag(String.valueOf(tagSet.get(0)));
        loc.addLocTag(String.valueOf(tagSet.get(2)));

        locationRepository.save(loc);

        System.out.println("Location 저장 완료");
    }

    @Test
    public void testLocTag_Read() {
        Optional<Location> loc = locationRepository.findLocByLoc_no(1L);

        Location temp = loc.get();

        System.out.println(temp);
    }

    @Test
    public void testLocName_Read() {
        List<Location> list = locationRepository.findAllByName("Location_0001");

        for (Location location : list) {
            System.out.println("location = " + location);
        }
    }

    @Test
    public void testLoc_Update() {
        Optional<Location> loc = locationRepository.findLocByUUID("UUID_0");

        Location temp = loc.get();

        System.out.println("temp = " + temp);

        temp.setLoc_name("Updated Loc_0");
        temp.setInfo("Updated Location Info");

        locationRepository.save(temp);
    }

    @Test
    public void testLocUUID_Read() {
        Optional<Location> item = locationRepository.findLocByUUID("UUID_0");

        System.out.println("item = " + item.get());
    }

    @Test
    public void testLocAddr_Read() {
        List<Location> list = locationRepository.findByRoadAddrContaining("Addr");

        for (Location location : list) {
            System.out.println("location = " + location);
        }

        System.out.println("\n\n-------------------------\n\n");

        list = locationRepository.findAllByRoadAddrAndAddrDetail("Addr_000", "AddrDetail_0000");

        for (Location location : list) {
            System.out.println("location = " + location);
        }
    }

    @Test
    public void testLocContaining() {
        List<Location> list = locService.locationNameSearch("Loc", SearchOption.CONTAIN);
        int i = 0;

        for (Location location : list) {
            System.out.println("location " + (++i) + " = " + location);
        }
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(4)
                .build();

        PageResultDTO<LocationDTO, Location> resultDTO = locService.getList(pageRequestDTO);

        System.out.println("PREV = " + resultDTO.isPrev());
        System.out.println("NEXT = " + resultDTO.isNext());
        System.out.println("TOTAL : " + resultDTO.getTotalPage());

        System.out.println("-------------------------------------------------");
        for (LocationDTO locationDTO : resultDTO.getDtoList()) {
            System.out.println(locationDTO.getLoc_no() + "\tlocationDTO = " + locationDTO);
        }

        System.out.println("=================================================");
        List<Integer> temp = resultDTO.getPageList();
        resultDTO.getPageList().forEach(i -> System.out.println(i));
    }
}
