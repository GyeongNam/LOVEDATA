package com.project.love_data;

import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.businessLogic.service.SearchOption;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
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

    @Autowired
    LocationService locService;

    @Test
    public void InsertInitLocation() {
        List<LocationTag> tagSet = new ArrayList<>();
        tagSet.add(LocationTag.ACTION_A);
        tagSet.add(LocationTag.ACTION_B);

        Long user_no = (long) new Random().nextInt(4);

        Location loc = Location.builder()
                .loc_name("중부대학교 충청캠퍼스")
                .user_no(user_no)
                .roadAddr("충청남도 금산군 추부면 대학로 201")
                .addrDetail("충청캠퍼스")
                .siDo("충청남도")
                .siGunGu("금산군")
                .info("중부대학교는 학생성장을 학교 발전을 위한 최고의 비전으로 추구하고 있습니다." +
                        " 학생성장은 인공지능과 융복합 시대의 전공역량을 충분히 획득한 전문인재, 올바른 인성과 균형 잡힌 교양을 갖춘 바른 인재, 그리고 창의적인 문제해결력을 갖춘 창의인재로의 발전을 지향합니다")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("041-750-6500")
                .zipNo("32713")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.학교));
        loc.addLocTag(String.valueOf(LocationTag.야외));

        locationRepository.save(loc);

        Image img;
        for (int i = 0; i < 3; i++) {
            img = Image.builder()
                    .location(loc)
                    .img_uuid("Jungbu_Chungnam_" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Jungbu-Chungnam-" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            imageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        user_no = (long) new Random().nextInt(4);

        loc = null;

        loc = Location.builder()
                .loc_name("중부대학교 고양캠퍼스")
                .user_no(user_no)
                .roadAddr("경기도 고양시 덕양구 동헌로 305")
                .addrDetail("고양캠퍼스")
                .siDo("경기도")
                .siGunGu("고양시")
                .info("중부대학교는 학생성장을 학교 발전을 위한 최고의 비전으로 추구하고 있습니다." +
                        " 학생성장은 인공지능과 융복합 시대의 전공역량을 충분히 획득한 전문인재, 올바른 인성과 균형 잡힌 교양을 갖춘 바른 인재, 그리고 창의적인 문제해결력을 갖춘 창의인재로의 발전을 지향합니다")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("031-8075-1000")
                .zipNo("10279")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.학교));
        loc.addLocTag(String.valueOf(LocationTag.야외));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 3; i++) {
            img = Image.builder()
                    .location(loc)
                    .img_uuid("Jungbu-Goyang-" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Jungbu-Goyang-" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            imageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        System.out.println("Location 저장 완료");
    }

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

            Image img = Image.builder()
                    .location(loc)
                    .img_uuid("UUID_"+i)
                    .user_no((long) i)
                    .img_url("path/" + i)
                    .build();

            imageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())){
                loc.setThumbnail(img.getImg_url());
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
        System.out.println(temp.getImgSet());
    }

    @Test
    public void testLocName_Read() {
        List<Location> list = locationRepository.findAllByName("Location_0001");

        for (Location location : list) {
            System.out.println("location = " + location);
            System.out.println(location.getImgSet());
        }
    }

    @Test
    public void testLoc_Update() {
        Optional<Location> loc = locationRepository.findLocByUUID("UUID_0");

        Location temp = loc.get();

        System.out.println("temp = " + temp);
        System.out.println(temp.getImgSet());

        temp.setLoc_name("Updated Loc_0");
        temp.setInfo("Updated Location Info");
        Image img = Image.builder()
                .img_url("Path/10")
                .img_uuid("UUID_0010.png")
                .user_no(0L)
                .location(temp)
                .build();

        imageRepository.save(img);

        temp.addImg(img);

        locationRepository.save(temp);

        Optional<Location> box = locationRepository.findLocByUUID(temp.getLoc_uuid());
        System.out.println(box.get());
        System.out.println(box.get().getImgSet());
    }

    @Test
    public void testLocUUID_Read() {
        Optional<Location> item = locationRepository.findLocByUUID("UUID_1");

        System.out.println("item = " + item.get());
        System.out.println(item.get().getImgSet());
    }

    @Test
    public void testLocAddr_Read() {
        List<com.project.love_data.model.service.Location> list = locationRepository.findByRoadAddrContaining("Addr");

        for (com.project.love_data.model.service.Location location : list) {
            System.out.println("location = " + location);
        }

        System.out.println("\n\n-------------------------\n\n");

        list = locationRepository.findAllByRoadAddrAndAddrDetail("Addr_000", "AddrDetail_0000");

        for (com.project.love_data.model.service.Location location : list) {
            System.out.println("location = " + location);
        }
    }

    @Test
    public void testLocContaining() {
        List<com.project.love_data.model.service.Location> list = locService.locationNameSearch("Loc", SearchOption.CONTAIN);
        int i = 0;

        for (com.project.love_data.model.service.Location location : list) {
            System.out.println("location " + (++i) + " = " + location);
        }
    }

    @Test
    public void testList() {
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(4)
                .build();

        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);

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

    @Test
    public void deleteByID() {
        long id = 14L;

        Optional<Location> box = locationRepository.findById(id);

        if (!box.isPresent()){
            System.out.println("데이터가 없습니다.");
            return;
        }

        Location loc = box.get();

        System.out.println("삭제 전 데이터");
        System.out.println(loc);

        locService.delete(loc);

        box = locationRepository.findById(id);

        if (box.isPresent()) {
            System.out.println("삭제 실패");
        } else {
            System.out.println("삭제 성공");
        }

        List<Location> list = locationRepository.findAll();

        for (Location location : list) {
            System.out.println("location = " + location);
            System.out.println(location.getImgSet());
        }
    }

    @Test
    public void deleteByUUID() {
//        locationRepository.deleteByLoc_uuid("UUID_0");
        String uuid = "UUID_0";

        Optional<Location> box = locationRepository.findLocByUUID(uuid);

        if (!box.isPresent()){
            System.out.println("데이터가 없습니다.");
           return;
        }

        Location loc = box.get();

        System.out.println("삭제 전 데이터");
        System.out.println(loc);

        locService.delete(loc);

        box = locationRepository.findLocByUUID(uuid);

        if (box.isPresent()) {
            System.out.println("삭제 실패");
        } else {
            System.out.println("삭제 성공");
        }

        List<Location> list = locationRepository.findAll();

        for (Location location : list) {
            System.out.println("location = " + location);
            System.out.println(location.getImgSet());
        }
    }
}
