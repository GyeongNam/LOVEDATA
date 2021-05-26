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
                    .user_no(new Random().nextLong())
                    .roadAddr("Addr_" + pad)
                    .addrDetail("AddrDetail_" + pad)
                    .siDo("siDo_" + pad)
                    .siGunGu(pad)
                    .info("This locaiton is Generated randomly " + pad)
                    .build();

            if (i % 2 == 0) {
                loc.addLocTag(tagSet.get(0).toString());
                loc.addLocTag(tagSet.get(2).toString());
            } else {
                loc.addLocTag(tagSet.get(1).toString());
                loc.addLocTag(tagSet.get(3).toString());
            }

//            loc.addLocImg("Img_" + pad);

            Image img = Image.builder()
                    .img_url("path/" + i)
                    .user_no(Long.valueOf(i))
                    .loc_no(Long.valueOf(i))
                    .img_uuid("uuid_" + i)
                    .build();

            imageRepository.save(img);

            locationRepository.save(loc);
        }

        System.out.println("Location 저장 완료");
    }

    @Test
    public void testLocTag_Read() {
        Optional<Location> loc = locationRepository.findLoc_TagByName("Location_0000");

        Location temp = loc.get();

        System.out.println(temp.getTagSet());
    }

    @Test
    public void testLocImg_Read() {
//        Optional<Location> loc = locationRepository.findLoc_ImgByName("asdfasdf asdf");
        Optional<Location> loc = locationRepository.findLocByName("loc_1");

        Location temp = loc.get();

        loc = locationRepository.findLoc_ImgByNo(temp.getLoc_no());

        System.out.println(temp.getImgList());
    }
}
