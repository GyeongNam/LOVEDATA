package com.project.love_data.businessLogic.service;

import com.project.love_data.model.resource.Image;
import com.project.love_data.model.resource.ImageDTO;
import com.project.love_data.model.service.Location;
import com.project.love_data.model.service.LocationDTO;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

@Service
public class ImageService {

    public Image dtoToEntity(ImageDTO dto) {
        Image entity = Image.builder()
                .img_url(dto.getImg_url())
                .user_no(dto.getUser_no())
                .loc_uuid(dto.getLoc_uuid())
                .img_uuid(dto.getImg_uuid())
                .build();

        return entity;
    }

    public ImageDTO getImageDTO(LocationDTO locDTO, String fileRootPath, String fileName) {
        ImageDTO dto = ImageDTO.builder()
                .img_url(fileRootPath + File.separator + fileName)
                .user_no(locDTO.getUser_no())
                .loc_uuid(locDTO.getLoc_uuid())
                .img_uuid(fileName)
                .build();

        return dto;
    }
}
