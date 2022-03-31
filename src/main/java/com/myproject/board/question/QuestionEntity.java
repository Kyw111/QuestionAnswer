package com.myproject.board.question;

import com.myproject.board.answer.AnswerEntity;
import lombok.Getter;
import lombok.Setter;
import org.w3c.dom.Text;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 200)
    private String title;

    @Column(columnDefinition = "TEXT") // 내용을 적는 컬럼 특성상 글자 수를 제한할 수 없는 경우에 사용
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE) // 질문 하나에는 여러개의 답변이 작성 - 질문삭제시 해당답변들도 모두 삭제하기 위함
    private List<AnswerEntity> answerList;
}
