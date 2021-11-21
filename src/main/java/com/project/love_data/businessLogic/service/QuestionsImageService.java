package com.project.love_data.businessLogic.service;

import com.project.love_data.model.resource.QuestionsImage;
import com.project.love_data.model.service.Notice;
import com.project.love_data.repository.QuestionsImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Log4j2
@Service
@RequiredArgsConstructor
public class QuestionsImageService {
    @Autowired
    QuestionsImageRepository questionsImageRepository;

    public void update(QuestionsImage questionsImage){
        questionsImageRepository.save(questionsImage);
    }

    public void delete(QuestionsImage questionsImage) {
        questionsImageRepository.delete(questionsImage);
    }

    public List<QuestionsImage> qu_no_imgselect(String qu_no){
        Optional<List<QuestionsImage>> questionsImages = questionsImageRepository.qu_no_imgselect(qu_no);

        return questionsImages.orElse(new ArrayList<>());
    }

    public List<QuestionsImage> user_no_imgselect(String qu_no){
        Optional<List<QuestionsImage>> questionsImages = questionsImageRepository.user_no_imgselect(qu_no);

        return questionsImages.orElse(new ArrayList<>());
    }


}
