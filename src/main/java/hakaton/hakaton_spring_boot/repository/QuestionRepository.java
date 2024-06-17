package hakaton.hakaton_spring_boot.repository;

import hakaton.hakaton_spring_boot.repository.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
