package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.ImageDTO;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;

    public Image getImage(Long imgId) {
        Optional<Image> item = repository.findById(imgId);

        return item.isPresent() ? item.get() : null;
    }

    public Image getImage(String uuid) {
        Optional<Image> item = repository.findImageByImg_uuid(uuid);

        return item.isPresent() ? item.get() : null;
    }

    public Image getImageEntity(String user_no, String fileRootPath, String fileName, Location location, long img_index) {
//        ImageDTO dto = getImageDTO(user_no, fileRootPath, fileName, location);
//        Image entity = dtoToEntity(dto);

        Image entity = Image.builder()
                .location(location)
                .img_uuid(fileName)
                .img_url(fileRootPath+"/"+fileName)
                .user_no(Long.valueOf(user_no))
                .idx(img_index)
                .build();

//        repository.save(entity);

        return entity;
    }

    public void update(Image img) {
        repository.save(img);
    }

    public Image dtoToEntity(ImageDTO dto) {
        Image entity = Image.builder()
                .img_url(dto.getImg_url())
                .user_no(dto.getUser_no())
//                .loc_uuid(dto.getLoc_uuid())
                .location(dto.getLocation())
                .img_uuid(dto.getImg_uuid())
                .idx(dto.getIdx())
                .build();

        return entity;
    }

    public ImageDTO entityToDTO(Image img) {
        ImageDTO dto = ImageDTO.builder()
                .idx(img.getIdx())
                .img_no(img.getImg_no())
                .img_url(img.getImg_url())
                .img_uuid(img.getImg_uuid())
                .location(img.getLocation())
                .regDate(img.getRegDate())
                .modDate(img.getModDate())
                .build();

        return dto;
    }

    private ImageDTO getImageDTO(String user_no, String fileRootPath, String fileName, Location location) {
        ImageDTO dto = ImageDTO.builder()
                .img_url(fileRootPath + File.separator + fileName)
                .user_no(Long.valueOf(user_no))
                .location(location)
                .img_uuid(fileName)
                .build();

        return dto;
    }

    public void delete(Image img) {
        repository.deleteByImg_uuid(img.getImg_uuid());
    }

    public Image editImageEntityIndex(String uuid, Long img_Index) {
        Image img = getImage(uuid);

        img.setIdx(img_Index);

        return img;
    }
}
