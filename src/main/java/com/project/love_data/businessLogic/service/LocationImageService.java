package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.LocationImageDTO;
import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.LocationImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
@Log4j2
@RequiredArgsConstructor
public class LocationImageService {
    private final LocationImageRepository repository;
    private final FilePathChangeService pathChangeService;

    public LocationImage getImage(Long imgId) {
        Optional<LocationImage> item = repository.findById(imgId);

        return item.orElse(null);
    }

    public LocationImage getLiveImage(String uuid) {
        Optional<LocationImage> item = repository.findLiveImageByImg_uuid(uuid);

        return item.orElse(null);
    }

    public LocationImage getAllImage(String uuid) {
        Optional<LocationImage> item = repository.findLiveImageByImg_uuid(uuid);

        return item.orElse(null);
    }

    public LocationImage getImageEntity(String user_no, String fileRootPath, String fileName, Location location, long img_index) {
//        ImageDTO dto = getImageDTO(user_no, fileRootPath, fileName, location);
//        Image entity = dtoToEntity(dto);

        LocationImage entity = LocationImage.builder()
                .location(location)
                .img_uuid(fileName)
                .img_url(fileRootPath+"/"+fileName)
                .user_no(Long.valueOf(user_no))
                .idx(img_index)
                .build();

//        repository.save(entity);

        return entity;
    }

    public LocationImage update(LocationImage img) {
        return repository.save(img);
    }

    public LocationImage dtoToEntity(LocationImageDTO dto) {
        LocationImage entity = LocationImage.builder()
                .img_url(dto.getImg_url())
                .user_no(dto.getUser_no())
//                .loc_uuid(dto.getLoc_uuid())
                .location(dto.getLocation())
                .img_uuid(dto.getImg_uuid())
                .idx(dto.getIdx())
                .is_deleted(dto.is_deleted())
                .build();

        return entity;
    }

    public LocationImageDTO entityToDTO(LocationImage img) {
        LocationImageDTO dto = LocationImageDTO.builder()
                .idx(img.getIdx())
                .img_no(img.getImg_no())
                .img_url(img.getImg_url())
                .img_uuid(img.getImg_uuid())
                .location(img.getLocation())
                .regDate(img.getRegDate())
                .modDate(img.getModDate())
                .is_deleted(img.is_deleted())
                .build();

        return dto;
    }

    private LocationImageDTO getImageDTO(String user_no, String fileRootPath, String fileName, Location location) {
        LocationImageDTO dto = LocationImageDTO.builder()
                .img_url(fileRootPath + File.separator + fileName)
                .user_no(Long.valueOf(user_no))
                .location(location)
                .img_uuid(fileName)
                .build();

        return dto;
    }

    private LocationImage disable(LocationImage img) {
        if (img == null) {
            return null;
        }

//        String extension = pathChangeService.getFileExtension(img.getImg_url());
//        if (pathChangeService.execute(img.getImg_uuid(), FileAction.DELETE, PathType.LOC,
//                FileExtension.valueOf(extension.toUpperCase(Locale.ROOT)))) {
//            img.set_deleted(true);
//            img.setImg_url("/image/upload/" + "LOC^"  + img.getImg_uuid());
//        }

        img.set_deleted(true);
        update(img);

        return getImage(img.getImg_no());
    }

    private LocationImage enable(LocationImage img) {
        if (img == null) {
            return null;
        }

//        String extension = pathChangeService.getFileExtension(img.getImg_url());
//        if (pathChangeService.execute(img.getImg_uuid(), FileAction.ROLLBACK, PathType.LOC,
//                FileExtension.valueOf(extension.toUpperCase(Locale.ROOT)))) {
//            img.set_deleted(false);
//            img.setImg_url("/image/location/" + img.getImg_uuid());
//        }

        img.set_deleted(false);
        update(img);

        return getImage(img.getImg_no());
    }

    public void rollback(Long imgNo) {
        LocationImage img = getImage(imgNo);

        if (img == null) {
            log.warn("존재 하지 않는 imgNo 입니다.");
            log.warn("롤백 과정이 취소됩니다.");
            return;
        }

        if (img.is_deleted()) {
            enable(img);
        }
    }

    public void delete(Long imgNo) {
        LocationImage img = getImage(imgNo);

        if (img == null) {
            log.warn("존재 하지 않는 imgNo 입니다.");
            log.warn("삭제 과정이 취소됩니다.");
            return;
        }

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public void delete(String uuid) {
        LocationImage img = getAllImage(uuid);

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public Long getFirstIndexOfLastDeletedLocationImage(Long locNo) {
        Optional<Long> imgNo = repository.findFirstIndexOfLastDeletedLocationImageByLocNo(locNo);

        return imgNo.orElse(0L);
    }

    public List<LocationImage> getLastDeletedImageList(Long locNo) {
        Long imgNoFI = getFirstIndexOfLastDeletedLocationImage(locNo);

        if (imgNoFI == 0) {
            return null;
        }

        Optional<List<LocationImage>> item = repository.findLocationImageListOfLocNoAndImgNo(locNo, imgNoFI);

        return item.orElse(null);
    }

    public void permaDelete(String img_uuid) {
        Optional<LocationImage> item = repository.findImageByImg_uuid(img_uuid);

        if (item.isPresent()) {
            LocationImage img = item.get();

            String extension = pathChangeService.getFileExtension(img.getImg_url());
            if (pathChangeService.execute(img.getImg_uuid(), FileAction.DELETE, PathType.LOC,
                    FileExtension.valueOf(extension.toUpperCase(Locale.ROOT)))) {
//                img.set_deleted(true);
//                img.setImg_url("/image/upload/" + "LOC^" + img.getImg_uuid());
                repository.deleteByImg_uuid(img_uuid);
            }
        }
    }

    public LocationImage editImageEntityIndex(String uuid, Long img_Index) {
        LocationImage img = getAllImage(uuid);

        img.setIdx(img_Index);

        return img;
    }

    public List<LocationImage> getAllLiveImageByLocNo(Long locNo) {
        if (locNo == null) {
            return null;
        }

        if (locNo < 0) {
            return null;
        }

        Optional<List<LocationImage>> items = repository.findAllLiveImageByLoc_no(locNo);

        return items.orElse(null);
    }
}
