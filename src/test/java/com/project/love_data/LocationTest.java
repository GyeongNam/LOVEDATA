package com.project.love_data;

import com.project.love_data.model.resource.Image;
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

        System.out.println("Location 저장 완료");
    }

    @Test
    public void testLocTag_Read() {
        Optional<Location> loc = locationRepository.findLocByLoc_no(1L);

        Location temp = loc.get();

        System.out.println(temp);
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
        List<Location> list = locationRepository.findLocByAddr("Addr");

        for (Location location : list) {
            System.out.println("location = " + location);
        }
    }
}
