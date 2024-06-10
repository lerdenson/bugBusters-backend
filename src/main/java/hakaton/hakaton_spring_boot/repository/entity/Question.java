package hakaton.hakaton_spring_boot.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String theme;
    private String question;
    private String answer1;
    private String answer2;
    private String answer3;
    private String answer4;
    private String correctanswer;
}
