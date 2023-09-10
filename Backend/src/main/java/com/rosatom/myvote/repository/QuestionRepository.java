package com.rosatom.myvote.repository;

import com.rosatom.myvote.model.dto.EmotionalPercentageByQuestion;
import com.rosatom.myvote.model.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
    @Query(value = "select * from question WHERE upper(name) LIKE '%' || upper(:query) || '%' And file_id = :file_id", nativeQuery = true)
    List<QuestionEntity> findByQuery(@Param("file_id") Long fileId, @Param("query") String query);

    @Query(value = "select * from question where file_id = :file_id", nativeQuery = true)
    List<QuestionEntity> findAllByFileId(@Param("file_id") Long fileId);

    List<QuestionEntity> deleteByFileId(Long fileId);

    @Query(value = """
            SELECT DISTINCT q.name, COUNT(*) AS count
            FROM question q
            JOIN answer a ON a.external_id = q.external_id
            WHERE q.file_id = :file_id AND a.emotional = :emotional
            GROUP BY q.name
            ORDER BY count DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<Map<String, Integer>> findTopAnalytics(@Param("file_id") Long fileId,
                                                @Param("emotional") String emotional,
                                                @Param("limit") Integer limit);

    @Query(value = """
            SELECT * FROM (SELECT\s
            	src.name,
                ROUND(((SELECT COUNT(*) FROM answer a1 WHERE a1.external_id = src.external_id AND a1.emotional = 'neutral' AND a1.file_id = :file_id) * 1.0 / (SELECT COUNT(*) FROM answer a2 WHERE a2.emotional = 'neutral'  AND a2.file_id = :file_id) * 1.0) * 100.0, 2) neutral,
                ROUND(((SELECT COUNT(*) FROM answer a1 WHERE a1.external_id = src.external_id AND a1.emotional = 'positive' AND a1.file_id = :file_id) * 1.0 / (SELECT COUNT(*) FROM answer a2 WHERE a2.emotional = 'positive'  AND a2.file_id = :file_id) * 1.0) * 100.0, 2) positive,
            	ROUND(((SELECT COUNT(*) FROM answer a1 WHERE a1.external_id = src.external_id AND a1.emotional = 'negative' AND a1.file_id = :file_id) * 1.0 / (SELECT COUNT(*) FROM answer a2 WHERE a2.emotional = 'negative' AND a2.file_id = :file_id) * 1.0) * 100.0, 2) negative,
                (SELECT COUNT(*) FROM answer a1 WHERE a1.file_id = :file_id AND a1.external_id = src.external_id)
            FROM (
            	SELECT DISTINCT q.external_id, q.name FROM question q
            	WHERE q.file_id = :file_id
            ) src) src
            ORDER BY :number_order DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<Map<String, Double>> emotionalDistribution(@Param("file_id") Long fileId,
                                                    @Param("limit") Integer limit,
                                                    @Param("number_order") String numberOrder);

    @Query(value = """
            SELECT a.emotional, COUNT(*) FROM question q
            JOIN answer a ON a.external_id = q.external_id
            WHERE q.file_id = :file_id
            AND (:question_id IS NULL OR q.id = :question_id)
            GROUP BY a.emotional
            """, nativeQuery = true)
    List<Map<String, Integer>> countStat(@Param("file_id") Long fileId,
                                         @Param("question_id") Long questionId);

    @Query(value = """
               SELECT COUNT(*) FROM question q
                         JOIN answer a ON a.external_id = q.external_id
                         WHERE 1 = 1\s
             			AND q.file_id = :file_id 
             			AND a.file_id = q.file_id 
                         AND (:question_id IS NULL OR q.id = :question_id)
             			AND (a.bad_words_percent IS NOT NULL AND a.bad_words_percent > 0.0)
            """, nativeQuery = true)
    Integer censoredStat(@Param("file_id") Long fileId,
                         @Param("question_id") Long questionId);

    @Query(value = """
            SELECT COUNT(*) FROM answer a\s
            JOIN question q ON q.external_id = a.external_id\s
            WHERE 1 = 1\s
            AND a.significance <= 0.5 AND a.file_id = :file_id
            AND q.file_id = :file_id
            AND a.file_id = q.file_id\s
            AND (:question_id IS NULL OR q.id = :question_id)
            """, nativeQuery = true)
    Integer countIncorrectAnswer(@Param("file_id") Long fileId, @Param("question_id") Long questionId);

    @Query(value = """
             SELECT q.id AS question_id, q.name AS question_name, COUNT(*) AS total_answers,
                     SUM(CASE WHEN a.significance < :percent THEN 1 ELSE 0 END) AS non_matching_answers
              FROM question q
              JOIN answer a ON q.external_id = a.external_id
              WHERE q.file_id = :file_id AND a.file_id = :file_id
              GROUP BY q.id, q.name
              ORDER BY 2 DESC
              LIMIT :limit
            """, nativeQuery = true)
    List<Map<String, Integer>> incorrectAnswerTop(
            @Param("file_id") Long fileId,
            @Param("percent") Double percent,
            @Param("limit") Integer limit);

    @Query(value = """
            SELECT DISTINCT src.answer FROM (SELECT a.answer FROM question q\s
            JOIN answer a ON q.external_id = a.external_id\s
            WHERE q.file_id = :file_id\s
            AND a.file_id = q.file_id\s
            AND (:question_id IS NULL OR q.id = :question_id)
            AND length(a.answer) > 5 AND a.answer LIKE '% %'
            ORDER BY a.significance DESC, a.bad_words_percent ASC) src\s
            LIMIT :limit
            """, nativeQuery = true)
    List<String> topAnswer(@Param("file_id") Long fileId,
                           @Param("question_id") Long questionId,
                           @Param("limit") Integer limit);
}
