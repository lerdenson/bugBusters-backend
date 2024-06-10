package hakaton.hakaton_spring_boot.repository;

import hakaton.hakaton_spring_boot.repository.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
//    List<Question> findAll();
//
//
//    Question getById(Long id);
}
