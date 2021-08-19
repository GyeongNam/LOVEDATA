package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.ReviewImageDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.repository.ReviewImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewImageService {
    private final ReviewImageRepository repository;

    public ReviewImage getImage(Long imgId) {
        Optional<ReviewImage> item = repository.findById(imgId);

        return item.isPresent() ? item.get() : null;
    }

    public ReviewImage getImage(String uuid) {
        Optional<ReviewImage> item = repository.findLiveImageByImg_uuid(uuid);

        return item.isPresent() ? item.get() : null;
    }

    public List<ReviewImage> getAllImagesByCorNo(Long corNo) {
        Optional<List<ReviewImage>> itmes = repository.findAllImageByCor_no(corNo);

        return itmes.orElse(null);
    }

    public List<ReviewImage> getAllLiveImageByCorNoAndRevNo(Long corNo, Long revNo) {
        Optional<List<ReviewImage>> items = repository.findAllLiveImageByCor_noAndRev_no(corNo, revNo);

        return items.orElse(null);
    }

    public List<ReviewImage> getLiveImagesByCorNo(Long corNo) {
        Optional<List<ReviewImage>> itmes = repository.findAllLiveImageByCor_no(corNo);

        return itmes.orElse(null);
    }

    public ReviewImage getImageEntity(Long user_no, String fileRootPath, String fileName, Long corNo, Long revNo, Long img_index) {
//        ImageDTO dto = getImageDTO(user_no, fileRootPath, fileName, location);
//        Image entity = dtoToEntity(dto);

        ReviewImage entity = ReviewImage.builder()
                .rev_no(revNo)
                .cor_no(corNo)
                .img_uuid(fileName)
                .img_url(fileRootPath+"/"+fileName)
                .user_no(user_no)
                .idx(img_index)
                .build();

//        repository.save(entity);

        return entity;
    }

    public ReviewImage update(ReviewImage img) {
        return repository.save(img);
    }

    public ReviewImage dtoToEntity(ReviewImageDTO dto) {
        ReviewImage entity = ReviewImage.builder()
                .img_url(dto.getImg_url())
                .user_no(dto.getUser_no())
                .cor_no(dto.getCor_no())
                .img_uuid(dto.getImg_uuid())
                .idx(dto.getIdx())
                .is_deleted(dto.is_deleted())
                .rev_no(dto.getRev_no())
                .build();

        return entity;
    }

    public ReviewImageDTO entityToDTO(ReviewImage img) {
        ReviewImageDTO dto = ReviewImageDTO.builder()
                .idx(img.getIdx())
                .img_no(img.getImg_no())
                .img_url(img.getImg_url())
                .img_uuid(img.getImg_uuid())
                .cor_no(img.getCor_no())
                .regDate(img.getRegDate())
                .modDate(img.getModDate())
                .is_deleted(img.is_deleted())
                .rev_no(img.getRev_no())
                .build();

        return dto;
    }

    private ReviewImageDTO getImageDTO(String user_no, String fileRootPath, String fileName, Long corNo, Long revNo) {
        ReviewImageDTO dto = ReviewImageDTO.builder()
                .img_url(fileRootPath + File.separator + fileName)
                .user_no(Long.valueOf(user_no))
                .cor_no(corNo)
                .rev_no(revNo)
                .img_uuid(fileName)
                .build();

        return dto;
    }

    private ReviewImage disable(ReviewImage img) {
        img.set_deleted(true);

        update(img);

        return getImage(img.getImg_no());
    }

    private ReviewImage enable(ReviewImage img) {
        img.set_deleted(false);

        update(img);

        return getImage(img.getImg_no());
    }

    public void delete(Long imgNo) {
        ReviewImage img = getImage(imgNo);

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public void delete(String uuid) {
        ReviewImage img = getImage(uuid);

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public void permaDelete(String img_uuid) {
        repository.deleteByImg_uuid(img_uuid);
    }

    public ReviewImage editImageEntityIndex(String uuid, Long img_Index) {
        ReviewImage img = getImage(uuid);

        img.setIdx(img_Index);

        return img;
    }
}
