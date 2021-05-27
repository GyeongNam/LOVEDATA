package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.ImageDTO;
import com.project.love_data.model.resource.Image;
import com.project.love_data.model.service.Location;
import com.project.love_data.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;

    public Image register(String user_no, String fileRootPath, String fileName, Location location) {
        ImageDTO dto = getImageDTO(user_no, fileRootPath, fileName, location);
        Image entity = dtoToEntity(dto);

        repository.save(entity);

        return entity;
    }

    public void update(Image img) {
        repository.save(img);
    }

    public Image dtoToEntity(ImageDTO dto) {
        com.project.love_data.model.resource.Image entity = com.project.love_data.model.resource.Image.builder()
                .img_url(dto.getImg_url())
                .user_no(dto.getUser_no())
//                .loc_uuid(dto.getLoc_uuid())
                .location(dto.getLocation())
                .img_uuid(dto.getImg_uuid())
                .build();

        return entity;
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
}
