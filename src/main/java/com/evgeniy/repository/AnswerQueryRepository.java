package com.evgeniy.repository;

import com.evgeniy.entity.AnswerQuery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerQueryRepository extends JpaRepository<AnswerQuery, Long> {
    List<AnswerQuery> findAllByTitleContainsIgnoreCase(String query);
}
