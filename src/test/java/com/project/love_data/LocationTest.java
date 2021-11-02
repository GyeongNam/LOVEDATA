package com.project.love_data;

import com.project.love_data.businessLogic.service.LocationService;
import com.project.love_data.businessLogic.service.MatchOption;
import com.project.love_data.businessLogic.service.SearchType;
import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.*;
import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationTag;
import com.project.love_data.repository.LocationImageRepository;
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
    LocationImageRepository locationImageRepository;

    @Autowired
    LocationService locService;

    @Test
    public void InsertInitLocation() {
//        Long user_no = (long) new Random().nextInt(4) + 1L;
        Long user_no = 1L;

        Location loc = Location.builder()
                .loc_name("중부대학교 충청캠퍼스")
                .user_no(user_no)
                .fullRoadAddr("충청남도 금산군 추부면 대학로 201")
                .roadAddr("대학로 201")
                .addrDetail("충청캠퍼스")
                .siDo("충청남도")
                .siGunGu("금산군")
                .info("중부대학교는 학생성장을 학교 발전을 위한 최고의 비전으로 추구하고 있습니다.\n" +
                        " 학생성장은 인공지능과 융복합 시대의 전공역량을 충분히 획득한 전문인재, 올바른 인성과 균형 잡힌 교양을 갖춘 바른 인재, 그리고 창의적인 문제해결력을 갖춘 창의인재로의 발전을 지향합니다")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("041-750-6500")
                .zipNo("32713")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.학교));
        loc.addLocTag(String.valueOf(LocationTag.야외));

        locationRepository.save(loc);

        LocationImage img;
        for (int i = 0; i < 3; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("Jungbu-Chungnam-" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Jungbu-Chungnam-" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

//        user_no = (long) (Math.random() * 10)+1;

        loc = null;

        loc = Location.builder()
                .loc_name("중부대학교 고양캠퍼스")
                .user_no(user_no)
                .fullRoadAddr("경기도 고양시 덕양구 동헌로 305")
                .roadAddr("동헌로 305")
                .addrDetail("고양캠퍼스")
                .siDo("경기도")
                .siGunGu("고양시")
                .info("중부대학교는 학생성장을 학교 발전을 위한 최고의 비전으로 추구하고 있습니다.\n" +
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
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("Jungbu-Goyang-" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Jungbu-Goyang-" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

//        user_no = (long) (Math.random() * 10)+1;

        loc = null;

        loc = Location.builder()
                .loc_name("서울숲")
                .user_no(user_no)
                .fullRoadAddr("서울특별시 성동구 뚝섬로 273")
                .roadAddr("뚝섬로 273")
                .addrDetail("서울숲")
                .siDo("서울특별시")
                .siGunGu("성동구")
                .info("서울숲은 문화예술공원, 체험학습원, 생태숲, 습지생태원 네 가지의 특색 있는 공간들로 구성되어 있으며, 한강과 맞닿아 있어 다양한 문화여가공간을 제공합니다.\n" +
                        "또한 서울숲공원은 조성부터 프로그램 운영까지 시민의 참여로 이루어진 최초의 공원입니다.")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("02-460-2905")
                .zipNo("04770")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.공원));
        loc.addLocTag(String.valueOf(LocationTag.야외));
        loc.addLocTag(String.valueOf(LocationTag.숲));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 4; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("Seoul-Forest-0" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Seoul-Forest-0" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        loc = Location.builder()
                .loc_name("광화문")
                .user_no(user_no)
                .fullRoadAddr("서울특별시 종로구 효자로 12")
                .roadAddr("효자로 12")
                .addrDetail("국립고궁박물관")
                .siDo("서울특별시")
                .siGunGu("종로구")
                .info("경복궁의 동서남북을 둘러싸고 있는 4개의 대문 중 남쪽에 위치한 정문(正門)이다." +
                        " 1865년 고종 중건 당시의 모습과 원래 축에서 틀어졌던 각도를 원래 위치로 돌려 2010년 8월 15일 광화문 복원공사를 마친 바 있다.")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("02-3700-3901")
                .zipNo("03045")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.체험));
        loc.addLocTag(String.valueOf(LocationTag.야외));
        loc.addLocTag(String.valueOf(LocationTag.박물관));
        loc.addLocTag(String.valueOf(LocationTag.한옥));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 4; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("Gwanghwamun-" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Gwanghwamun-" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        loc = Location.builder()
                .loc_name("하늘공원")
                .user_no(user_no)
                .fullRoadAddr("서울특별시 마포구 하늘공원로 95")
                .roadAddr("하늘공원로 95")
                .addrDetail("하늘공원")
                .siDo("서울특별시")
                .siGunGu("마포구")
                .info("서울특별시 마포구 상암동에 있는 생태환경공원.")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("02-300-5501")
                .zipNo("03900")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.체험));
        loc.addLocTag(String.valueOf(LocationTag.야외));
        loc.addLocTag(String.valueOf(LocationTag.숲));
        loc.addLocTag(String.valueOf(LocationTag.기념일));
        loc.addLocTag(String.valueOf(LocationTag.공원));
        loc.addLocTag(String.valueOf(LocationTag.가로수길));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 4; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("SkyPark-0" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/SkyPark-0" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        loc = Location.builder()
                .loc_name("전쟁기념관")
                .user_no(user_no)
                .fullRoadAddr("서울 용산구 이태원로 29")
                .roadAddr("이태원로 29")
                .addrDetail("전쟁기념관")
                .siDo("서울특별시")
                .siGunGu("용산구")
                .info("서울의 중심, 용산에 위치한 전쟁기념관은 전쟁을 단일 주제로, 5천년 민족사를 조망한 대한민국 유일의 전쟁사 종합박물관입니다.")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("02-709-3114")
                .zipNo("04391")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.체험));
        loc.addLocTag(String.valueOf(LocationTag.박물관));
        loc.addLocTag(String.valueOf(LocationTag.실내));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 4; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("WarMemorial-0" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/WarMemorial-0" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        loc = Location.builder()
                .loc_name("남산타워")
                .user_no(user_no)
                .fullRoadAddr("서울 용산구 남산공원길 105")
                .roadAddr("남산공원길 105")
                .addrDetail("남산타워")
                .siDo("서울특별시")
                .siGunGu("용산구")
                .info("서울의 중심에서 만나는 특별한 순간! NAMSAN SEOUL TOWER. 서울을 대표하는 복합문화공간 남산서울타워에서 잊지 못할 순간을 경험하세요.")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("02-756-2486")
                .zipNo("04340")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.체험));
        loc.addLocTag(String.valueOf(LocationTag.기념일));
        loc.addLocTag(String.valueOf(LocationTag.실내));
        loc.addLocTag(String.valueOf(LocationTag.산));
        loc.addLocTag(String.valueOf(LocationTag.야경));
        loc.addLocTag(String.valueOf(LocationTag.전망대));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 4; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("Namsan-Ntower-0" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/Namsan-Ntower-0" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        loc = Location.builder()
                .loc_name("독립기념관")
                .user_no(user_no)
                .fullRoadAddr("충남 천안시 동남구 목천읍 독립기념관로 1")
                .roadAddr("독립기념관로 1")
                .addrDetail("독립기념관")
                .siDo("충청남도")
                .siGunGu("천안시")
                .info("독립기념관은 정신적 힘을 뿜어내고 생산하는 곳입니다. " +
                        "식민지지배라는 수난과 시련을 이겨낸 민족의 저력을 확인하는 곳," +
                        " 불가능한 것을 가능한 것으로 만들어낸 독립정신을 재생산하는 곳, 이것이 독립기념관입니다.")
                .loc_uuid(UUID.randomUUID().toString())
                .tel("041-560-0114")
                .zipNo("31232")
                .build();

        loc.addLocTag(String.valueOf(LocationTag.체험));
        loc.addLocTag(String.valueOf(LocationTag.박물관));
        loc.addLocTag(String.valueOf(LocationTag.실내));
        loc.addLocTag(String.valueOf(LocationTag.전시));

        locationRepository.save(loc);

        img = null;
        for (int i = 0; i < 4; i++) {
            img = LocationImage.builder()
                    .location(loc)
                    .img_uuid("IndependenceHallofKorea-0" + i + ".jpg")
                    .user_no(user_no)
                    .img_url("/image/init/IndependenceHallofKorea-0" + i + ".jpg")
                    .build();
            img.setLocation(loc);

            locationImageRepository.save(img);

            loc.addImg(img);

            if ("".equals(loc.getThumbnail())) {
                loc.setThumbnail(img.getImg_url());
            }
        }

        locationRepository.save(loc);

        System.out.println("Location 저장 완료");
    }

//    @Test
//    public void InsertLocation() {
//        List<LocationTag> tagSet = new ArrayList<>();
//        tagSet.add(LocationTag.공원);
//        tagSet.add(LocationTag.학교);
//        tagSet.add(LocationTag.한옥);
//        tagSet.add(LocationTag.겨울);
//
//        for (int i = 0; i < 10; i++) {
//            String pad = StringUtils.leftPad(Integer.toString(i), 4, '0');
//            Location loc = Location.builder()
//                    .loc_name("Location_" + pad)
//                    .user_no((long) new Random().nextInt(4))
//                    .roadAddr("Addr_" + pad)
//                    .addrDetail("AddrDetail_" + pad)
//                    .siDo("siDo_" + pad)
//                    .siGunGu(pad)
//                    .info("This locaiton is Generated randomly " + pad)
//                    .loc_uuid("UUID_" + i)
//                    .tel("010-0000-00" + StringUtils.leftPad(Integer.toString(i), 2, '0'))
//                    .zipNo(String.valueOf(i))
//                    .build();
//
//            if (i % 2 == 0) {
//                loc.addLocTag(tagSet.get(0).toString());
//                loc.addLocTag(tagSet.get(2).toString());
//            } else {
//                loc.addLocTag(tagSet.get(1).toString());
//                loc.addLocTag(tagSet.get(3).toString());
//            }
//
//            locationRepository.save(loc);
//
//            LocationImage img = LocationImage.builder()
//                    .location(loc)
//                    .img_uuid("UUID_"+i)
//                    .user_no((long) i)
//                    .img_url("path/" + i)
//                    .build();
//
//            locationImageRepository.save(img);
//
//            loc.addImg(img);
//
//            if ("".equals(loc.getThumbnail())){
//                loc.setThumbnail(img.getImg_url());
//            }
//
//            locationRepository.save(loc);
//        }
//
//        Location loc = Location.builder()
//                .loc_name("Location_0001")
//                .user_no((long) new Random().nextInt(4))
//                .roadAddr("Addr_0001")
//                .addrDetail("AddrDetail_0001")
//                .siDo("siDo_0001")
//                .siGunGu("0001")
//                .info("This locaiton is Generated randomly duplicated")
//                .loc_uuid("UUID_0010")
//                .tel("010-0000-0001")
//                .zipNo("00000")
//                .build();
//
//        loc.addLocTag(String.valueOf(tagSet.get(0)));
//        loc.addLocTag(String.valueOf(tagSet.get(2)));
//
//        locationRepository.save(loc);
//
//        System.out.println("Location 저장 완료");
//    }
//
//    @Test
//    public void testLocTag_Read() {
//        Optional<Location> loc = locationRepository.findLocByLoc_no(1L);
//
//        Location temp = loc.get();
//
//        System.out.println(temp);
//        System.out.println(temp.getImgSet());
//    }
//
//    @Test
//    public void testLocName_Read() {
//        List<Location> list = locationRepository.findAllByName("중부대학교");
//
//        for (Location location : list) {
//            System.out.println("location = " + location);
//            System.out.println(location.getImgSet());
//        }
//    }
//
//    @Test
//    public void testLoc_Update() {
//        Optional<Location> loc = locationRepository.findLocByUUID("UUID_0");
//
//        Location temp = loc.get();
//
//        System.out.println("temp = " + temp);
//        System.out.println(temp.getImgSet());
//
//        temp.setLoc_name("Updated Loc_0");
//        temp.setInfo("Updated Location Info");
//        LocationImage img = LocationImage.builder()
//                .img_url("Path/10")
//                .img_uuid("UUID_0010.png")
//                .user_no(0L)
//                .location(temp)
//                .build();
//
//        locationImageRepository.save(img);
//
//        temp.addImg(img);
//
//        locationRepository.save(temp);
//
//        Optional<Location> box = locationRepository.findLocByUUID(temp.getLoc_uuid());
//        System.out.println(box.get());
//        System.out.println(box.get().getImgSet());
//    }
//
//    @Test
//    public void testLocUUID_Read() {
//        Optional<Location> item = locationRepository.findLocByUUID("095f0735-3290-4b91-8f4b-d191ef40d8ca");
//
//        System.out.println("item = " + item.get());
//        System.out.println(item.get().getImgSet());
//    }
//
//    @Test
//    public void testLocAddr_Read() {
//        List<com.project.love_data.model.service.Location> list = locationRepository.findByRoadAddrContaining("Addr");
//
//        for (com.project.love_data.model.service.Location location : list) {
//            System.out.println("location = " + location);
//        }
//
//        System.out.println("\n\n-------------------------\n\n");
//
//        list = locationRepository.findAllByRoadAddrAndAddrDetail("Addr_000", "AddrDetail_0000");
//
//        for (com.project.love_data.model.service.Location location : list) {
//            System.out.println("location = " + location);
//        }
//    }
//
//    @Test
//    public void testLocContaining() {
//        List<com.project.love_data.model.service.Location> list = locService.locationNameSearch("Loc", MatchOption.CONTAIN);
//        int i = 0;
//
//        for (com.project.love_data.model.service.Location location : list) {
//            System.out.println("location " + (++i) + " = " + location);
//        }
//    }
//
//    @Test
//    public void testList() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(4)
//                .build();
//
//        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);
//
//        System.out.println("PREV = " + resultDTO.isPrev());
//        System.out.println("NEXT = " + resultDTO.isNext());
//        System.out.println("TOTAL : " + resultDTO.getTotalPage());
//
//        System.out.println("-------------------------------------------------");
//        for (LocationDTO locationDTO : resultDTO.getDtoList()) {
//            System.out.println(locationDTO.getLoc_no() + "\tlocationDTO = " + locationDTO);
//        }
//
//        System.out.println("=================================================");
//        List<Integer> temp = resultDTO.getPageList();
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
//    }
//
//    @Test
//    public void undeleteByID() {
//        long id = 1L;
//
//        Location loc = locService.selectLoc(id);
//
//        if (loc == null ) {
//            System.out.println("선택한 ID를 가진 장소가 없습니다.");
//            return;
//        }
//
//        locService.rollback(loc.getLoc_no());
//
//        loc = locService.selectLoc(loc.getLoc_no());
//
//        if (loc.is_deleted()) {
//            System.out.println("롤백 실패");
//        } else {
//            System.out.println("롤백 성공");
//        }
//    }
//
//    @Test
//    public void deleteByID() {
//        long id = 2L;
//
//        Optional<Location> box = locationRepository.findById(id);
//
//        if (!box.isPresent()){
//            System.out.println("데이터가 없습니다.");
//            return;
//        }
//
//        Location loc = box.get();
//
//        System.out.println("삭제 전 데이터");
//        System.out.println(loc);
//
//        locService.delete(loc.getLoc_no());
//
//        box = locationRepository.findById(id);
//
//        if (box.get().is_deleted()) {
//            System.out.println("삭제 성공");
//        } else {
//            System.out.println("삭제 실패");
//        }
//
//        List<Location> list = locationRepository.findAll();
//
//        for (Location location : list) {
//            System.out.println("location = " + location);
//            System.out.println(location.getImgSet());
//        }
//    }
//
//    @Test
//    public void deleteByUUID() {
////        locationRepository.deleteByLoc_uuid("UUID_0");
//        String uuid = "UUID_0";
//
//        Optional<Location> box = locationRepository.findLocByUUID(uuid);
//
//        if (!box.isPresent()){
//            System.out.println("데이터가 없습니다.");
//           return;
//        }
//
//        Location loc = box.get();
//
//        System.out.println("삭제 전 데이터");
//        System.out.println(loc);
//
//        locService.delete(loc.getLoc_uuid());
//
//        box = locationRepository.findLocByUUID(uuid);
//
//        if (box.get().is_deleted()) {
//            System.out.println("삭제 성공");
//        } else {
//            System.out.println("삭제 실패");
//        }
//
//        List<Location> list = locationRepository.findAll();
//
//        for (Location location : list) {
//            System.out.println("location = " + location);
//            System.out.println(location.getImgSet());
//        }
//    }
//
//    @Test
//    public void nameSearchTest() {
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(4)
//                .searchType(SearchType.TITLE)
//                .keyword("중부대")
//                .build();
//
//        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);
//
//        System.out.println("PREV = " + resultDTO.isPrev());
//        System.out.println("NEXT = " + resultDTO.isNext());
//        System.out.println("TOTAL : " + resultDTO.getTotalPage());
//
//        System.out.println("-------------------------------------------------");
//        for (LocationDTO locationDTO : resultDTO.getDtoList()) {
//            System.out.println(locationDTO.getLoc_no() + "\tlocationDTO = " + locationDTO);
//        }
//
//        System.out.println("=================================================");
//        List<Integer> temp = resultDTO.getPageList();
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
//    }
//
//    @Test
//    public void tagSearchTest() {
//        List<String> tagList = new ArrayList<>();
//        tagList.add(LocationTag.야외.name());
//        tagList.add(LocationTag.숲.name());
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(4)
//                .searchType(SearchType.TAG)
//                .tagList(tagList)
//                .build();
//
//        PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> resultDTO = locService.getList(pageRequestDTO);
//
//        System.out.println("PREV = " + resultDTO.isPrev());
//        System.out.println("NEXT = " + resultDTO.isNext());
//        System.out.println("TOTAL : " + resultDTO.getTotalPage());
//
//        System.out.println("-------------------------------------------------");
//        for (LocationDTO locationDTO : resultDTO.getDtoList()) {
//            System.out.println(locationDTO.getLoc_no() + "\tlocationDTO = " + locationDTO);
//        }
//
//        System.out.println("=================================================");
//        List<Integer> temp = resultDTO.getPageList();
//        resultDTO.getPageList().forEach(i -> System.out.println(i));
//    }
}
