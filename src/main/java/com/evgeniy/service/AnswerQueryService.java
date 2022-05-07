package com.evgeniy.service;

import com.evgeniy.entity.AnswerQuery;
import com.evgeniy.repository.AnswerQueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnswerQueryService {
    @Autowired
    AnswerQueryRepository answerQueryRepository;


    public List<AnswerQuery> findByTitleContains(String title) {

        return answerQueryRepository.findAllByTitleContainsIgnoreCase(title);
    }
}
