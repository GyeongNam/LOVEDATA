package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.ReviewImageDTO;
import com.project.love_data.model.resource.ReviewImage;
import com.project.love_data.model.service.Review;
import com.project.love_data.repository.ReviewImageRepository;
import com.project.love_data.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ReviewImageService {
    public final ReviewRepository revRepository;
    private final ReviewImageRepository repository;
    private final FilePathChangeService pathChangeService;

    public ReviewImage getImage(Long imgId) {
        Optional<ReviewImage> item = repository.findById(imgId);

        return item.orElse(null);
    }

    public ReviewImage getAllImage(String uuid) {
        Optional<ReviewImage> item = repository.findImageByImg_uuid(uuid);

        return item.orElse(null);
    }

    public ReviewImage getLiveImage(String uuid) {
        Optional<ReviewImage> item = repository.findLiveImageByImg_uuid(uuid);

        return item.orElse(null);
    }

    public List<ReviewImage> getAllLiveImageByRevNo(Long revNo) {
        Optional<List<ReviewImage>> items = repository.findAllLiveImageByRev_no(revNo);

        return items.orElse(null);
    }

    public List<ReviewImage> getAllImagesByCorNo(Long corNo) {
        Optional<List<ReviewImage>> itmes = repository.findAllImageByCor_no(corNo);

        return itmes.orElse(null);
    }

    public List<ReviewImage> getAllLiveImageByCorNoAndRevNo(Long corNo, Long revNo) {
        Optional<List<ReviewImage>> items = repository.findAllLiveImageByCor_noAndRev_no(corNo, revNo);

        return items.orElse(null);
    }

    public List<ReviewImage> getAllImageByCorNoAndRevNo(Long corNo, Long revNo) {
        Optional<List<ReviewImage>> items = repository.findAllImageByCor_noAndRev_no(corNo, revNo);

        return items.orElse(null);
    }

    public List<ReviewImage> getLiveImagesByCorNo(Long corNo) {
        Optional<List<ReviewImage>> itmes = repository.findAllLiveImageByCor_no(corNo);

        return itmes.orElse(null);
    }

    public List<ReviewImage> getLastDeletedImageByRevNo(Long revNo) {
        Optional<List<ReviewImage>> items = repository.findLastDeletedReviewImagesByRevNo(revNo);

        return items.orElse(null);
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
//        String extension = pathChangeService.getFileExtension(img.getImg_url());
//        if (pathChangeService.execute(img.getImg_uuid(), FileAction.ROLLBACK,
//                PathType.REV, FileExtension.valueOf(extension.toUpperCase(Locale.ROOT)))){
//            img.set_deleted(false);
//            img.setImg_url("/image/review/" + img.getImg_uuid());
//            update(img);
//        }

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
        ReviewImage img = getAllImage(uuid);

        if (!img.is_deleted()) {
            disable(img);
        }
    }

    public void permaDelete(String img_uuid) {
        Optional<ReviewImage> item = repository.findImageByImg_uuid(img_uuid);

        if (item.isPresent()) {
            ReviewImage img = item.get();
            String extension = pathChangeService.getFileExtension(img.getImg_url());
            if (pathChangeService.execute(img.getImg_uuid(), FileAction.DELETE,
                    PathType.REV, FileExtension.valueOf(extension.toUpperCase(Locale.ROOT)))){
//                img.set_deleted(true);
//                img.setImg_url("/image/upload/REV^" + img.getImg_uuid());
//                update(img);
                repository.deleteByImg_uuid(img_uuid);
            }
        }
    }

    public ReviewImage editImageEntityIndex(String uuid, Long img_Index) {
        ReviewImage img = getAllImage(uuid);

        img.setIdx(img_Index);

        return img;
    }

    public void rollback(Long imgNo) {
        ReviewImage img = getImage(imgNo);

        if (img.is_deleted()) {
            enable(img);
        }
    }

    public List<ReviewImage> updateOldImage(Long revNo, List<String> filePath) {
        Optional<Review> revItem = revRepository.findByRev_no(revNo);
        Optional<List<ReviewImage>> items = repository.findAllLiveImageByRev_no(revNo);
        if (!items.isPresent() || !revItem.isPresent()) {
            return null;
        }

        List<ReviewImage> entities = items.get();
        List<ReviewImage> duplicatedImg = new ArrayList<>();

        if (filePath != null) {
            first :
            for (ReviewImage image : entities) {
                second :
                for (int i = 1; i < filePath.size(); i += 2) {
                    if (filePath.get(i).equals(image.getImg_uuid())) {
                        duplicatedImg.add(image);
//                        image.set_deleted(true);
//                        update(image);
                        continue first;
                    }
                }

                delete(image.getImg_no());
            }
            entities.clear();

            first :
            for (int i = 0; i < filePath.size(); i += 2) {
                // filePath.get(i)  ==  Parent Folder (URI)
                // filePath.get(i+1)  ==  fileNames
                second :
                for (int j = 0; j < duplicatedImg.size(); j++) {
                    if (!filePath.get(i + 1).equals(duplicatedImg.get(j).getImg_uuid())) {
                        entities.add(duplicatedImg.get(j));
                        continue first;
                    }
                }
                ReviewImage revImage = getImageEntity(revItem.get().getUser_no(),
                        filePath.get(i), filePath.get(i+1), revItem.get().getCorNo(), revItem.get().getRevNo(), (long) (i / 2));
                ReviewImage imgEntity = update(revImage);
                entities.add(imgEntity);
            }
        } else {
            for (ReviewImage image : entities) {
                delete(image.getImg_no());
            }

            entities.clear();
        }


        return entities;
    }

    public List<ReviewImage> getImage_UserNo(Long user_no) {
        Optional<List<ReviewImage>> item = repository.findAllLiveImageByUser_no(user_no);

        return item.orElse(null);
    }
}
