package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.LocationImageDTO;
import com.project.love_data.model.resource.LocationImage;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.LocationImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class LocationImageService {
    private final LocationImageRepository repository;

    public LocationImage getImage(Long imgId) {
        Optional<LocationImage> item = repository.findById(imgId);

        return item.isPresent() ? item.get() : null;
    }

    public LocationImage getImage(String uuid) {
        Optional<LocationImage> item = repository.findImageByImg_uuid(uuid);

        return item.isPresent() ? item.get() : null;
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

    public void update(LocationImage img) {
        repository.save(img);
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
        img.set_deleted(true);

        update(img);

        return getImage(img.getImg_no());
    }

    private LocationImage enable(LocationImage img) {
        img.set_deleted(false);

        update(img);

        return getImage(img.getImg_no());
    }

    public void delete(Long imgNo) {
        LocationImage img = getImage(imgNo);

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public void delete(String uuid) {
        LocationImage img = getImage(uuid);

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public void permaDelete(String img_uuid) {
        repository.deleteByImg_uuid(img_uuid);
    }

    public LocationImage editImageEntityIndex(String uuid, Long img_Index) {
        LocationImage img = getImage(uuid);

        img.setIdx(img_Index);

        return img;
    }
}
