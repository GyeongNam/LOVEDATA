package com.project.love_data.businessLogic.service;

import com.project.love_data.model.service.CorLocMapper;
import com.project.love_data.repository.CorLocMapperRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Log4j2
public class CorLocMapperService {
    private final CorLocMapperRepository repository;

    public CorLocMapper createNewEntity() {
        CorLocMapper entity = CorLocMapper.builder().build();

        return entity;
    }

    public List<CorLocMapper> register(Long corNo, List<Long> locNoList) {
        List<CorLocMapper> entities = new ArrayList<>();

        for (int i = 0; i < locNoList.size(); i++) {
            CorLocMapper entity = CorLocMapper.builder()
                    .cor_no(corNo)
                    .loc_no(locNoList.get(i))
                    .loc_index(i)
                    .build();

            entities.add(save(entity));
        }

        return entities;
    }

    public CorLocMapper save(CorLocMapper entity) {
        CorLocMapper item = repository.save(entity);

        return item;
    }

    public List<CorLocMapper> getLocationsByCorNo(Long corNo) {
        Optional<List<CorLocMapper>> item = repository.findAllByCor_no(corNo);

        return item.orElse(null);
    }

    public CorLocMapper select(Long clmNo) {
        Optional<CorLocMapper> item = repository.findById(clmNo);

        return item.orElse(null);
    }

    public CorLocMapper select(String clmUUID) {
        Optional<CorLocMapper> item = repository.findByUUID(clmUUID);

        return item.orElse(null);
    }

    public boolean permaDelete(Long clmNo) {
        CorLocMapper item = select(clmNo);

        if (item == null) {
            return false;
        }

        repository.deleteByClm_No(item.getClm_No());

        item = select(item.getClm_No());

        if (item != null) {
            return true;
        }

        return false;
    }

    public boolean permaDelete(String clmUuid) {
        CorLocMapper item = select(clmUuid);

        if (item == null) {
            return false;
        }

        repository.deleteByClm_No(item.getClm_No());

        item = select(item.getClm_No());

        if (item != null) {
            return true;
        }

        return false;
    }
}
