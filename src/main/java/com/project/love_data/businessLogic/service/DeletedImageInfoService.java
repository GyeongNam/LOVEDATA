package com.project.love_data.businessLogic.service;

import com.project.love_data.dto.DeletedImageInfoDTO;
import com.project.love_data.model.service.DeletedImageInfo;
import com.project.love_data.repository.DeletedImageInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class DeletedImageInfoService {
    @Autowired
    DeletedImageInfoRepository repository;

    public DeletedImageInfo dtoToEntity(DeletedImageInfoDTO dto) {
        DeletedImageInfo entity = DeletedImageInfo.builder()
                .diiNo(dto.getDiiNo())
                .diiUuid(dto.getDiiUuid())
                .imgNo(dto.getImgNo())
                .imgUuid(dto.getImgUuid())
                .imgType(dto.getImgType())
                .imgUserNo(dto.getImgUserNo())
                .result(dto.getResult())
                .deleted(dto.isDeleted())
                .build();

        return entity;
    }

    public DeletedImageInfoDTO entityToDTO(DeletedImageInfo entity) {
        DeletedImageInfoDTO dto = DeletedImageInfoDTO.builder()
                .diiNo(entity.getDiiNo())
                .diiUuid(entity.getDiiUuid())
                .imgNo(entity.getImgNo())
                .imgUuid(entity.getImgUuid())
                .imgType(entity.getImgType())
                .imgUserNo(entity.getImgUserNo())
                .result(entity.getResult())
                .deleted(entity.isDeleted())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .build();

        return dto;
    }

    public DeletedImageInfo register(Long imgNo, String imgType, Long imgUserNo, String imgUuid) {

        if (imgNo == null || imgType == null || imgUserNo == null || imgUuid == null) {
            log.warn("Fail during Registering DeletedImageInfo\nreqParam doesn't have sufficient keys");
            return null;
        }

        if (imgType.equals("")) {
            log.warn("imgType is Empty");
            return null;
        }

        DeletedImageInfo entity = DeletedImageInfo.builder()
                .imgType(imgType)
                .imgNo(imgNo)
                .imgUserNo(imgUserNo)
                .imgUuid(imgUuid)
                .build();

        save(entity);

        DeletedImageInfo item = select(entity.getDiiUuid());

        return item;
    }

    public boolean changeStatus(Long diiNo, String result, boolean deleted) {
        if (diiNo == null) {
            return false;
        }

        if (diiNo < 0) {
            return false;
        }

        if (result == null) {
            return false;
        }

        Optional<DeletedImageInfo> item = repository.findById(diiNo);

        if (!item.isPresent()) {
            log.warn("DIINo is not current PK");
            return false;
        }

        DeletedImageInfo entity = item.get();

        entity.setResult(result);
        entity.setDeleted(deleted);

        save(entity);

        item = repository.findById(diiNo);
        entity = item.get();

        if (!entity.getResult().equals(result) || entity.isDeleted() != deleted) {
            log.warn("Change DII Status failed");
            return false;
        }

        return true;
    }

    public void save(DeletedImageInfo entity) {
        repository.save(entity);
    }

    public DeletedImageInfo select(Long diiNo) {
        Optional<DeletedImageInfo> item = repository.findById(diiNo);

        return item.orElse(null);
    }

    public DeletedImageInfo select(String diiUuid) {
        Optional<DeletedImageInfo> item = repository.findDIIByDiiUuid(diiUuid);

        return item.orElse(null);
    }

    public DeletedImageInfo select(Long imgNo, String imgType) {
        Optional<DeletedImageInfo> item = repository.findDIIByImgTypeAndImgNo(imgType, imgNo);

        return item.orElse(null);
    }

    public DeletedImageInfo select(Long diiNo, boolean deleted) {
        Optional<DeletedImageInfo> item = repository.findDIIByDiiNoAndDeleted(diiNo, deleted);

        return item.orElse(null);
    }

    public DeletedImageInfo select(String diiUuid, boolean deleted) {
        Optional<DeletedImageInfo> item = repository.findDIIByDiiUuidAndDeleted(diiUuid, deleted);

        return item.orElse(null);
    }

    public DeletedImageInfo select(Long imgNo, String imgType, boolean deleted) {
        Optional<DeletedImageInfo> item = repository.findDIIByImgTypeAndImgNoAndDeleted(imgType, imgNo, deleted);

        return item.orElse(null);
    }

    public List<DeletedImageInfo> findAllByImgType(String imgType){
        Optional<List<DeletedImageInfo>> items = repository.findDIIsByImgType(imgType);

        return items.orElse(null);
    }

    public List<DeletedImageInfo> findAllByUserNo(Long userNo){
        Optional<List<DeletedImageInfo>> items = repository.findDIIsByUserNo(userNo);

        return items.orElse(null);
    }

    public List<DeletedImageInfo> findAllByImgType(String imgType, boolean deleted){
        Optional<List<DeletedImageInfo>> items = repository.findDIIsByImgTypeAndDeleted(imgType, deleted);

        return items.orElse(null);
    }

    public List<DeletedImageInfo> findAllByUserNo(Long userNo, boolean deleted){
        Optional<List<DeletedImageInfo>> items = repository.findDIIsByUserNoAndDeleted(userNo, deleted);

        return items.orElse(null);
    }

    public DeletedImageInfo findByImgUuid(String uuid) {
        if (uuid == null) {
            return null;
        }

        if (uuid.equals("")) {
            return null;
        }

        Optional<DeletedImageInfo> item = repository.findDIIByImgUuid(uuid);

        return item.orElse(null);
    }

    public boolean updateStatusToDelete(String uuid, String result) {
        DeletedImageInfo entity = findByImgUuid(uuid);

        if (entity == null) {
            return false;
        }

        entity.setDeleted(true);
        entity.setResult(result);

        save(entity);

        entity = findByImgUuid(uuid);

        if (!entity.isDeleted() || !entity.getResult().equals(result)) {
            return false;
        }

        return true;
    }
}
