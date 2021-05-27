package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.ImageDTO;
import com.project.love_data.model.resource.Image;
import com.project.love_data.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.io.File;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository repository;

    public String register(String user_no, String loc_uuid, String fileRootPath, String fileName) {
        ImageDTO dto = getImageDTO(user_no, loc_uuid, fileRootPath, fileName);
        Image entity = dtoToEntity(dto);

        repository.save(entity);

        return entity.getImg_uuid();
    }

    private Image dtoToEntity(ImageDTO dto) {
        Image entity = Image.builder()
                .img_url(dto.getImg_url())
                .user_no(dto.getUser_no())
                .loc_uuid(dto.getLoc_uuid())
                .img_uuid(dto.getImg_uuid())
                .build();

        return entity;
    }

    private ImageDTO getImageDTO(String user_no, String loc_uuid, String fileRootPath, String fileName) {
        ImageDTO dto = ImageDTO.builder()
                .img_url(fileRootPath + File.separator + fileName)
                .user_no(Long.valueOf(user_no))
                .loc_uuid(loc_uuid)
                .img_uuid(fileName)
                .build();

        return dto;
    }
}
