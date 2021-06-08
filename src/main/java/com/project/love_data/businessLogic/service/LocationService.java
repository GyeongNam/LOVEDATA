package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.LocationDTO;
import com.project.love_data.dto.PageRequestDTO;
import com.project.love_data.dto.PageResultDTO;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Comment;
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
    private final ImageRepository imgRepository;
    private final ImageService imgService;
    private final CommentService cmtService;

    public Location register(Map<String, String> reqParam, List<String> tagList, List<String> filePath) {
        LocationDTO dto = getLocationDto(reqParam, tagList);
        List<Image> imgList = new ArrayList<>();
        Location entity = dtoToEntity(dto);

        for (int i = 1; i < filePath.size(); i++) {
            // filePath.get(0)  ==  Parent Folder (URI)
            // filePath.get(i)  ==  fileNames
            imgList.add(imgService.getImageEntity(reqParam.get("user_no"), filePath.get(0), filePath.get(i), entity));
        }

        entity.setImgSet(new HashSet<>(imgList));

        repository.save(entity);

        for (Image image : imgList) {
            imgRepository.save(image);
            if ("".equals(entity.getThumbnail())) {
                entity.setThumbnail(image.getImg_url());
            }
        }

        log.info("entity : " + entity);

        return entity;
    }

    public Location dtoToEntity(LocationDTO dto) {
        Location entity = Location.builder()
                .loc_no(dto.getLoc_no())
                .loc_name(dto.getLoc_name())
                .loc_uuid(dto.getLoc_uuid())
                .user_no(dto.getUser_no())
                .roadAddr(dto.getRoadAddr())
                .addrDetail(dto.getAddrDetail())
                .siDo(dto.getSiDo())
                .siGunGu(dto.getSiGunGu())
                .info(dto.getInfo())
                .tel(dto.getTel())
                .zipNo(dto.getZipNo())
                .tagSet(dto.getTagSet())
                .imgSet(new HashSet<>(dto.getImgList()))
                .cmtSet(new HashSet<>(dto.getCmtList()))
                .likeCount(dto.getLikeCount())
                .thumbnail(dto.getThumbnail())
                .viewCount(dto.getViewCount())
                .build();

        return entity;
    }

    public LocationDTO entityToDto(Location entity){
        LocationDTO dto = LocationDTO.builder()
                .loc_no(entity.getLoc_no())
                .loc_name(entity.getLoc_name())
                .loc_uuid(entity.getLoc_uuid())
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
                .likeCount(entity.getLikeCount())
                .thumbnail(entity.getThumbnail())
                .viewCount(entity.getViewCount())
                .build();

        // Image List 변환 및 정렬
        // idx 기준 정렬
        List<Image> tempList = new ArrayList<>();
        List<Image> imgList = new ArrayList<>();

        for (Image img :
                entity.getImgSet()) {
            tempList.add(img);
        }

//        for (int i = 0; i < tempList.size(); i++) {
//            for (int j = 0; j < tempList.size(); j++) {
//                if (tempList.get(j).getIdx() == i) {
//                    imgList.add(tempList.get(j));
//                    break;
//                }
//            }
//        }

        for (int i = 0; i < tempList.size(); i++) {
            tempList.get(i).setIdx((long) i);
            imgList.add(tempList.get(i));
        }

        // Comment List 변환 및 정렬
        // cmd_idx 기준 정렬
        List<Comment> tempCmtList = new ArrayList<>(entity.getCmtSet());
        List<Comment> cmtList = new ArrayList<>();

        for (int i = 0; i < tempCmtList.size(); i++) {
            for (int j = 0; j < tempCmtList.size(); j++) {
                if (tempCmtList.get(j).getCmtIdx() == i) {
                    cmtList.add(tempCmtList.get(j));
                    break;
                }
            }
        }

        dto.setImgList(imgList);
        dto.setCmtList(cmtList);

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

        return loc;
    }

    public PageResultDTO<LocationDTO, Location> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("viewCount").descending());

        Page<com.project.love_data.model.service.Location> result = repository.findAll(pageable);

        Function<com.project.love_data.model.service.Location, LocationDTO> fn = (entity -> entityToDto(entity));

        return new PageResultDTO<>(result, fn);
    }

    public List<Location> locationNameSearch(String loc_name, SearchOption searchOption) {
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

        return repository.findByLoc_nameContaining(sb.toString());
    }

    public Location selectLoc(Long loc_no){
        Optional<Location> result = repository.findById(loc_no);

        return result.isPresent() ? result.get() : null;
    }

    public LocationDTO selectLocDTO(Long loc_no) {
        Optional<Location> result = repository.findById(loc_no);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public Location selectLoc(String loc_uuid) {
        Optional<Location> result = repository.findLocByUUID(loc_uuid);

        return result.isPresent() ? result.get() : null;
    }

    public LocationDTO selectLocDTO(String loc_uuid) {
        Optional<Location> result = repository.findLocByUUID(loc_uuid);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    public void update(Location loc) {
        repository.save(loc);
    }

    public void delete(Location loc) {
        List<Image> list = (List<Image>) loc.getImgSet();
        Set<Comment> cmtSet = loc.getCmtSet();

        for (Image image : list) {
            image.setLocation(null);

            imgService.update(image);

            imgService.delete(image);
        }

        for (Comment cmt : cmtSet) {
            cmtService.delete(cmt);
        }

        loc.setImgSet(null);

        update(loc);

        repository.deleteByLoc_uuid(loc.getLoc_uuid());
    }

    public boolean onClickLike(Long loc_no) {
        Location entity = selectLoc(loc_no);

        if (entity == null) {
            return false;
        }

        entity.setLikeCount(entity.getLikeCount() + 1);

        update(entity);

        return true;
    }

    public boolean onClickLikeUndo(Long loc_no) {
        Location entity = selectLoc(loc_no);

        if(entity == null) {
            return false;
        }

        entity.setLikeCount(entity.getLikeCount() - 1);

        update(entity);

        return true;
    }

    public void incViewCount(Long loc_no) {
        LocationDTO dto = selectLocDTO(loc_no);

        dto.setViewCount(dto.getViewCount()+1);

        Location entity = dtoToEntity(dto);

        update(entity);
    }
}
