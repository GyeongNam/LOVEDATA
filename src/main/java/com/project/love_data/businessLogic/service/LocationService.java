package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.ImageRepository;
import com.project.love_data.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
@Log4j2
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository repository;
    private final ImageService imgService;

    public Location register(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        LocationDTO dto = getLocationDto(reqParam, tagList);
        List<Image> imgList = new ArrayList<>();
        Location entity = dtoToEntity(dto);

        for (int i = 1; i < filePath.size() - 1; i++) {
            imgList.add(imgService.register(reqParam.get("user_no"), filePath.get(0), filePath.get(i), entity));
        }

        entity.setImgList(imgList);

        repository.save(entity);

        log.info("entity : " + entity);

        return entity;
    }

    public com.project.love_data.model.service.Location dtoToEntity(LocationDTO dto) {
        com.project.love_data.model.service.Location entity = com.project.love_data.model.service.Location.builder()
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
                .zipNo(dto.getZipNo())
                .build();

        return entity;
    }

    public LocationDTO entityToDto(com.project.love_data.model.service.Location entity){
        LocationDTO dto = LocationDTO.builder()
                .loc_no(entity.getLoc_no())
                .loc_name(entity.getLoc_name())
                .user_no(entity.getUser_no())
                .roadAddr(entity.getRoadAddr())
                .addrDetail(entity.getAddrDetail())
                .siDo(entity.getSiDo())
                .siGunGu(entity.getSiGunGu())
                .info(entity.getInfo())
                .tel(entity.getTel())
                .zipNo(entity.getZipNo())
                .tagSet(entity.getTagSet())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    private LocationDTO getLocationDto(Map<String, String> reqParam, List<String> tagList) {
        LocationDTO loc = LocationDTO.builder()
                .loc_name(reqParam.get("name"))
                .user_no(Long.valueOf(reqParam.get("user_no")))
                .roadAddr(reqParam.get("roadAddr"))
                .addrDetail(reqParam.get("addrDetail"))
                .siDo(reqParam.get("siDoName"))
                .siGunGu(reqParam.get("siGunGuName"))
                .info(reqParam.get("info"))
                .tel(reqParam.get("tel"))
                .zipNo(reqParam.get("zipNo"))
                .build();

        for (String item : tagList) {
            loc.addLocTag(item);
        }

        System.out.println("loc = " + loc);

        return loc;
    }

    public PageResultDTO<LocationDTO, com.project.love_data.model.service.Location> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("viewCount").descending());

        Page<com.project.love_data.model.service.Location> result = repository.findAll(pageable);

        Function<com.project.love_data.model.service.Location, LocationDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public List<com.project.love_data.model.service.Location> locationNameSearch(String loc_name, SearchOption searchOption) {
        StringBuilder sb = new StringBuilder();
        switch (searchOption) {
            case START_WITH:
                sb.append(loc_name);
                sb.insert(0, "%");
                break;
            case END_WITH:
                sb.append(loc_name);
                sb.append("%");
                break;
            case CONTAIN:
                sb.append(loc_name);
                sb.insert(0, "%");
                sb.append("%");
                break;
            default:
                return null;
        }

        log.info(sb.toString());

        return repository.findByLoc_nameContaining(sb.toString());
    }

    public void update(Location loc) {
        repository.save(loc);
    }

    public void delete(Location loc) {
        List<Image> list = loc.getImgList();

        for (Image image : list) {
            image.setLocation(null);

            imgService.update(image);

            imgService.delete(image);
        }

        loc.setImgList(null);

        update(loc);

        repository.deleteByLoc_uuid(loc.getLoc_uuid());
    }
}
