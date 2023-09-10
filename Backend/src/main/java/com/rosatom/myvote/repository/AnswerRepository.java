package com.rosatom.myvote.repository;

import com.rosatom.myvote.model.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {
    void deleteByFileId(Long fileId);

    @Query(value = """
            SELECT a.* FROM question q
            JOIN answer a ON q.external_id = a.external_id
            WHERE q.file_id = :file_id
                  AND a.file_id = q.file_id
                  AND (:size = 0 OR q.id IN (:question_ids))
            """, nativeQuery = true)
    List<AnswerEntity> findAllByFileId(@Param("file_id") Long fileId,
                                       @Param("question_ids") List<Long> questionIds,
                                       @Param("size") Integer size);
}
