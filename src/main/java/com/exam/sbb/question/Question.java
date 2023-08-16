package com.exam.sbb.question;



import com.exam.sbb.answer.Answer;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 200)
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL})
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answer.setQuestion(this);
        getAnswerList().add(answer);
    }
}
